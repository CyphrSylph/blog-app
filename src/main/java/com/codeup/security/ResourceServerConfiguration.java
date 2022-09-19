package com.codeup.security;

import com.codeup.errors.CustomAccessDeniedHandler;
import com.codeup.errors.CustomAuthenticationEntryPoint;
import com.codeup.errors.CustomAccessDeniedHandler;
import com.codeup.errors.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

// This class is responsible for the actual securing of individual endpoints defined in our controllers.
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public ResourceServerConfiguration(CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    // This method allows us to simply define the overall context of our resource. Often you may hear this referred to as a realm, resource, or audience.
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("api");
    }

    // This is where define top-level restrictions to protected resources, as well as declare what endpoints are allowed by everyone (even anonymous users).
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/users")
                .hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/api/posts")
                .hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**")
                .permitAll()
                .antMatchers("/api/users/create")
                .permitAll()
                .antMatchers("/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(new CustomAccessDeniedHandler());
    }
}

