package com.security.secure.jwt;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.secure.serviceImpl.UserDetailsServiceImpl;
import com.security.utils.PropertyUtils;


public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
//	@Autowired
//	PropertyUtils propertyUtils;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			
			
			String[] check_request = request.getRequestURI().split("/");
			String[] u = PropertyUtils.getProperty("skip.url").split(",");
			boolean c = Arrays.asList(u).contains(check_request[3]);
			if(c) {
				filterChain.doFilter(request, response);
			}else {
				String jwt = parseJwt(request);
				if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
					String username = jwtUtils.getUserNameFromJwtToken(jwt);

					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authentication);
					filterChain.doFilter(request, response);
				} else {
					throw new Exception();
					//filterChain.doFilter(request, response);
				}
			}
			
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
			logger.error("Cannot set user authentication: {}", e);
			wrongData(e.getMessage());
			
		}

		
	}

	private String wrongData(String message) {
		return message;
	}
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}
}