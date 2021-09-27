/*
 * detectable
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.detectable.detectables.dart.pubdep;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synopsys.integration.bdio.graph.DependencyGraph;
import com.synopsys.integration.detectable.ExecutableTarget;
import com.synopsys.integration.detectable.ExecutableUtils;
import com.synopsys.integration.detectable.detectable.codelocation.CodeLocation;
import com.synopsys.integration.detectable.detectable.executable.DetectableExecutableRunner;
import com.synopsys.integration.detectable.detectables.dart.PubSpecYamlNameVersionParser;
import com.synopsys.integration.detectable.extraction.Extraction;
import com.synopsys.integration.executable.ExecutableOutput;
import com.synopsys.integration.executable.ExecutableRunnerException;
import com.synopsys.integration.util.NameVersion;

public class PubDepsExtractor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DetectableExecutableRunner executableRunner;
    private final PubDepsParser pubDepsParser;
    private PubSpecYamlNameVersionParser nameVersionParser;

    public PubDepsExtractor(DetectableExecutableRunner executableRunner, PubDepsParser pubDepsParser, PubSpecYamlNameVersionParser nameVersionParser) {
        this.executableRunner = executableRunner;
        this.pubDepsParser = pubDepsParser;
        this.nameVersionParser = nameVersionParser;
    }

    public Extraction extract(File directory, @Nullable ExecutableTarget dartExe, @Nullable ExecutableTarget flutterExe, DartPubDepsDetectableOptions dartPubDepsDetectableOptions, File pubSpecYamlFile) {
        try {
            List<String> pubDepsCommand = new ArrayList<>();
            pubDepsCommand.add("pub");
            pubDepsCommand.add("deps");

            if (dartPubDepsDetectableOptions.isExcludeDevDependencies()) {
                pubDepsCommand.add("--no-dev");
            }

            ExecutableOutput pubDepsOutput = null;

            if (dartExe != null) {
                pubDepsOutput = runPubDepsCommand(directory, dartExe, pubDepsCommand);
            }

            if (pubDepsOutput == null || pubDepsOutput.getReturnCode() != 0) {
                if (flutterExe == null && dartExe != null) {
                    return new Extraction.Builder().failure(String.format("An error occurred trying to run %s %s", dartExe.toCommand(), String.join(" ", pubDepsCommand))).build();
                } else {
                    // If command does not work with Dart, it could be because at least one of the packages requires Flutter
                    logger.debug("Running dart pub deps was not successful.  Going to try running flutter pub deps.");
                    pubDepsOutput = runPubDepsCommand(directory, flutterExe, pubDepsCommand);
                }
            }

            Optional<NameVersion> nameVersion = nameVersionParser.parseNameVersion(pubSpecYamlFile);

            DependencyGraph dependencyGraph = pubDepsParser.parse(pubDepsOutput.getStandardOutputAsList());

            CodeLocation codeLocation = new CodeLocation(dependencyGraph);

            return new Extraction.Builder().success(codeLocation).nameVersionIfPresent(nameVersion).build();
        } catch (Exception e) {
            return new Extraction.Builder().exception(e).build();
        }
    }

    private ExecutableOutput runPubDepsCommand(File directory, ExecutableTarget exe, List<String> commandArgs) throws ExecutableRunnerException {
        return executableRunner.execute(ExecutableUtils.createFromTarget(directory, exe, commandArgs));
    }
}