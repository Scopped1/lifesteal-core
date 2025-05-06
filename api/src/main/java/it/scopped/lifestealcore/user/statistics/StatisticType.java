package it.scopped.lifestealcore.user.statistics;

public enum StatisticType {

    KILLS("kills"),
    DEATHS("deaths"),
    HEARTS("hearts"),
    PLAY_TIME("play_time");

    public static final StatisticType[] VALUES = values();

    private final String identifier;

    StatisticType(String identifier) {
        this.identifier = identifier;
    }

    public String identifier() {
        return identifier;
    }
}
