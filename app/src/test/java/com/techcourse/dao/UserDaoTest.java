package com.techcourse.dao;

import com.interface21.jdbc.core.JdbcTemplate;
import com.techcourse.config.DataSourceConfig;
import com.techcourse.domain.User;
import com.techcourse.support.jdbc.init.DatabasePopulatorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class UserDaoTest {

    private UserDao userDao;
    private DataSource dataSource;

    @BeforeEach
    void setup() throws SQLException {
        dataSource = DataSourceConfig.getInstance();
        userDao = new UserDao(new JdbcTemplate());
        DatabasePopulatorUtils.execute(dataSource);

        final var user = new User("gugu", "password", "hkkang@woowahan.com");
        userDao.insert(dataSource.getConnection(), user);
    }

    @Test
    void findAll() throws SQLException {
        final var users = userDao.findAll(dataSource.getConnection());

        assertThat(users).isNotEmpty();
    }

    @Test
    void findById() throws SQLException {
        final var user = userDao.findById(dataSource.getConnection(), 1L).orElseThrow();

        assertThat(user.getAccount()).isEqualTo("gugu");
    }

    @Test
    void findByAccount() throws SQLException {
        final var account = "gugu";
        final var user = userDao.findByAccount(dataSource.getConnection(), account).orElseThrow();

        assertThat(user.getAccount()).isEqualTo(account);
    }

    @Test
    void insert() throws SQLException {
        final var account = "insert-gugu";
        final var user = new User(account, "password", "hkkang@woowahan.com");
        userDao.insert(dataSource.getConnection(), user);

        final var actual = userDao.findById(dataSource.getConnection(), 2L).orElseThrow();

        assertThat(actual.getAccount()).isEqualTo(account);
    }

    @Test
    void update() throws SQLException {
        final var newPassword = "password99";
        final var user = userDao.findById(dataSource.getConnection(), 1L).orElseThrow();
        user.changePassword(newPassword);

        userDao.update(dataSource.getConnection(), user);

        final var actual = userDao.findById(dataSource.getConnection(), 1L).orElseThrow();

        assertThat(actual.getPassword()).isEqualTo(newPassword);
    }
}
