package it.scopped.lifestealcore.database.connector.credentials;

public record AuthCredentials(
        String host,
        int port,
        String database,
        String username,
        String password
) {
    public String jdbc() {
        return "jdbc:mysql://" + host + ":" + port + "/" + database;
    }
}
