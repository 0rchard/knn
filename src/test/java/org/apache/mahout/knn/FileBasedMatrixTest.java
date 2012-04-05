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

import org.apache.mahout.knn.generate.MultiNormal;
import org.apache.mahout.math.DenseMatrix;
import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.MatrixSlice;
import org.apache.mahout.math.function.Functions;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;

public class FileBasedMatrixTest {
    @Test
    public void testSetData() throws IOException {
        File f = File.createTempFile("matrix", ".m");
        f.deleteOnExit();

        Matrix m0 = new DenseMatrix(100000, 30);
        MultiNormal gen = new MultiNormal(30);
        for (MatrixSlice row : m0) {
            row.vector().assign(gen.sample());
        }

        FileBasedMatrix.writeMatrix(f, m0);

        FileBasedMatrix m = new FileBasedMatrix(100000, 30);
        m.setData(f);

        assertEquals(0, m0.minus(m).aggregate(Functions.MAX, Functions.ABS), 1e-8);

        int i = 0;
        for (MatrixSlice row : m) {
            assertEquals(0, row.vector().minus(m0.viewRow(i++)).norm(1), 1e-8);
        }
    }
}
