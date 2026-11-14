public class TimeOfDay implements Comparable<TimeOfDay> {
    private int hours;
    private int minutes;

    public TimeOfDay(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public int compareTo(TimeOfDay o) {
        int resultHours = Integer.compare(hours, o.hours);
        if (resultHours != 0) {
            return resultHours;
        }
        return Integer.compare(minutes, o.minutes);

    }
}