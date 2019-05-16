package com.onlymuxia.demos.auth2.configs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;


@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
	

	static final String RESOURCE_ID = "order";

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	@Qualifier("authenticationManagerBean")
	AuthenticationManager authenticationManager;
	
	public static TokenStore tokenStore = new InMemoryTokenStore();
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore).authenticationManager(authenticationManager).userDetailsService(userDetailsService);
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception
	{
	   oauthServer.checkTokenAccess("permitAll()");    
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setTokenStore(tokenStore);
		return tokenServices;
		
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        String finalSecret = "{bcrypt}"+new BCryptPasswordEncoder().encode("123456");
//      //配置两个客户端,一个用于password认证一个用于client认证
      clients.inMemory()
      .withClient("client-auth")
              .resourceIds(RESOURCE_ID)
              .authorizedGrantTypes("client_credentials", "refresh_token")
              .scopes("select")
              .authorities("oauth2")
              .secret(finalSecret)
              .and()
              .withClient("user-auth")
              .resourceIds(RESOURCE_ID)
              .authorizedGrantTypes("password", "refresh_token")
              .scopes("select")
              .authorities("oauth2")
              .secret(finalSecret);
	}

}