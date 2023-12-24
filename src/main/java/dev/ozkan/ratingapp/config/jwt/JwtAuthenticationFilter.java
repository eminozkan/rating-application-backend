package dev.ozkan.ratingapp.config.jwt;

import dev.ozkan.ratingapp.config.UserSession;
import dev.ozkan.ratingapp.config.auth.AuthenticationProperties;
import dev.ozkan.ratingapp.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private final List<String> excludedPaths = List.of(
            "/auth/login",
            "/auth/register");

    @Autowired
    private AuthenticationProperties properties;

    JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String path = request.getServletPath();
            if (request.getCookies() == null) {
                filterChain.doFilter(request, response);
                return;
            }

            Optional<Cookie> cookie0 = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals(properties.getCookieName())).findFirst();

            if (cookie0.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }

            Cookie cookie = cookie0.get();
            String jwt = cookie.getValue();

            String userEmail = jwtService.extractUsername(jwt);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                if (jwtService.isTokenValid(jwt, userDetails)) {
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
            request.setAttribute("SESSION", new UserSession(userId, userEmail));
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            final Cookie deleteCookie = new Cookie(properties.getCookieName(), "");
            deleteCookie.setPath(properties.getCookiePath());
            deleteCookie.setHttpOnly(true);
            deleteCookie.setSecure(false);
            deleteCookie.setMaxAge(0);
            response.addCookie(deleteCookie);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            String message = """
                    {"message" : "invalid jwt token"}
                    """;
            response.getWriter().write(message);
        }
    }

    private boolean isExcludedPath(String path) {
        return excludedPaths.stream().anyMatch(p -> pathMatcher.match(p, path));
    }

}