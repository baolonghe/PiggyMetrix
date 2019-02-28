package com.piggymetrics.account.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import com.piggymetrics.account.service.security.CustomUserInfoTokenServices;

import feign.RequestInterceptor;

/**
 * @author cdov
 */
@Configuration
@EnableResourceServer // 中间关于template的配置是用于发送http请求的。其余是用来保护配置资源的。
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	// 配置资源服务器信息，如公钥访问地址等。
	private final ResourceServerProperties sso;

	@Autowired
	public ResourceServerConfig(ResourceServerProperties sso) {
		this.sso = sso;
	}

	@Bean // 初始化OAuth2RestTemplate，用户请求创建token时候验证基本信息
	@ConfigurationProperties(prefix = "security.oauth2.client")
	public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
		return new ClientCredentialsResourceDetails();
	}

	@Bean // 使得Feign进行RestTemplate调用的请求前进行token拦截。 如果不存在token则需要auth-server中获取token
			// 利用feign的的扩展RequestInterceptor，对于每次请求我们自动设置Oauth2的AccessToken 头信息
	public RequestInterceptor oauth2FeignRequestInterceptor() {
		return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientCredentialsResourceDetails());
	}

	@Bean // 创建template
	public OAuth2RestTemplate clientCredentialsRestTemplate() {
		return new OAuth2RestTemplate(clientCredentialsResourceDetails());
	}

	@Bean // 用来令牌的加载以及读取，根据userInfouri，通过restTemplate去获取用户信息。然后解析令牌
	public ResourceServerTokenServices tokenServices() {
		return new CustomUserInfoTokenServices(sso.getUserInfoUri(), sso.getClientId());
	}

	@Override // 会覆盖，SecurityConfigAdpter ，配置需要认证的服务。
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/demo").permitAll().anyRequest().authenticated();
	}
}
