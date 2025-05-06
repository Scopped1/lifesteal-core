package it.scopped.lifestealcore.database.connector;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import it.scopped.lifestealcore.database.connector.credentials.AuthCredentials;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolManager {

    private final AuthCredentials credentials;

    private HikariDataSource dataSource;

    public ConnectionPoolManager(AuthCredentials credentials) {
        this.credentials = credentials;
        setupPool();
    }

    private void setupPool() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(credentials.jdbc());
        config.setUsername(credentials.username());
        config.setPassword(credentials.password());

        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.addDataSourceProperty("useUnicode", true);
        config.addDataSourceProperty("useSSL", false);

        config.setPoolName("lifestealcore-pool");

        dataSource = new HikariDataSource(config);
    }

    public Connection connection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
