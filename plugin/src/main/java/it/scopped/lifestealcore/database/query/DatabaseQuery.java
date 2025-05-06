package it.scopped.lifestealcore.database.query;

public interface DatabaseQuery {

    String CREATE_PLAYERS_TABLE = """
            CREATE TABLE IF NOT EXISTS `lifesteal_players` (
                `uuid`       VARCHAR(36) PRIMARY KEY NOT NULL,
                `name`       VARCHAR(16) NOT NULL UNIQUE,
                `kills`      BIGINT      NOT NULL DEFAULT 0,
                `deaths`     BIGINT      NOT NULL DEFAULT 0,
                `hearts`     INT         NOT NULL DEFAULT 20,
                `play_time`  BIGINT      NOT NULL DEFAULT 0
            )
            """;

    String DELETE_PLAYER = """
        DELETE FROM `lifesteal_players`
        WHERE `uuid` = ? AND `name` = ?
        """;

    String GET_PLAYERS = """
        SELECT * FROM `lifesteal_players`
        """;

    String SAVE_PLAYER = """
        INSERT INTO `lifesteal_players` (`uuid`, `name`, `kills`, `deaths`, `hearts`, `play_time`)
        VALUES (?, ?, ?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE
            `name` = VALUES(`name`),
            `kills` = VALUES(`kills`),
            `deaths` = VALUES(`deaths`),
            `hearts` = VALUES(`hearts`),
            `play_time` = VALUES(`play_time`)
        """;

}