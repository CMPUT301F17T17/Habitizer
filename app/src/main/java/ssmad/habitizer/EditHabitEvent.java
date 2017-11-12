package ssmad.habitizer;

/**
 * Created by 旻丰 on 2017/11/10.
 */

/**
 * Overwrite old information with new information
 */
public class EditHabitEvent extends HabitEvent {
    private HabitEvent oldInfo;
    private HabitEvent newInfo;

    public EditHabitEvent(HabitEvent old, HabitEvent newEvent){
        oldInfo = old;
        newInfo = newEvent;
    }
}
