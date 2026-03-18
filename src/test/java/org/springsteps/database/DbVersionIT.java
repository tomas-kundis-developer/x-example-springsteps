package org.springsteps.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DbVersionIT {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private static final String SQL_DB_VERSION = "SELECT * FROM version()";

  private static final String EXPECTED_DATABASE_VERSION = "PostgreSQL 18.3";

  @Test
  void testDbVersion() {
    String dbVersion = jdbcTemplate.query(
        SQL_DB_VERSION,
        (row, i) -> row.getString("version")).getFirst();

    assertThat(dbVersion).contains(EXPECTED_DATABASE_VERSION);
  }
}
