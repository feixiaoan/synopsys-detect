/*
 * detector
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.detector.base;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum DetectorType {
    BITBAKE,
    CARGO,
    COCOAPODS,
    CONAN,
    CONDA,
    CPAN,
    CRAN,
    GIT, // Git just provides project name and version info. It does not provide any information about project dependencies.
    GO_MOD,
    GO_DEP,
    GO_VNDR,
    GO_VENDOR,
    GO_GRADLE,
    GRADLE,
    HEX,
    LERNA,
    MAVEN,
    NPM,
    NUGET,
    PACKAGIST,
    PEAR,
    PIP,
    RUBYGEMS,
    SBT,
    SWIFT,
    YARN,
    CLANG;

    protected static final List<String> POSSIBLE_NAMES = Arrays.stream(DetectorType.values()).map(DetectorType::name).collect(Collectors.toList());

    public static List<String> getPossibleNames() {
        return POSSIBLE_NAMES;
    }
}
