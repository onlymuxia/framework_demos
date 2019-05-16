package com.onlymuxia.demos.auth2.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import com.onlymuxia.demos.auth2.filters.JWTLoginFilter;
import com.onlymuxia.demos.auth2.filters.JwtAuthenticationFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Autowired
	@Qualifier("authenticationManagerBean")
	AuthenticationManager authenticationManager;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(AuthorizationServerConfiguration.RESOURCE_ID).stateless(true);;
	}

      @Override
      public void configure(HttpSecurity http) throws Exception {
      	 http.cors().and().csrf().disable().authorizeRequests()
           .antMatchers(HttpMethod.POST, "/users/register").permitAll()
           .antMatchers(HttpMethod.POST, "/login").permitAll()
           .antMatchers("/order/**").authenticated()
           .anyRequest().authenticated()
           .and()
           .addFilter(new JWTLoginFilter(authenticationManager))
           .addFilter(new JwtAuthenticationFilter(authenticationManager)) ;
      }

}