package com.secuirty.secuirtymanagement.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.secuirty.secuirtymanagement.service.implement.userDetailServiceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{


    private Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private userDetailServiceimpl userDetails;
    @Autowired
    private jwtUtils jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                
        final String RequestTokenHeader = request.getHeader("Authorization");
        log.info("RequestTokenHeader: "+RequestTokenHeader);
        String username = null;
        String jwttoken = null;

        if(RequestTokenHeader != null && RequestTokenHeader.startsWith("Bearer ")){
            jwttoken = RequestTokenHeader.substring(7);
            log.info("jwttoken: "+jwttoken);
            try{
                username = this.jwtUtil.extractUsername(jwttoken);
                log.info("username: "+username);
            
            }catch(Exception e){
                log.error("Cannot extract username from token / expirerd token");
            }

        }else{
            log.error("Invalid token, not start with bearer string");
        }

        // validate token
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            final UserDetails userDetail = this.userDetails.loadUserByUsername(username);
             log.info("userDetails: "+userDetail.getAuthorities());
            log.info("User: "+username+" is authenticated");

            if(this.jwtUtil.validateToken(jwttoken, userDetail)){
                // token is valid

                UsernamePasswordAuthenticationToken uPAT = new UsernamePasswordAuthenticationToken(userDetail,null,userDetail.getAuthorities());
                uPAT.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.info("Authenticated user: "+username+", setting security context");
                SecurityContextHolder.getContext().setAuthentication(uPAT);
            }
        }else{
            log.error("Token not valide");
        }
        log.info("Before filter chaining");       
    filterChain.doFilter(request, response);
    log.info("After filter chaining");
    }
    
    
}
    
        

    

