package br.edu.undf.sga_ic.infra.security;

import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import br.edu.undf.sga_ic.enums.UsuarioRole;
import br.edu.undf.sga_ic.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtService {

	private final JwtProperties jwtProperties;

	private final Set<String> invalidatedTokens = new HashSet<>();

	public void invalidateToken(String token) {
		invalidatedTokens.add(token);
	}

	public boolean isTokenInvalid(String token) {
		return invalidatedTokens.contains(token);
	}

	public String getJwtFromCookies(HttpServletRequest request) {
		return Optional.ofNullable(WebUtils.getCookie(request, jwtProperties.getJwtCookieName())).map(Cookie::getValue)
				.orElse(null);
	}

	public ResponseCookie generateJwtCookie(Long usuarioId, UsuarioRole usuarioRole) {
		String jwt = generateTokenFromUserDetails(usuarioId, usuarioRole.name());
		return createCookie(jwtProperties.getJwtCookieName(), jwt, 3600);
	}

	public ResponseCookie getCleanJwtCookie() {
		return createCookie(jwtProperties.getJwtCookieName(), null, 0);
	}

	public ResponseCookie createCookie(String cookieName, String jwtValue, long maxAge) {
		return ResponseCookie.from(cookieName, jwtValue).path("/").maxAge(maxAge).httpOnly(true).secure(true)
				.sameSite("Strict").build();
	}

	public String generateTokenFromUserDetails(Long usuarioId, String usuarioRole) {
		return Jwts.builder().claim("userRole", usuarioRole).claim("usuarioId", usuarioId).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtProperties.getExpirationMs()))
				.signWith(key(), SignatureAlgorithm.HS256).compact();
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
	}

	public boolean validateJwtToken(String authToken) {
		try {
			if (isTokenInvalid(authToken)) {
				log.error("O token encontrado está inválido.");
				return false;
			}
			Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
			return true;
		} catch (MalformedJwtException e) {
			log.error("Token jwt inválido: " + e);
			return false;
		} catch (ExpiredJwtException e) {
			log.error("Token jwt expirado: " + e);
			return false;
		} catch (UnsupportedJwtException e) {
			log.error("Token jwt não é compatível: " + e);
			return false;
		} catch (IllegalArgumentException e) {
			log.error("A string jwt está vazia: " + e);
			return false;
		}
	}

	public String getUsuarioRoleFromJwtToken(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
		return claims.get("userRole", String.class);
	}

	public Long getUsuarioIdFromJwtToken(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
		return claims.get("usuarioId", Long.class);
	}
}
