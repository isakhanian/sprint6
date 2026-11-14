import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    public Map<Coach, Integer> getCountByCoaches() {
        Map<Coach, Integer> trainingCounts = new HashMap<>();

        for (DayTimetable day : timetable.values()) {
            for (TrainingSession session : day.getAllTrainingSessions()) {
                Coach coach = session.getCoach();
                int count = trainingCounts.getOrDefault(coach, 0);
                trainingCounts.put(coach, count + 1);
            }
        }

        List<Map.Entry<Coach, Integer>> sortedEntries =
                new ArrayList<>(trainingCounts.entrySet());

        sortedEntries.sort(new Comparator<Map.Entry<Coach, Integer>>() {
            @Override
            public int compare(Map.Entry<Coach, Integer> a,
                               Map.Entry<Coach, Integer> b) {
                return Integer.compare(b.getValue(), a.getValue());
            }
        });

        Map<Coach, Integer> result = new LinkedHashMap<>();

        for (Map.Entry<Coach, Integer> entry : sortedEntries) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

}
