package com.qf.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 合同请求头属性配置类
 * 
 * @author SenWu
 *
 */
@Component
@ConfigurationProperties(prefix = "contract.client")
public class ContractProperties {

	/**
	 * 系统的租户 ID
	 */
	private String tenantId;

	/**
	 * 系统 ID
	 */
	private String clientId;

	/**
	 * 系统密钥
	 */
	private String secret;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

}
