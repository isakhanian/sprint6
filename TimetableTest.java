
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession session = new TrainingSession(
                group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(session);

        assertEquals(1, timetable.getTrainingSessionsForDay(
                DayOfWeek.MONDAY).getAllTrainingSessions().size());

        assertEquals(0, timetable.getTrainingSessionsForDay(
                DayOfWeek.TUESDAY).getAllTrainingSessions().size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);

        TrainingSession thursdayAdult = new TrainingSession(
                groupAdult, coach, DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        TrainingSession mondayChild = new TrainingSession(
                groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        TrainingSession thursdayChild = new TrainingSession(
                groupChild, coach, DayOfWeek.THURSDAY, new TimeOfDay(13, 0));

        TrainingSession saturdayChild = new TrainingSession(
                groupChild, coach, DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(thursdayAdult);
        timetable.addNewTrainingSession(mondayChild);
        timetable.addNewTrainingSession(thursdayChild);
        timetable.addNewTrainingSession(saturdayChild);

        assertEquals(1, timetable.getTrainingSessionsForDay(
                DayOfWeek.MONDAY).getAllTrainingSessions().size());

        List<TrainingSession> thursdaySessions =
                timetable.getTrainingSessionsForDay(
                        DayOfWeek.THURSDAY).getAllTrainingSessions();

        assertEquals(2, thursdaySessions.size());
        assertSame(thursdayChild, thursdaySessions.get(0));
        assertSame(thursdayAdult, thursdaySessions.get(1));

        assertEquals(0, timetable.getTrainingSessionsForDay(
                DayOfWeek.TUESDAY).getAllTrainingSessions().size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession session = new TrainingSession(
                group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(session);

        assertEquals(1, timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size());

        assertEquals(0, timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(14, 0)).size());
    }

    @Test
    void testMultipleSessionsAtSameTime() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Иванов", "Пётр", "Сергеевич");

        Group group1 = new Group("Гимнастика", Age.CHILD, 60);
        Group group2 = new Group("Акробатика", Age.ADULT, 90);

        TrainingSession session1 = new TrainingSession(
                group1, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        TrainingSession session2 = new TrainingSession(
                group2, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        List<TrainingSession> sessions =
                timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        assertEquals(2, sessions.size());
        assertTrue(sessions.contains(session1));
        assertTrue(sessions.contains(session2));
    }

    @Test
    void testEmptyTimetable() {
        Timetable timetable = new Timetable();

        assertTrue(timetable.getTrainingSessionsForDay(
                DayOfWeek.MONDAY).getAllTrainingSessions().isEmpty());

        assertTrue(timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0)).isEmpty());
    }

    @Test
    void testSameTimeDifferentDays() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Иванов", "Пётр", "Сергеевич");
        Group group = new Group("Гимнастика", Age.CHILD, 60);

        TrainingSession mondaySession = new TrainingSession(
                group, coach, DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        TrainingSession fridaySession = new TrainingSession(
                group, coach, DayOfWeek.FRIDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondaySession);
        timetable.addNewTrainingSession(fridaySession);

        List<TrainingSession> monday =
                timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        List<TrainingSession> friday =
                timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.FRIDAY, new TimeOfDay(10, 0));

        assertEquals(1, monday.size());
        assertEquals(1, friday.size());

        assertSame(mondaySession, monday.get(0));
        assertSame(fridaySession, friday.get(0));
    }

    @Test
    void testGetCountByCoachesEmptyTimetable() {
        Timetable timetable = new Timetable();

        List<CounterForCoach> result = timetable.getCountByCoaches();

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetCountByCoachesSortedDescending() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Пётр", "Сергеевич");
        Coach coach2 = new Coach("Петров", "Иван", "Иванович");

        Group group = new Group("Гимнастика", Age.CHILD, 60);

        timetable.addNewTrainingSession(new TrainingSession(
                group, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(
                group, coach1, DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(
                group, coach1, DayOfWeek.FRIDAY, new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(
                group, coach2, DayOfWeek.TUESDAY, new TimeOfDay(12, 0)));

        List<CounterForCoach> result = timetable.getCountByCoaches();

        assertEquals(2, result.size());

        assertEquals(coach1, result.get(0).getCoach());
        assertEquals(3, result.get(0).getCount());

        assertEquals(coach2, result.get(1).getCoach());
        assertEquals(1, result.get(1).getCount());
    }

    @Test
    void testSameCoachWithDifferentObjects() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Пётр", "Сергеевич");
        Coach coach2 = new Coach("Иванов", "Пётр", "Сергеевич");

        Group group = new Group("Гимнастика", Age.CHILD, 60);

        timetable.addNewTrainingSession(new TrainingSession(
                group, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(
                group, coach2, DayOfWeek.TUESDAY, new TimeOfDay(12, 0)));

        List<CounterForCoach> result = timetable.getCountByCoaches();

        assertEquals(1, result.size());
        assertEquals(coach1, result.get(0).getCoach());
        assertEquals(2, result.get(0).getCount());
    }
}
