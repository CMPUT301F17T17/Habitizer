package ssmad.habitizer;



/**
 * Created by 旻丰 on 2017/11/10.
 */

public class DeleteHabit extends Habit{
    private Habit delete;
    public DeleteHabit(String title, Habit habit){
        delete = habit;
    }
}
