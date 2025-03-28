package me.dionclei.webflux.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import me.dionclei.webflux.repositories.UserRepository;
import me.dionclei.webflux.services.TokenService;
import reactor.core.publisher.Mono;

@Component
public class SecurityFilter implements WebFilter{

	@Autowired
	TokenService service;
	@Autowired
	UserRepository repository;
	
	@Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (token != null) {
            try {
                String subject = service.validateToken(token);
                return repository.findUserByEmail(subject)
                        .flatMap(user -> {
                            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                            return chain.filter(exchange)
                            		.contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                        });
            } catch (Exception e) {
                return chain.filter(exchange);
            }
        }
        return chain.filter(exchange);
    }
}
