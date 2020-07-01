/**
 * configuration
 *
 * Copyright (c) 2020 Synopsys, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.configuration.property;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

/**
 * This is the most basic property.
 * It has no type information and a value cannot be retrieved for it (without a subclass).
 **/
public abstract class Property {
    public Property(String key) {
        this.key = key;
    }

    @NotNull
    private final String key;

    public boolean isCaseSensitive() {
        return false;
    }

    public boolean isOnlyExampleValues() {
        return false;
    }

    public List<String> listExampleValues() {
        return new ArrayList<>();
    }

    public String describeType() {
        return null;
    }

    public String describeDefault() {
        return null;
    }

    public boolean isCommaSeparated() {
        return false;
    }

    public String getKey() {
        return key;
    }

}
