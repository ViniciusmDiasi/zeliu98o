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
package com.alipay.sofa.rpc.register.binding;

import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.alipay.sofa.rpc.config.SofaBootRpcConfigConstants;
import com.alipay.sofa.rpc.register.param.RpcBindingParam;
import com.alipay.sofa.runtime.spi.binding.AbstractBinding;
import com.alipay.sofa.runtime.spi.health.HealthResult;
import org.springframework.context.ApplicationContext;
import org.w3c.dom.Element;

/**
 * rpc binding implementation
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
public abstract class RpcBinding extends AbstractBinding {

    protected String             appName;

    protected String             beanId;

    protected RpcBindingParam    rpcBindingParam;

    /**
     * Spring 上下文
     */
    protected ApplicationContext applicationContext;

    /**
     * 是否是服务引用方
     */
    protected boolean            inBinding;

    /**
     * the ConsumerConfig 。在服务引用方才有值。
     */
    protected ConsumerConfig     consumerConfig;

    public RpcBinding(RpcBindingParam bindingParam, ApplicationContext applicationContext, boolean inBinding) {
        this.rpcBindingParam = bindingParam;
        this.applicationContext = applicationContext;
        this.inBinding = inBinding;
    }

    public String getBootStrap() {

        String bindingType = getBindingType().getType();
        if (bindingType.equalsIgnoreCase(SofaBootRpcConfigConstants.RPC_PROTOCOL_DUBBO)) {
            return SofaBootRpcConfigConstants.RPC_PROTOCOL_DUBBO;
        } else {
            return SofaBootRpcConfigConstants.RPC_PROTOCOL_BOLT;
        }
    }

    @Override
    public String getURI() {
        return null;
    }

    @Override
    public Element getBindingPropertyContent() {
        return null;
    }

    @Override
    public int getBindingHashCode() {
        return rpcBindingParam.hashCode();
    }

    /**
     * 健康检查
     * @return 健康检查结果
     */
    @Override
    public HealthResult healthCheck() {
        HealthResult result = new HealthResult(getName());

        // health check when reference
        if (inBinding && consumerConfig != null) {
            if (consumerConfig.getConsumerBootstrap().isSubscribed()) {
                result.setHealthy(true);
            } else {
                result.setHealthy(false);
                result.setHealthReport("Addresses unavailable");
            }
        } else {
            result.setHealthy(isHealthy);
        }

        return result;
    }

    /**
     * Getter method for property <tt>appName</tt>.
     *
     * @return property value of appName
     */
    public String getAppName() {
        return appName;
    }

    /**
     * Setter method for property <tt>appName</tt>.
     *
     * @param appName  value to be assigned to property appName
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * Getter method for property <tt>beanId</tt>.
     *
     * @return property value of beanId
     */
    public String getBeanId() {
        return beanId;
    }

    /**
     * Setter method for property <tt>beanId</tt>.
     *
     * @param beanId  value to be assigned to property beanId
     */
    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    /**
     * Getter method for property <tt>rpcBindingParam</tt>.
     *
     * @return property value of rpcBindingParam
     */
    public RpcBindingParam getRpcBindingParam() {
        return rpcBindingParam;
    }

    /**
     * Setter method for property <tt>rpcBindingParam</tt>.
     *
     * @param rpcBindingParam  value to be assigned to property rpcBindingParam
     */
    public void setRpcBindingParam(RpcBindingParam rpcBindingParam) {
        this.rpcBindingParam = rpcBindingParam;
    }

    /**
     * Getter method for property <tt>applicationContext</tt>.
     *
     * @return property value of applicationContext
     */
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * Setter method for property <tt>applicationContext</tt>.
     *
     * @param applicationContext  value to be assigned to property applicationContext
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Getter method for property <tt>inBinding</tt>.
     *
     * @return property value of inBinding
     */
    public boolean isInBinding() {
        return inBinding;
    }

    /**
     * Setter method for property <tt>inBinding</tt>.
     *
     * @param inBinding  value to be assigned to property inBinding
     */
    public void setInBinding(boolean inBinding) {
        this.inBinding = inBinding;
    }

    /**
     * Getter method for property <tt>consumerConfig</tt>.
     *
     * @return property value of consumerConfig
     */
    public ConsumerConfig getConsumerConfig() {
        return consumerConfig;
    }

    /**
     * Setter method for property <tt>consumerConfig</tt>.
     *
     * @param consumerConfig  value to be assigned to property consumerConfig
     */
    public void setConsumerConfig(ConsumerConfig consumerConfig) {
        this.consumerConfig = consumerConfig;
    }
}