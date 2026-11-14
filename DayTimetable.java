import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DayTimetable {
    private TreeMap<TimeOfDay, List<TrainingSession>> dayTimetable = new TreeMap<>();


    public void addTrainingSession(TrainingSession trainingSession) {
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();
        if (dayTimetable.containsKey(timeOfDay)) {
            List<TrainingSession> classes = dayTimetable.get(timeOfDay);
            classes.add(trainingSession);
        } else {
            List<TrainingSession> classes = new ArrayList<>();
            classes.add(trainingSession);
            dayTimetable.put(timeOfDay, classes);

        }
}

    public List<TrainingSession> getTrainingSessionsForTime(TimeOfDay timeOfDay) {
        return dayTimetable.getOrDefault(timeOfDay, new ArrayList<>());

}

    public List<TrainingSession> getAllTrainingSessions() {
        List<TrainingSession> allSessions = new ArrayList<>();
        for (Map.Entry<TimeOfDay, List<TrainingSession>> entry : dayTimetable.entrySet()) {
            List<TrainingSession> sessions = entry.getValue();
            allSessions.addAll(sessions);

}       return allSessions;

}

}
