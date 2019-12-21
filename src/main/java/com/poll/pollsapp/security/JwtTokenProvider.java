package com.poll.pollsapp.security;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
	
	@Value("${app.jwtSecret}")
	private String jwtsecret;
	
	@Value("${app.jwtExpirationInMs}")
	private int expirationTime;
	
	public String generateToken(Authentication authentication)
	{
		UserPrincipal userprincipal = (UserPrincipal)authentication.getPrincipal();
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + expirationTime);
		return Jwts.builder().setSubject(Long.toString(userprincipal.getId())).setExpiration(expiryDate)
				.setIssuedAt(now)
				.signWith(SignatureAlgorithm.HS512, jwtsecret).compact();
	}
	
	public Long getUserIDfromJWT(String token)
	{
		Claims claim = Jwts.parser()
				.setSigningKey(jwtsecret)
				.parseClaimsJws(token)
				.getBody();
		
		return Long.parseLong(claim.getSubject());
	}
		
	public boolean validateToken(String authToken)
	{
		try
		{
			Jwts.parser().setSigningKey(jwtsecret).parseClaimsJws(authToken);
			return true;
		}
		 catch (SignatureException ex) {
	            logger.error("Invalid JWT signature");
	        } catch (MalformedJwtException ex) {
	            logger.error("Invalid JWT token");
	        } catch (ExpiredJwtException ex) {
	            logger.error("Expired JWT token");
	        } catch (UnsupportedJwtException ex) {
	            logger.error("Unsupported JWT token");
	        } catch (IllegalArgumentException ex) {
	            logger.error("JWT claims string is empty.");
	        }
	        return false;
	}
	
}
