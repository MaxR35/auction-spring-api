package fr.rougeux.projet.auction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TestUpdate {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    void update() {
        String email = "john.doe@gmail.com";
        String password = "fda098";
        String encodedPassword = passwordEncoder.encode(password);

        String query = """
                UPDATE USERS
                SET email = :email, password = :password
                WHERE user_id = 5
                """;

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("email", email);
        parameterSource.addValue("password", encodedPassword);

        jdbcTemplate.update(query, parameterSource);

        String select = """
                SELECT password FROM USERS WHERE email = :email
                """;

        MapSqlParameterSource parameterSource2 = new MapSqlParameterSource();
        parameterSource2.addValue("email", email);

        String storedHash = jdbcTemplate.queryForObject(select, parameterSource2, String.class);

        assertTrue(passwordEncoder.matches(password, storedHash));
    }
}
