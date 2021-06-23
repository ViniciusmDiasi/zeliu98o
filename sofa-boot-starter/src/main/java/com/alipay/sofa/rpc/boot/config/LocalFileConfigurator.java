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

import org.springframework.util.StringUtils;

/**
 *
 * 本地注册中心配置
 * 配置格式：com.alipay.sofa.rpc.registry.protocol=local:/home/registry
 *
 * @author liangen
 * @version $Id: LocalFileConfigurator.java, v 0.1 2018年04月17日 下午2:44 liangen Exp $
 */
public class LocalFileConfigurator {

    /**
     * 缓存文件地址
     */
    private static String  FILE;

    /**
     * 是否已经解析配置
     */
    private static boolean alreadyParse = false;

    /**
     * 获取缓存文件地址
     * @return 缓存文件地址
     */
    public static String getFile() {

        return FILE;

    }

    /**
     * 解析配置 value
     * @param config 配置 value
     */
    public static void parseConfig(String config) {
        if (StringUtils.hasText(config) && config.startsWith("local") && config.length() > 5) {

            FILE = config.substring(6);
        }
    }

    /**
     * 读取配置 key ,获取其 value 进行解析。
     */
    public static void parseConfig() {
        if (!alreadyParse) {
            String config = SofaBootRpcConfig.getPropertyAllCircumstances(SofaBootRpcConfigConstants.REGISTRY_PROTOCOL);
            parseConfig(config);

            alreadyParse = true;
        }
    }

    public static void setFile(String file) {
        LocalFileConfigurator.FILE = file;
    }

}