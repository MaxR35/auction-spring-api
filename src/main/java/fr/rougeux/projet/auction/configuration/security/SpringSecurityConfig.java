package fr.rougeux.projet.auction.configuration.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.spec.SecretKeySpec;
import javax.sql.DataSource;

@Configuration
public class SpringSecurityConfig {


    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery("""
                        SELECT email as username, password, 1 as enabled
                        FROM USERS u
                        WHERE u.email = ?
                        """);

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("""
                SELECT u.email AS username,
                CASE WHEN u.is_admin = 1 THEN 'ROLE_ADMIN' ELSE 'ROLE_USER' END AS authority
                FROM USERS u
                WHERE u.email = ?
                """);

        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/auth/me").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/auth/logout").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/sales/**").hasRole("USER") // Reserve aux ROLE_USER
                        .requestMatchers(HttpMethod.GET,"/img/**").hasRole("USER") // Reserve aux ROLE_USER
                        .anyRequest().denyAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                        .bearerTokenResolver(cookieBearerTokenResolver()) // ✅ custom resolver
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public JwtEncoder jwtEncoder(@Value("${app.jwtSecret}") String jwtSecret) {
        return new NimbusJwtEncoder(new ImmutableSecret<>(jwtSecret.getBytes()));
    }

    @Bean
    public JwtDecoder jwtDecoder(@Value("${app.jwtSecret}") String jwtSecret) {
        SecretKeySpec secretKey = new SecretKeySpec(jwtSecret.getBytes(), 0, jwtSecret.getBytes().length,"RSA");
        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix(""); // on ne rajoute pas ROLE_ car déjà présent
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");


        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public BearerTokenResolver cookieBearerTokenResolver() {
        return request -> {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("JWT".equals(cookie.getName())) {
                        return cookie.getValue();
                    }
                }
            }
            return null;
        };
    }
}
