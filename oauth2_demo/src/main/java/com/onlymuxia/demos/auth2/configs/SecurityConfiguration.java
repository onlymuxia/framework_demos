package com.onlymuxia.demos.auth2.configs;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.onlymuxia.demos.auth2.filters.JWTLoginFilter;
import com.onlymuxia.demos.auth2.filters.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
        .antMatchers(HttpMethod.POST, "/users/register").permitAll()
        .antMatchers(HttpMethod.POST, "/login").permitAll()
        .antMatchers("/order/**").authenticated()
        .anyRequest().authenticated()
        .and()
        .addFilter(new JWTLoginFilter(authenticationManager()))
        .addFilter(new JwtAuthenticationFilter(authenticationManager()));
}

//	
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
		// return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		AuthenticationManager manager = super.authenticationManagerBean();
		return manager;
	}
}