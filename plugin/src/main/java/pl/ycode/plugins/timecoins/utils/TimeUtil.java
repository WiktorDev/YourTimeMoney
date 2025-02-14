package pl.ycode.plugins.timecoins.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TimeUtil {
    private static final Map<Character, Long> FORMATS = Map.of(
            'D', 86400000L,
            'H', 3600000L,
            'M', 60000L,
            'S', 1000L
    );

    public static long timeFromString(String string) {
        char format = string.toUpperCase().charAt(string.length() - 1);
        if (Character.isDigit(format)) return Long.parseLong(string);
        int number = Integer.parseInt(string.substring(0, string.length() - 1));
        if (!FORMATS.containsKey(format)) return 0;
        return number * FORMATS.get(format);
    }

    public static String formatTime(Long time) {
        long days = TimeUnit.MILLISECONDS.toDays(time);
        time -= TimeUnit.DAYS.toMillis(days);

        long hours = TimeUnit.MILLISECONDS.toHours(time);
        time -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        time -= TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.MILLISECONDS.toSeconds(time);
        time -= TimeUnit.SECONDS.toMillis(seconds);

        long millis = TimeUnit.MILLISECONDS.toMillis(time);

        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(days).append(" d. ");
        }
        if (hours > 0) {
            sb.append(hours).append(" h. ");
        }
        if (minutes > 0L) {
            sb.append(minutes).append(" m. ");
        }
        if (seconds > 0L) {
            sb.append(seconds).append(" s. ");
        }
        if (millis > 0L) {
            sb.append(millis).append(" ms.");
        }
        return sb.toString();
    }

    public static String formatDateTime(final Long time) {
        final DateFormat format = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        return format.format(time);
    }
    public static String formatDate(final Long time) {
        final DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.format(time);
    }

    public static String format(String text, String placeholder, long time) {
        int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(time);
        int minutes = seconds / 60;
        int hours = minutes / 60;
        int days = hours / 24;
        StringBuilder timeBuilder = new StringBuilder();
        if (days > 0) {
            timeBuilder.append(days).append("d");
            hours = hours - days * 24;
            if (hours > 0) {
                timeBuilder.append(" ").append(hours).append("h");
            }
            minutes = minutes - days * 24 * 60 - hours * 60;
            if (minutes > 0) {
                timeBuilder.append(" ").append(minutes).append("m");
            }
        } else if (hours > 0) {
            timeBuilder.append(hours).append("g");
            minutes = minutes - hours * 60;
            if (minutes > 0) {
                timeBuilder.append(" ").append(minutes).append("m");
            }
        } else if (minutes > 0) {
            timeBuilder.append(minutes).append("m");
            seconds = seconds - minutes * 60;
            if (seconds > 0) {
                timeBuilder.append(" ").append(seconds).append("s");
            }
        } else {
            timeBuilder.append(seconds).append("s");
        }
        return text.replace(placeholder, timeBuilder.toString());
    }
}