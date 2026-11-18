
public class CounterForCoach implements Comparable<CounterForCoach> {

    private Coach coach;
    private int count;

    public CounterForCoach(Coach coach, int count) {
        this.coach = coach;
        this.count = count;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int compareTo(CounterForCoach o) {
        return Integer.compare(o.count, count);
    }
}
