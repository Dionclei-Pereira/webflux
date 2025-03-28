package me.dionclei.webflux.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	
	@Autowired
	SecurityFilter filter;
	
	@Bean
	SecurityWebFilterChain config(ServerHttpSecurity http) {
		return http.csrf(c -> c.disable())
				.authorizeExchange(exchange -> exchange
						.pathMatchers(HttpMethod.POST, "/auth/**").permitAll()
						.pathMatchers(HttpMethod.POST, "/users").hasAuthority("ROLE_ADMIN")
						.anyExchange().authenticated()
				)
				.addFilterBefore(filter, SecurityWebFiltersOrder.AUTHORIZATION)
				.build();
	}
	
    @Bean
    PasswordEncoder password() {
    	return new BCryptPasswordEncoder();
    }
	
}
