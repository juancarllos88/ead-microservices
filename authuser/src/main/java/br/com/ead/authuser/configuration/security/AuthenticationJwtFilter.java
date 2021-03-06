package br.com.ead.authuser.configuration.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;

public class AuthenticationJwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private UserDetailsService userDetailsService;

	 @Override
	    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
	        try {
	            String jwtStr = getTokenHeader(httpServletRequest);
	            if (jwtStr != null && jwtProvider.validateJwt(jwtStr)) {
	                String username = jwtProvider.getUsernameJwt(jwtStr);
	                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
	                        userDetails, null, userDetails.getAuthorities());
	                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	            }
	        } catch (Exception e) {
	            logger.error("Cannot set User Authentication: {}", e);
	        }
	        filterChain.doFilter(httpServletRequest, httpServletResponse);
	    }

	    private String getTokenHeader(HttpServletRequest request) {
	        String headerAuth = request.getHeader("Authorization");
	        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
	            return headerAuth.substring(7, headerAuth.length());
	        }
	        return null;
	    }

}
