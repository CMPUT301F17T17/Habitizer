package ssmad.habitizer;

import ssmad.habitizer.HabitEvent;
/**
 * Created by 旻丰 on 2017/11/10.
 */

public class DeleteHabitEvent extends HabitEvent{
    private HabitEvent delete;
    public DeleteHabitEvent(String title, HabitEvent event){
        delete = event;
    }

