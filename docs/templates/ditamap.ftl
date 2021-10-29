<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE map PUBLIC "-//OASIS//DTD DITA Map//EN" "map.dtd">
<map id="detect-user-guide" xml:lang="en-US">
  <title>${solution_name}</title>
  <topicmeta>
    <shortdesc>Synopsys Detect 7.7.0-SIGQA9</shortdesc>
    <!-- zoomin bundle name -->
    <othermeta name="bundle" content="integrations-detect"/>
  </topicmeta>
  <!-- link to zoomin classification map -->
  <topicref processing-role="resource-only" format="xml" type="xml"
    href="integrations-classification.xml" outputclass="classmapdata" scope="local"/>

  <topicref href="introduction.md" format="markdown"/>
  <topicref href="releasenotes.md" format="markdown"/>
  <topicref href="gettingstarted/overview.md" format="markdown">
    <topicref href="gettingstarted/terms.md" format="markdown"/>
    <topicref href="gettingstarted/howitworks.md" format="markdown"/>
    <topicref href="gettingstarted/workflow.md" format="markdown"/>
    <topicref href="gettingstarted/configuring.md" format="markdown"/>
    <topicref href="gettingstarted/phases.md" format="markdown"/>
    <topicref href="requirements.md" format="markdown"/>
    <topicref href="gettingstarted/usersandroles.md" format="markdown"/>
    <topicref href="quickstart.md" format="markdown"/>
    <topicref href="gettingstarted/gettinghelp.md" format="markdown"/>
  </topicref>

  <topicref href="downloadingandrunning/overview.md" format="markdown">
    <topicref href="downloadingandrunning/downloadlocations.md" format="markdown"/>
    <topicref href="downloadingandrunning/airgap.md" format="markdown"/>
    <topicref href="downloadingandrunning/verification.md" format="markdown"/>
    <topicref href="downloadingandrunning/includingexcluding.md" format="markdown"/>
    <topicref href="downloadingandrunning/rapidscan.md" format="markdown"/>
    <topicref href="downloadingandrunning/status-file.md" format="markdown"/>
    <topicref href="downloadingandrunning/proxies.md" format="markdown"/>
    <topicref href="downloadingandrunning/concurrent.md" format="markdown"/>
    <topicref href="downloadingandrunning/runincontainer.md" format="markdown"/>
  </topicref>

  <topicref href="components/overview.md" format="markdown">
    <topicref href="components/tools.md" format="markdown"/>
    <topicref href="components/detectors.md" format="markdown"/>
    <topicref href="components/inspectors.md" format="markdown"/>
  </topicref>

  <topicref href="configuring/overview.md" format="markdown">
    <topicref href="configuring/commandline.md" format="markdown"/>
    <topicref href="configuring/envvars.md" format="markdown"/>
    <topicref href="configuring/configfile.md" format="markdown"/>
    <topicref href="configuring/profiles.md" format="markdown"/>
    <topicref href="configuring/othermethods.md" format="markdown"/>
    <topicref href="configuring/credentials.md" format="markdown"/>
    <topicref href="configuring/pathproperties.md" format="markdown"/>
    <topicref href="configuring/propertywildcards.md" format="markdown"/>

    <topicref href="scripts/overview.md" format="markdown">
      <topicref href="scripts/script-escaping-special-characters.md" format="markdown"/>
    </topicref>

    <topicref href="naming/projectversionscannaming.md" format="markdown"/>
    <topicref href="naming/aggregation.md" format="markdown"/>
  </topicref>

  <topicref href="properties/all-properties.md" format="markdown">
    <topicref href="properties/basic-properties.md" format="markdown"/>
    <topicref href="properties/deprecated-properties.md" format="markdown"/>

    <topicref href="properties/configuration/overview.md" format="markdown">
      <topicref href="properties/configuration/binary-scanner.md" format="markdown"/>
      <topicref href="properties/configuration/blackduck-server.md" format="markdown"/>
      <topicref href="properties/configuration/cleanup.md" format="markdown"/>
      <topicref href="properties/configuration/debug.md" format="markdown"/>
      <topicref href="properties/configuration/default.md" format="markdown"/>
      <topicref href="properties/configuration/detector.md" format="markdown"/>
      <topicref href="properties/configuration/general.md" format="markdown"/>
      <topicref href="properties/configuration/impact-analysis.md" format="markdown"/>
      <topicref href="properties/configuration/logging.md" format="markdown"/>
      <topicref href="properties/configuration/paths.md" format="markdown"/>
      <topicref href="properties/configuration/project.md" format="markdown"/>
      <topicref href="properties/configuration/proxy.md" format="markdown"/>
      <topicref href="properties/configuration/report.md" format="markdown"/>
      <topicref href="properties/configuration/signature-scanner.md" format="markdown"/>
    </topicref>

    <topicref href="properties/detectors/overview.md" format="markdown">
      <topicref href="properties/detectors/bazel.md" format="markdown"/>
      <topicref href="properties/detectors/bitbake.md" format="markdown"/>
      <topicref href="properties/detectors/conan.md" format="markdown"/>
      <topicref href="properties/detectors/conda.md" format="markdown"/>
      <topicref href="properties/detectors/cpan.md" format="markdown"/>
      <topicref href="properties/detectors/dart.md" format="markdown"/>
      <topicref href="properties/detectors/docker.md" format="markdown"/>
      <topicref href="properties/detectors/go.md" format="markdown"/>
      <topicref href="properties/detectors/gradle.md" format="markdown"/>
      <topicref href="properties/detectors/hex.md" format="markdown"/>
      <topicref href="properties/detectors/lerna.md" format="markdown"/>
      <topicref href="properties/detectors/maven.md" format="markdown"/>
      <topicref href="properties/detectors/npm.md" format="markdown"/>
      <topicref href="properties/detectors/nuget.md" format="markdown"/>
      <topicref href="properties/detectors/packagist.md" format="markdown"/>
      <topicref href="properties/detectors/pear.md" format="markdown"/>
      <topicref href="properties/detectors/pip.md" format="markdown"/>
      <topicref href="properties/detectors/python.md" format="markdown"/>
      <topicref href="properties/detectors/ruby.md" format="markdown"/>
      <topicref href="properties/detectors/sbt.md" format="markdown"/>
      <topicref href="properties/detectors/yarn.md" format="markdown"/>
    </topicref>
  </topicref>

  <topicref href="results/overview.md" format="markdown">
    <topicref href="results/reports.md" format="markdown"/>
  </topicref>

  <topicref href="troubleshooting/overview.md" format="markdown">
    <topicref href="troubleshooting/diagnosticmode.md" format="markdown"/>
    <topicref href="troubleshooting/exit-codes.md" format="markdown"/>
    <topicref href="troubleshooting/solutions.md" format="markdown"/>
    <topicref href="troubleshooting/usage-metrics.md" format="markdown"/>
  </topicref>

  <topicref href="packagemgrs/overview.md" format="markdown">
    <topicref href="packagemgrs/bazel.md" format="markdown"/>
    <topicref href="packagemgrs/bitbake.md" format="markdown"/>
    <topicref href="packagemgrs/cargo.md" format="markdown"/>
    <topicref href="packagemgrs/carthage.md" format="markdown"/>
    <topicref href="packagemgrs/clang.md" format="markdown"/>
    <topicref href="packagemgrs/conan.md" format="markdown"/>
    <topicref href="packagemgrs/conda.md" format="markdown"/>
    <topicref href="packagemgrs/dart.md" format="markdown"/>
    <topicref href="packagemgrs/docker-images.md" format="markdown"/>
    <topicref href="packagemgrs/golang.md" format="markdown"/>
    <topicref href="packagemgrs/gradle.md" format="markdown"/>
    <topicref href="packagemgrs/hex.md" format="markdown"/>
    <topicref href="packagemgrs/lerna.md" format="markdown"/>
    <topicref href="packagemgrs/maven.md" format="markdown"/>
    <topicref href="packagemgrs/npm.md" format="markdown"/>
    <topicref href="packagemgrs/nuget.md" format="markdown"/>
    <topicref href="packagemgrs/pnpm.md" format="markdown"/>
    <topicref href="packagemgrs/python.md" format="markdown"/>
    <topicref href="packagemgrs/sbt.md" format="markdown"/>
    <topicref href="packagemgrs/yarn.md" format="markdown"/>
    <topicref href="packagemgrs/yocto-bitbake.md" format="markdown"/>
  </topicref>

  <keydef keys="company_name"><topicmeta><keywords><keyword>Synopsys</keyword></keywords></topicmeta></keydef>
  <keydef keys="division_name"><topicmeta><keywords><keyword>Synopsys SIG</keyword></keywords></topicmeta></keydef>
  <keydef keys="binary_repo_type"><topicmeta><keywords><keyword>Artifactory</keyword></keywords></topicmeta></keydef>
  <keydef keys="solution_name"><topicmeta><keywords><keyword>Synopsys Detect</keyword></keywords></topicmeta></keydef>
  <keydef keys="project_name"><topicmeta><keywords><keyword>synopsys-detect</keyword></keywords></topicmeta></keydef>
  <keydef keys="source_repo_organization"><topicmeta><keywords><keyword>blackducksoftware</keyword></keywords></topicmeta></keydef>
  <keydef keys="image_repo_organization"><topicmeta><keywords><keyword>blackducksoftware</keyword></keywords></topicmeta></keydef>
  <keydef keys="blackduck_release_page"><topicmeta><keywords><keyword>https://github.com/blackducksoftware/hub/releases</keyword></keywords></topicmeta></keydef>
  <keydef keys="bash_script_name"><topicmeta><keywords><keyword>detect7.sh</keyword></keywords></topicmeta></keydef>
  <keydef keys="powershell_script_name"><topicmeta><keywords><keyword>detect7.ps1</keyword></keywords></topicmeta></keydef>
  <keydef keys="binary_repo_url_base"><topicmeta><keywords><keyword>https://sig-repo.synopsys.com</keyword></keywords></topicmeta></keydef>
  <keydef keys="binary_repo_ui_url_base"><topicmeta><keywords><keyword>https://sig-repo.synopsys.com/ui/repos/tree/General</keyword></keywords></topicmeta></keydef>
  <keydef keys="binary_repo_repo"><topicmeta><keywords><keyword>bds-integrations-release</keyword></keywords></topicmeta></keydef>
  <keydef keys="binary_repo_pkg_path"><topicmeta><keywords><keyword>com/synopsys/integration</keyword></keywords></topicmeta></keydef>
  <keydef keys="script_repo_url_base"><topicmeta><keywords><keyword>https://detect.synopsys.com</keyword></keywords></topicmeta></keydef>
  <keydef keys="spring_boot_config_doc_url"><topicmeta><keywords><keyword>https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html</keyword></keywords></topicmeta></keydef>
  <keydef keys="blackduck_product_name"><topicmeta><keywords><keyword>Black Duck</keyword></keywords></topicmeta></keydef>
  <keydef keys="coverity_product_name"><topicmeta><keywords><keyword>Coverity</keyword></keywords></topicmeta></keydef>
  <keydef keys="blackduck_signature_scanner_name"><topicmeta><keywords><keyword>Black Duck Signature Scanner</keyword></keywords></topicmeta></keydef>
  <keydef keys="blackduck_signature_scan_act"><topicmeta><keywords><keyword>Black Duck signature scan</keyword></keywords></topicmeta></keydef>
  <keydef keys="blackduck_binary_scan_capability"><topicmeta><keywords><keyword>Black Duck - Binary Analysis</keyword></keywords></topicmeta></keydef>
  <keydef keys="polaris_product_name"><topicmeta><keywords><keyword>Polaris</keyword></keywords></topicmeta></keydef>
  <keydef keys="dockerinspector_name"><topicmeta><keywords><keyword>Black Duck Docker Inspector</keyword></keywords></topicmeta></keydef>
  <keydef keys="blackduck_kb"><topicmeta><keywords><keyword>Black Duck KnowledgeBase</keyword></keywords></topicmeta></keydef>
  <keydef keys="impact_analysis_name"><topicmeta><keywords><keyword>Vulnerability Impact Analysis Tool</keyword></keywords></topicmeta></keydef>
  <keydef keys="professional_services"><topicmeta><keywords><keyword>Synopsys Software Integrity Group Client Services</keyword></keywords></topicmeta></keydef>

</map>

