package ssmad.habitizer;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by cryst on 11/10/2017.
 */

public class HabitUnitTest {
    @Test
    public void testHGetters() throws Exception {
        Date cal = Calendar.getInstance().getTime();
        Habit habit = new Habit("Push-Ups", cal, "Big Arms" );

        assertEquals("Push-Ups", habit.getTitle());
        assertEquals("Big Arms", habit.getReason());
        assertEquals(cal, habit.getStartDate());
    }

    @Test
    public void testHSetters() throws Exception {
        Date cal = Calendar.getInstance().getTime();
        Habit habit = new Habit("Push-Ups", cal, "Big Arms");

        habit.setTitle("Sit-Ups");
        habit.setReason("Flat Stomach");
        Date cal2 = Calendar.getInstance().getTime();
        habit.setStartDate(cal2);

        assertEquals("Sit-Ups", habit.getTitle());
        assertEquals("Flat Stomach", habit.getReason());
        assertEquals(cal2, habit.getStartDate());
    }


}

