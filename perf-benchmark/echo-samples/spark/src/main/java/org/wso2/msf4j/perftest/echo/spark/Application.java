/*
 * Copyright (c) WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.msf4j.perftest.echo.spark;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import static spark.Spark.port;
import static spark.Spark.post;

/**
 * Application
 */
public class Application {
    public static void main(String[] args) throws Exception {
        port(8080);
        post("/EchoService/echo", (req, res) -> req.body());
        post("/EchoService/dbecho", (req, res) -> {
            java.nio.file.Path tempfile = Files.createTempFile(UUID.randomUUID().toString(), ".tmp");
            Files.write(tempfile, req.bodyAsBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            String returnStr = new String(Files.readAllBytes(tempfile), Charset.defaultCharset());
            Files.delete(tempfile);
            return returnStr;
        });
    }
}
