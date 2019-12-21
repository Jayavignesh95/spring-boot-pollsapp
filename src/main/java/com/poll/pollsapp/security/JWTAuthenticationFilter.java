package com.poll.pollsapp.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.poll.pollsapp.repository.UserRepository;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	public CustomUserDetailService userDetails;
	
	@Autowired
	public JwtTokenProvider jwtProvider;

	public static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	public String getTokenfromRequest(HttpServletRequest request)
	{
		String bearerToken = request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken)&& bearerToken.startsWith("Bearer "))
		{
            return bearerToken.substring(7, bearerToken.length());
		}
		
		return null;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try
		{
			String jwt = getTokenfromRequest(request);
			
			if(StringUtils.hasText(jwt)&&jwtProvider.validateToken(jwt))
			{
				Long userID = jwtProvider.getUserIDfromJWT(jwt);
				
				UserDetails userdetails = userDetails.loadUserById(userID);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userdetails,null,userdetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);


			}
		}
		catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }
		filterChain.doFilter(request, response);
		
	}

}
