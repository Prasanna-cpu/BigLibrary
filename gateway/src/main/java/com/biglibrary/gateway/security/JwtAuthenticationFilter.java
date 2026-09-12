package com.biglibrary.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthenticationFilter implements WebFilter {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.header:Authorization}")
	private String jwtHeader;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		String authHeader = exchange.getRequest().getHeaders().getFirst(jwtHeader);

		if (jwtSecret == null) {
			return Mono.error(new BadCredentialsException("JWT secret is not configured"));
		}

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String jwt = authHeader.substring(7);
			try {
				SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
				Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();

				String type = (String) claims.get("type");

				if (!"access".equals(type)) {
					return chain.filter(exchange);
				}

				String email = String.valueOf(claims.getSubject());
				String authorities = String.valueOf(claims.get("authorities"));

				List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

				Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorityList);

				return chain.filter(exchange)
						.contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));

			} catch (MalformedJwtException e) {
				return Mono.error(new BadCredentialsException("Invalid JWT token: " + e.getMessage()));
			} catch (ExpiredJwtException e) {
				return Mono.error(new BadCredentialsException("Expired JWT token: " + e.getMessage()));
			} catch (Exception e) {
				return Mono.error(new BadCredentialsException("Exception: " + e.getMessage()));
			}
		}

		return chain.filter(exchange);
	}
}
