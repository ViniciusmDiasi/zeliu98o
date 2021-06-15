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
package com.alipay.sofa.rpc.register.holder;

import com.alipay.sofa.rpc.common.SofaBootRpcRuntimeException;
import com.alipay.sofa.rpc.filter.Filter;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * 持有从 XML 解析的 Filter 实例或者 id。
 *
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
public class RpcFilterHolder {

    private static List<String> filterIds     = new CopyOnWriteArrayList<String>();

    private static List<String> filterClasses = new CopyOnWriteArrayList<String>();

    private static boolean      alreadyLoad   = false;
    private static final Object LOAD_LOCK     = new Object();

    private static List<Filter> filters       = new CopyOnWriteArrayList<Filter>();

    /**
     * 增加 Filter id
     * @param filterId id
     */
    public static void addFilterId(String filterId) {
        if (StringUtils.hasText(filterId)) {
            filterIds.add(filterId);
        }
    }

    /**
     * 增加 Filter 实例
     * @param filterClass 实例
     */
    public static void addFilterClass(String filterClass) {
        if (StringUtils.hasText(filterClass)) {
            filterClasses.add(filterClass);
        }
    }

    /**
     * 获取所有的 Filter 实例
     * @param applicationContext Spring 上下文
     * @return 所有的 Filter 实例
     */
    public static List<Filter> getFilters(ApplicationContext applicationContext) {

        if (applicationContext != null) {
            if (!alreadyLoad) {
                synchronized (LOAD_LOCK) {
                    if (!alreadyLoad) {
                        loadFilters(applicationContext);
                        alreadyLoad = true;
                    }
                }
            }

            return filters;

        } else {
            throw new SofaBootRpcRuntimeException("The applicationContext should not be null");
        }
    }

    /**
     * 加载并持有所有的 Filter id 或实例
     * @param applicationContext Spring 上下文
     */
    public static void loadFilters(ApplicationContext applicationContext) {
        for (String filterId : filterIds) {
            filters.add((applicationContext.getBean(filterId, Filter.class)));
        }
        for (String clazz : filterClasses) {
            Class filterClass = null;
            try {
                filterClass = Class.forName(clazz);
            } catch (ClassNotFoundException e) {
                throw new SofaBootRpcRuntimeException("Can not find filter class " + clazz + " ", e);
            }
            if (Filter.class.isAssignableFrom(filterClass)) {
                try {
                    filters.add((Filter) filterClass.newInstance());
                } catch (Exception e) {
                    throw new SofaBootRpcRuntimeException("Error happen when create instance of " + filterClass + " ",
                        e);
                }
            } else {
                throw new SofaBootRpcRuntimeException(
                    "The class of " + clazz + " should be a subclass of Filter");
            }
        }
    }
}