package ssmad.habitizer;

/**
 * Created by 旻丰 on 2017/11/12.
 */

/**
 * Overwrite old information with new information
 */
public class EditHabit extends Habit{
    private Habit oldInfo;
    private Habit newInfo;

    public EditHabit(Habit old, Habit newData){
        oldInfo = old;
        newInfo = newData;
    }
}
