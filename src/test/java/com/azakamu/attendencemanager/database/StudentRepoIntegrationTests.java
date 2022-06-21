package com.azakamu.attendencemanager.database;

import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.jdbc.Sql;

@DataJdbcTest
@Sql("/schema_tests.sql")
public class StudentRepoIntegrationTests {

}
