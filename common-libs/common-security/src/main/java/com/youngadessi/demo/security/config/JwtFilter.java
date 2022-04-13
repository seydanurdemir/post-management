package com.youngadessi.demo.security.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final JwtUserDetailsService jwtUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String bearerToken=request.getHeader("Authorization");

        try{

            if (StringUtils.isNotBlank(bearerToken)){

                if (bearerToken.startsWith("Bearer")){

                    bearerToken=bearerToken.substring(7);

                    jwtUtils.validateToken(bearerToken);

                    String username=jwtUtils.getUsernameFromToken(bearerToken);

                    UserDetails jwtUserDetails=jwtUserDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtUserDetails, null, jwtUserDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }

            }

        }catch (SignatureException ex) {
            log.error("JwtTokenProvider.validateToken -> Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("JwtTokenProvider.validateToken -> Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("JwtTokenProvider.validateToken -> Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("JwtTokenProvider.validateToken -> Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JwtTokenProvider.validateToken -> JWT claims string is empty.");
        }

        filterChain.doFilter(request,response);

    }


}
