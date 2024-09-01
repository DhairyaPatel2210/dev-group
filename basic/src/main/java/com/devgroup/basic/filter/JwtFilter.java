package com.devgroup.basic.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.devgroup.basic.services.UserWebSecurityService;
import com.devgroup.basic.utilities.JWTUtil;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JWTUtil jwtUtility;
	
	@Autowired
	private UserWebSecurityService userService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			
			Cookie[] cookies = request.getCookies();
			String authorization = cookies!= null && cookies.length != 0 ? cookies[0].getValue() : null;
			String token = null;
			String userName = null;
			
			if(authorization != null && authorization.startsWith("Bearer%20")) {
				token = authorization.substring(9);
				userName = jwtUtility.getUsernameFromToken(token);
			}
			
			if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userService.loadUserByUsername(userName);
				
				if(jwtUtility.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken userpasstoken = 
							new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
					userpasstoken.setDetails( 
							new WebAuthenticationDetailsSource().buildDetails(request)
					);
					
					SecurityContextHolder.getContext().setAuthentication(userpasstoken);
				}
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		filterChain.doFilter(request, response);
	}
	
}