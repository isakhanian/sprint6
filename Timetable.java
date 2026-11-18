import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Timetable {
    private Map<DayOfWeek, DayTimetable> timetable = new HashMap<>();
    

    public void addNewTrainingSession(TrainingSession trainingSession) {
       DayOfWeek dayOfWeek =  trainingSession.getDayOfWeek();
       if (timetable.containsKey(dayOfWeek)) {
            DayTimetable dayTimetable = timetable.get(dayOfWeek);
            dayTimetable.addTrainingSession(trainingSession);
       } else {
            DayTimetable dayTimetable = new DayTimetable();
            dayTimetable.addTrainingSession(trainingSession);
            timetable.put(dayOfWeek, dayTimetable);
       }
    }

    public DayTimetable getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.getOrDefault(dayOfWeek, new DayTimetable());
        
    }
    
    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        DayTimetable dayTimetable = getTrainingSessionsForDay(dayOfWeek);
        return dayTimetable.getTrainingSessionsForTime(timeOfDay);

    }

    
    public List<CounterForCoach> getCountByCoaches() {
        Map<Coach, Integer> trainingCounts = new HashMap<>();

        for (DayTimetable day : timetable.values()) {
            for (TrainingSession session : day.getAllTrainingSessions()) {
                Coach coach = session.getCoach();
                int count = trainingCounts.getOrDefault(coach, 0);
                trainingCounts.put(coach, count + 1);
            }
        }

        List<CounterForCoach> result = new ArrayList<>();

        for (Map.Entry<Coach, Integer> entry : trainingCounts.entrySet()) {
            CounterForCoach counter = new CounterForCoach(
                    entry.getKey(), entry.getValue());

            result.add(counter);
        }

        Collections.sort(result);

        return result;
}


}
