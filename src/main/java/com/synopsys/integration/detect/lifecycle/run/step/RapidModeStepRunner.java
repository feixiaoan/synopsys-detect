package com.synopsys.integration.detect.lifecycle.run.step;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.synopsys.integration.blackduck.api.generated.view.DeveloperScansScanView;
import com.synopsys.integration.blackduck.codelocation.signaturescanner.command.ScanCommandOutput;
import com.synopsys.integration.detect.configuration.DetectUserFriendlyException;
import com.synopsys.integration.detect.configuration.enumeration.BlackduckScanMode;
import com.synopsys.integration.detect.configuration.enumeration.DetectTool;
import com.synopsys.integration.detect.lifecycle.OperationException;
import com.synopsys.integration.detect.lifecycle.run.data.BlackDuckRunData;
import com.synopsys.integration.detect.lifecycle.run.data.DockerTargetData;
import com.synopsys.integration.detect.lifecycle.run.operation.OperationRunner;
import com.synopsys.integration.detect.lifecycle.run.step.utility.StepHelper;
import com.synopsys.integration.detect.tool.signaturescanner.operation.SignatureScanOuputResult;
import com.synopsys.integration.detect.tool.signaturescanner.operation.SignatureScanResult;
import com.synopsys.integration.detect.workflow.bdio.BdioResult;
import com.synopsys.integration.detect.workflow.blackduck.developer.aggregate.RapidScanResultSummary;
import com.synopsys.integration.detect.workflow.file.DirectoryManager;
import com.synopsys.integration.detect.workflow.status.FormattedCodeLocation;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.rest.HttpUrl;
import com.synopsys.integration.util.NameVersion;

public class RapidModeStepRunner {
    private final OperationRunner operationRunner;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final StepHelper stepHelper;
    private final Gson gson;
    private final DirectoryManager directoryManager;

    public RapidModeStepRunner(OperationRunner operationRunner, StepHelper stepHelper, Gson gson, DirectoryManager directoryManager) {
        this.operationRunner = operationRunner;
        this.stepHelper = stepHelper;
        this.gson = gson;
        this.directoryManager = directoryManager;
    }

    public void runOnline(BlackDuckRunData blackDuckRunData, NameVersion projectVersion, BdioResult bdioResult,
            DockerTargetData dockerTargetData, Optional<String> scaaasFilePath) throws OperationException, IOException {
        operationRunner.phoneHome(blackDuckRunData);
        Optional<File> rapidScanConfig = operationRunner.findRapidScanConfig();
        String scanMode = blackDuckRunData.getScanMode().displayName();
        rapidScanConfig.ifPresent(config -> logger.info("Found " + scanMode.toLowerCase() + " scan config file: {}", config));

        String blackDuckUrl = blackDuckRunData.getBlackDuckServerConfig().getBlackDuckUrl().toString();
        List<HttpUrl> parsedUrls = new ArrayList<>();
        Set<FormattedCodeLocation> formattedCodeLocations = new HashSet<>();
        
        List<HttpUrl> uploadResultsUrls = operationRunner.performRapidUpload(blackDuckRunData, bdioResult, rapidScanConfig.orElse(null));
        
        if (uploadResultsUrls != null && uploadResultsUrls.size() > 0) {
            processScanResults(uploadResultsUrls, parsedUrls, formattedCodeLocations, DetectTool.DETECTOR.name());  
        }

        stepHelper.runToolIfIncluded(DetectTool.SIGNATURE_SCAN, "Signature Scanner", () -> {
            logger.debug("Stateless scan signature scan detected.");

            SignatureScanStepRunner signatureScanStepRunner = new SignatureScanStepRunner(operationRunner);
            SignatureScanOuputResult signatureScanOutputResult = signatureScanStepRunner
                    .runRapidSignatureScannerOnline(blackDuckRunData, projectVersion, dockerTargetData);
            
            List<HttpUrl> parseScanUrls = parseScanUrls(scanMode, signatureScanOutputResult, blackDuckUrl);
            processScanResults(parseScanUrls, parsedUrls, formattedCodeLocations, DetectTool.SIGNATURE_SCAN.name());  
        });
        
        stepHelper.runToolIfIncluded(DetectTool.BINARY_SCAN, "Binary Scanner", () -> {
            logger.debug("Stateless binary scan detected.");
            
            // Check if this is an SCA environment. Stateless Binary Scans are only supported there.
            if (scaaasFilePath.isPresent()) {
                List<HttpUrl> bdbaResultUrls = new ArrayList<>();
                invokeBdbaRapidScan(blackDuckRunData, projectVersion, blackDuckUrl, bdbaResultUrls, false, scaaasFilePath.get());
                processScanResults(bdbaResultUrls, parsedUrls, formattedCodeLocations, DetectTool.BINARY_SCAN.name());  
            } else {
                logger.debug("Stateless binary scan detected but no detect.scaaas.scan.path specified, skipping.");
            }
        });
        
        stepHelper.runToolIfIncluded(DetectTool.CONTAINER_SCAN, "Container Scanner", () -> {
            logger.debug("Stateless container scan detected.");
            
            // Check if this is an SCA environment. Stateless Container Scans are only supported there.
            if (scaaasFilePath.isPresent()) {
                List<HttpUrl> containerResultUrls = new ArrayList<>();
                invokeBdbaRapidScan(blackDuckRunData, projectVersion, blackDuckUrl, containerResultUrls, true, scaaasFilePath.get());
                processScanResults(containerResultUrls, parsedUrls, formattedCodeLocations, DetectTool.CONTAINER_SCAN.name()); 
            } else {
                logger.debug("Stateless container scan detected but no detect.scaaas.scan.path specified, skipping.");
            }
        });

        // Get info about any scans that were done
        BlackduckScanMode mode = blackDuckRunData.getScanMode();
        List<DeveloperScansScanView> rapidFullResults = operationRunner.waitForFullRapidResults(blackDuckRunData, parsedUrls, mode);

        // Generate a report, even an empty one if no scans were done as that is what previous detect versions did.
        File jsonFile = operationRunner.generateRapidJsonFile(projectVersion, rapidFullResults);
        RapidScanResultSummary summary = operationRunner.logRapidReport(rapidFullResults, mode);

        operationRunner.publishRapidResults(jsonFile, summary, mode);
        operationRunner.publishCodeLocationData(formattedCodeLocations);
    }

