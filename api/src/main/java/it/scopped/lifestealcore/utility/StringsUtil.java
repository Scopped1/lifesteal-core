package it.scopped.lifestealcore.utility;

public class StringsUtil {

    public static String replace(String message, Object... params) {
        if (params.length % 2 != 0) {
            throw new IllegalArgumentException("Parameters should be in key-value pairs.");
        }

        for (int i = 0; i < params.length; i += 2) {
            message = message.replace(params[i].toString(), params[i + 1].toString());
        }

        return message;
    }

    public static String parseStringTime(long seconds) {
        long[] values = {seconds / 86400L, seconds % 86400L / 3600L, seconds % 3600L / 60L, seconds % 60L};
        String[] labels = {"d ", "h ", "m ", "s"};

        StringBuilder formattedTime = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            if (values[i] > 0L || !formattedTime.isEmpty()) {
                formattedTime.append(values[i]).append(labels[i]);
            }
        }

        return formattedTime.toString();
    }

    public static long parseTimeString(String timeString) {
        long seconds = 0L;

        for (String part : timeString.split("\\s+")) {
            seconds += Integer.parseInt(part.substring(0, part.length() - 1)) * (
                    part.endsWith("d") ? 86400L : (part.endsWith("h") ? 3600L : (part.endsWith("m") ? 60L : 1L)));
        }

        return seconds;
    }

}
