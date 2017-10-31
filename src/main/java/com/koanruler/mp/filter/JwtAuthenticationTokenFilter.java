package com.koanruler.mp.filter;

import com.koanruler.mp.entity.CustomUserDetails;
import com.koanruler.mp.entity.User;
import com.koanruler.mp.service.CustomUserDetailsService;
import com.koanruler.mp.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by chengyuan on 2017/9/17.
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader(this.tokenHeader);
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            final String authToken = authHeader.substring(tokenHead.length()); // The part after "Bearer "

            if ( jwtTokenUtil.validateToken(authToken)
                    && SecurityContextHolder.getContext().getAuthentication() == null) {

                //直接验证token是否合法来避免昂贵的数据查询
                String account = jwtTokenUtil.getAccountFromToken(authToken);
                logger.info("checking authentication " + account);

                User user = new User();
                user.setAccount(account);
                user.setId(jwtTokenUtil.getUserId(authToken));
                user.setName(jwtTokenUtil.getUserNameFromToken(authToken));
                user.setType(jwtTokenUtil.getUserTypeFromToken(authToken));
                CustomUserDetails userDetails = new CustomUserDetails(user);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                        httpServletRequest));

                logger.info("authenticated user " + account + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);

                //UserDetails userDetails = this.userDetailsService.loadUserByUsername(account);

//                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                            userDetails, null, userDetails.getAuthorities());
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
//                            httpServletRequest));
//
//                    logger.info("authenticated user " + account + ", setting security context");
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
