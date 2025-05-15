package br.edu.undf.sga_ic.infra.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SecurityFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwt = jwtService.getJwtFromCookies(request);

		if (jwt != null && jwtService.validateJwtToken(jwt)) {

			Long usuarioId = jwtService.getUsuarioIdFromJwtToken(jwt);
			log.warn("Recebendo requisição de usuário de id: " + usuarioId);

			String usuarioRole = jwtService.getUsuarioRoleFromJwtToken(jwt);

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuarioId,
					null, List.of(() -> usuarioRole));

			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}
}