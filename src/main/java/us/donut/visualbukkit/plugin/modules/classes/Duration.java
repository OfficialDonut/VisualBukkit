package us.donut.visualbukkit.plugin.modules.classes;

public class Duration {

    private long ticks;

    private Duration(long ticks) {
        this.ticks = ticks;
    }

    public long getTicks() {
        return ticks;
    }

    @Override
    public String toString() {
        return ticks + " ticks";
    }

    public static Duration fromTicks(double ticks) {
        return new Duration(Math.round(ticks));
    }

    public static Duration fromSeconds(double seconds) {
        return fromTicks(seconds * 20);
    }

    public static Duration fromMinutes(double minutes) {
        return fromSeconds(minutes * 60);
    }

    public static Duration fromHours(double hours) {
        return fromMinutes(hours * 60);
    }

    public static Duration fromDays(double days) {
        return fromHours(days * 24);
    }
}
