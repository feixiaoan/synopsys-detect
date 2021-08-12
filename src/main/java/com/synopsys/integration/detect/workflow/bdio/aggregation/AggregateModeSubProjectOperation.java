/*
 * synopsys-detect
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.detect.workflow.bdio.aggregation;

import com.synopsys.integration.bdio.graph.DependencyGraph;
import com.synopsys.integration.detect.configuration.DetectUserFriendlyException;
import com.synopsys.integration.detect.workflow.codelocation.DetectCodeLocation;

import java.io.File;
import java.util.List;

public class AggregateModeSubProjectOperation {

    private final FullAggregateGraph fullAggregateGraph;

    public AggregateModeSubProjectOperation(FullAggregateGraph fullAggregateGraph) {
        this.fullAggregateGraph = fullAggregateGraph;
    }

    public DependencyGraph aggregateCodeLocations(final File sourcePath, final List<DetectCodeLocation> codeLocations) throws DetectUserFriendlyException {
        return fullAggregateGraph.aggregateCodeLocations(new ModernAggregateDependencyCreator(), sourcePath, codeLocations);
    }
}