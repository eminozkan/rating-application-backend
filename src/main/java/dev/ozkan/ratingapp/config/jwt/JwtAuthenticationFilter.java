package dev.ozkan.ratingapp.config.jwt;

import dev.ozkan.ratingapp.config.UserSession;
import dev.ozkan.ratingapp.core.jwt.JwtService;
import dev.ozkan.ratingapp.core.model.user.User;
import dev.ozkan.ratingapp.core.model.user.UserRole;
import dev.ozkan.ratingapp.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;


    JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader= request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return ;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        UserRole role = UserRole.STANDARD;
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            var user = (User) userDetails;
            if (user.getRole() == UserRole.ADMIN){
                role = UserRole.ADMIN;
            }
            if(jwtService.isTokenValid(jwt,userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        String userId = userRepository.getByEmail(userEmail).get().getUserId();
        var session = new UserSession(userId,userEmail,role);
        request.setAttribute("SESSION",session);
        filterChain.doFilter(request,response);
    }

}