/*
 * Copyright (C) 2014 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stratio.cassandra.lucene.search.condition.builder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stratio.cassandra.lucene.search.condition.BooleanCondition;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * {@link ConditionBuilder} for building a new {@link BooleanCondition}.
 *
 * @author Andres de la Pena {@literal <adelapena@stratio.com>}
 */
public class BooleanConditionBuilder extends ConditionBuilder<BooleanCondition, BooleanConditionBuilder> {

    /** The mandatory conditions. */
    @JsonProperty("must")
    protected final List<ConditionBuilder<?, ?>> must = new LinkedList<>();

    /** The optional conditions. */
    @JsonProperty("should")
    protected final List<ConditionBuilder<?, ?>> should = new LinkedList<>();

    /** The mandatory not conditions. */
    @JsonProperty("not")
    protected final List<ConditionBuilder<?, ?>> not = new LinkedList<>();

    /** The max boolean query clauses. */
    @JsonProperty("max_clauses")
    protected Integer maxClauses = null;

    /**
     * Returns this builder with the specified mandatory conditions.
     *
     * @param builders the conditions to be added
     * @return this builder with the specified conditions
     */
    public BooleanConditionBuilder must(ConditionBuilder<?, ?>... builders) {
        must.addAll(Arrays.asList(builders));
        return this;
    }

    /**
     * Returns this builder with the specified optional conditions.
     *
     * @param builders the conditions to be added
     * @return this builder with the specified conditions
     */
    public BooleanConditionBuilder should(ConditionBuilder<?, ?>... builders) {
        should.addAll(Arrays.asList(builders));
        return this;
    }

    /**
     * Returns this builder with the specified mandatory not conditions.
     *
     * @param builders the conditions to be added
     * @return this builder with the specified conditions
     */
    public BooleanConditionBuilder not(ConditionBuilder<?, ?>... builders) {
        not.addAll(Arrays.asList(builders));
        return this;
    }

    /**
     * Returns this builder with the specified max booleqna query clauses
     *
     * @param maxClauses teh booleanQuery allowed max clauses
     * @return this builder with the specified conditions
     */
    public BooleanConditionBuilder maxClauses(Integer maxClauses) {
        this.maxClauses = maxClauses;
        return this;
    }

    /**
     * Returns the {@link BooleanCondition} represented by this builder.
     *
     * @return a new boolean condition
     */
    @Override
    public BooleanCondition build() {
        return new BooleanCondition(boost,
                                    must.stream().map(ConditionBuilder::build).collect(toList()),
                                    should.stream().map(ConditionBuilder::build).collect(toList()),
                                    not.stream().map(ConditionBuilder::build).collect(toList()),
                                    maxClauses);
    }
}
