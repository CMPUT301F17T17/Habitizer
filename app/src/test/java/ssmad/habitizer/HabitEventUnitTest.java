package ssmad.habitizer;

import android.location.Location;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by cryst on 11/10/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
public class HabitEventUnitTest {
    @Test
    public void testHEGetters() throws Exception {
        Date cal = Calendar.getInstance().getTime();
        HabitEvent habitevent = new HabitEvent("Push-Ups", cal);

        assertEquals("Push-Ups", habitevent.getTitle());
        assertEquals(cal, habitevent.getCompletionDate());
    }

    @Test
    public void testHESetters() throws Exception {
        Date cal = Calendar.getInstance().getTime();
        HabitEvent habitevent = new HabitEvent("Push-Ups", cal);

        habitevent.setTitle("Sit-Ups");
        habitevent.setComment("I did 5!");

        Date cal2 = Calendar.getInstance().getTime();
        habitevent.setCompletionDate(cal2);

        Location loc = new Location("dummyLoc");
        habitevent.setLocation(loc);

        habitevent.setPathToPicture("dummyPath");


        assertEquals("Sit-Ups", habitevent.getTitle());
        assertEquals("I did 5!", habitevent.getComment());
        assertEquals(cal2, habitevent.getCompletionDate());
        assertEquals(loc, habitevent.getLocation());
        assertEquals("dummyPath", habitevent.getPathToPicture());
    }

}
