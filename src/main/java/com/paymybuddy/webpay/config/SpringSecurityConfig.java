package com.paymybuddy.webpay.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.paymybuddy.webpay.service.CustomUserDetailsService;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	
	
	@Autowired
    private CustomUserDetailsService userDetailsService ;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/user/**").hasAnyAuthority("ADMIN","USER")
                .antMatchers("/user/create").permitAll()
                .antMatchers("/","/log/submit").permitAll()
                .and().formLogin()
                .defaultSuccessUrl("/user")
                .and().logout()    //logout configuration
                .logoutUrl("/user/disconnect")
                .logoutSuccessUrl("/")
                .and().exceptionHandling()
                .and()
                .csrf()
                .and()
                .rememberMe().tokenValiditySeconds(20000);
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1);}

    @Override
    public void configure(WebSecurity web){
        web
                .ignoring()
                .antMatchers("/resources/**");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

    }
}
