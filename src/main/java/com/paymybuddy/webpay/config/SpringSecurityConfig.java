package com.paymybuddy.webpay.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
          .authorizeRequests(authz -> authz
            .antMatchers(HttpMethod.GET, "/**").hasAuthority("SCOPE_read")
            .antMatchers(HttpMethod.POST, "/**").hasAuthority("SCOPE_write")
            .antMatchers(HttpMethod.PUT, "/**").hasAuthority("SCOPE_write")
            .antMatchers(HttpMethod.DELETE, "/**").hasAuthority("SCOPE_write")
            .anyRequest().authenticated())
          .oauth2ResourceServer(oauth2 -> oauth2.jwt());
	}
}
