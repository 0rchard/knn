/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.mahout.knn;

import org.apache.mahout.math.Vector;

/**
 * Decorates a vector with a floating point weight.
 */
public class WeightedVector extends DelegatingVector implements Comparable<WeightedVector> {
    private final double weight;

    public WeightedVector(Vector v, double weight) {
        super(v);
        this.weight = weight;
    }
    
    private WeightedVector(Vector v, Vector projection) {
        super(v);
        this.weight = v.dot(projection);
    }
    
    public static WeightedVector project(Vector v, Vector projection) {
        return new WeightedVector(v, projection);
    }

    public double getWeight() {
        return weight;
    }


    @Override
    public int compareTo(WeightedVector other) {
        int r = Double.compare(weight, other.getWeight());
        if (r == 0) {
            return hashCode() - other.hashCode();
        } else {
            return r;
        }
    }
}
