package nl.hu.cisq1.lingo.security.presentation.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import nl.hu.cisq1.lingo.security.data.User;
import io.jsonwebtoken.Jwts;
import nl.hu.cisq1.lingo.security.presentation.Dto.Login;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final String secret;
    private final Integer expirationInMs;

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(
            String path,
            String secret,
            Integer expirationInMs,
            AuthenticationManager authenticationManager
    ) {
        super(new AntPathRequestMatcher(path));

        this.secret = secret;
        this.expirationInMs = expirationInMs;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        Login login = new ObjectMapper()
                .readValue(request.getInputStream(), Login.class);

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.username, login.password)
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        byte[] signingKey = this.secret.getBytes();

        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", "JWT")
                .setIssuer("hu-lingo-api")
                .setAudience("hu-lingo")
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + this.expirationInMs))
                .claim("rol", roles)
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .compact();

        response.addHeader("Authorization", "Bearer " + token);
    }
}