    /**
     * This method takes a list of URLs for a given scan type and adds them to the parsedUrls structure so 
     * results can be retrieved from BD after all scans are done. It also stores information for the status.json
     * file in formattedCodeLocations so scanId and type can be reported.
     */
    private void processScanResults(List<HttpUrl> scanResultUrls, List<HttpUrl> parsedUrls,
            Set<FormattedCodeLocation> formattedCodeLocations, String scanType) {        
        for (HttpUrl httpUrl : scanResultUrls) {
            UUID scanId;
            try {
                scanId = operationRunner.getScanIdFromScanUrl(httpUrl);
                parsedUrls.add(httpUrl);
                FormattedCodeLocation codeLocationData = new FormattedCodeLocation(null, scanId, scanType);
                formattedCodeLocations.add(codeLocationData);
            } catch (IllegalArgumentException e) {
                logger.info(String.format("Unable to parse scanId from URL %s", httpUrl));
            }
        }
    }

    private void invokeBdbaRapidScan(BlackDuckRunData blackDuckRunData, NameVersion projectVersion, String blackDuckUrl,
            List<HttpUrl> parsedUrls, boolean isContainerScan, String scaasFilePath)
            throws IntegrationException, IOException, InterruptedException, OperationException, DetectUserFriendlyException {
        // Generate the UUID we use to communicate with BDBA
        UUID bdbaScanId = UUID.randomUUID();
        
        RapidBdbaStepRunner rapidBdbaStepRunner = new RapidBdbaStepRunner(gson, bdbaScanId, blackDuckRunData.getBlackDuckServerConfig().getTimeout());
        rapidBdbaStepRunner.submitScan(isContainerScan, scaasFilePath);
        rapidBdbaStepRunner.pollForResults();
        rapidBdbaStepRunner.downloadAndExtractBdio(directoryManager);

        UUID bdScanId = operationRunner.initiateStatelessBdbaScan(blackDuckRunData);
        operationRunner.uploadBdioEntries(blackDuckRunData, bdScanId);

        // add this scan to the URLs to wait for
        parsedUrls.add(new HttpUrl(blackDuckUrl + "/api/developer-scans/" + bdScanId.toString()));
    }

    /**
     * The signature scanner only returns a high level success or failure to us. Details are in the
     * output directory's scanOutput.json. We need to crack that open to get the scanId so we can poll
     * for the true results from BlackDuck later.
     * 
     * @return a list of URLs that BlackDuck should poll for rapid signature scan results.
     */
    private List<HttpUrl> parseScanUrls(String scanMode, SignatureScanOuputResult signatureScanOutputResult, String blackDuckUrl) throws IOException, IntegrationException {
        List<ScanCommandOutput> outputs = signatureScanOutputResult.getScanBatchOutput().getOutputs();
        List<HttpUrl> parsedUrls = new ArrayList<>(outputs.size());
        
        for (ScanCommandOutput output : outputs) {
            try {
                File specificRunOutputDirectory = output.getSpecificRunOutputDirectory();
                String scanOutputLocation = specificRunOutputDirectory.toString() + SignatureScanResult.OUTPUT_FILE_PATH;
                Reader reader = Files.newBufferedReader(Paths.get(scanOutputLocation));

                SignatureScanResult result = gson.fromJson(reader, SignatureScanResult.class);

                if (result.getExitStatus() == null || !result.getExitStatus().equalsIgnoreCase("FAILURE")) {

                    Set<String> parsedIds = result.parseScanIds();

                    for (String id : parsedIds) {
                        HttpUrl url = new HttpUrl(blackDuckUrl + "/api/developer-scans/" + id);

                        logger.info(scanMode + " mode signature scan URL: {}", url);
                        parsedUrls.add(url);
                    }
                } else {
                    logger.debug("{} mode signature scan result not processed for scan IDs due to exit status from BD: {}", scanMode, result.getExitStatus());
                }
            } catch (Exception e) {
                throw new IntegrationException("Unable to parse rapid signature scan results.");
            }
        }
        return parsedUrls;
    }
}
