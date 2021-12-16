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
package com.alipay.sofa.rpc.boot.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.alipay.sofa.rpc.config.RegistryConfig;

/**
 *
 * @author zhuoyu.sjw
 * @version $Id: NacosConfiguratorTest.java, v 0.1 2018-12-03 17:36 zhuoyu.sjw Exp $$
 */
public class NacosConfiguratorTest {

    @Test
    public void buildFromAddress() {
        String address = "nacos://127.0.0.1:8848?cluster=test";

        NacosConfigurator nacosConfigurator = new NacosConfigurator();
        RegistryConfig registryConfig = nacosConfigurator.buildFromAddress(address);

        assertNotNull(registryConfig);
        assertEquals("nacos", registryConfig.getProtocol());
        assertEquals("127.0.0.1:8848", registryConfig.getAddress());
        assertNotNull(registryConfig.getParameters());
        assertEquals("test", registryConfig.getParameter("cluster"));
    }
}