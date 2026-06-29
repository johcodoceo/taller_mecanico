package com.taller_mecanico.api_gateway.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long startTime = System.currentTimeMillis();
        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod() == null
                ? "UNKNOWN"
                : exchange.getRequest().getMethod().toString();

        logger.info("event=gateway_request_start method={} path={}", method, path);

        if (path.contains("/auth/login") || path.contains("/auth/register")) {
            logger.info("event=gateway_auth_bypass method={} path={}", method, path);
            return chain.filter(exchange)
                    .doFinally(signalType -> logger.info(
                            "event=gateway_request_complete method={} path={} status={} durationMs={}",
                            method, path, exchange.getResponse().getStatusCode(), System.currentTimeMillis() - startTime));
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("event=gateway_auth_rejected reason=missing_bearer_token method={} path={}", method, path);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete()
                    .doFinally(signalType -> logger.info(
                            "event=gateway_request_complete method={} path={} status={} durationMs={}",
                            method, path, exchange.getResponse().getStatusCode(), System.currentTimeMillis() - startTime));
        }

        String token = authHeader.substring(7);
        boolean valido = jwtUtil.validateToken(token);

        if (!valido) {
            logger.warn("event=gateway_auth_rejected reason=invalid_token method={} path={}", method, path);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete()
                    .doFinally(signalType -> logger.info(
                            "event=gateway_request_complete method={} path={} status={} durationMs={}",
                            method, path, exchange.getResponse().getStatusCode(), System.currentTimeMillis() - startTime));
        }

        String username = jwtUtil.extractUsername(token);
        logger.info("event=gateway_auth_success username={} method={} path={}", username, method, path);

        return chain.filter(exchange)
                .doFinally(signalType -> logger.info(
                        "event=gateway_request_complete method={} path={} status={} durationMs={}",
                        method, path, exchange.getResponse().getStatusCode(), System.currentTimeMillis() - startTime));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
