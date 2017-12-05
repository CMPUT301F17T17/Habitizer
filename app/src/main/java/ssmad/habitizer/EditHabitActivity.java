package ssmad.habitizer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditHabitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_habit);

        final int position = getIntent().getExtras().getInt(ViewHabitActivity.toEdit);
        final Habit habit = DummyMainActivity.myHabits.get(position);

        final TextView habitInput = (TextView)findViewById(R.id.habit_input);
        habitInput.setVisibility(View.VISIBLE);
        habitInput.setText(habit.getTitle());

        final TextView reasonInput = (TextView)findViewById(R.id.reason_input);
        reasonInput.setVisibility(View.VISIBLE);
        reasonInput.setText(habit.getReason());

        (findViewById(R.id.editing)).setVisibility(View.VISIBLE);

        Button doneButton = (Button) findViewById(R.id.done_button);
        doneButton.setVisibility(View.VISIBLE);

        AddHabitActivity.setDays(habit);
        AddHabitActivity.setUpDays(this);
        AddHabitActivity.makeDaysEditable(this);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AddHabitActivity._habitChecksOut(EditHabitActivity.this)){
                    habit.setTitle(habitInput.getText().toString());
                    habit.setReason(reasonInput.getText().toString());
                    habit.setDaysOfWeekDue(AddHabitActivity.days);
                    AddHabitActivity.resetDays();
                    EditHabitActivity.this.finish();
                }
            }
        });

    }
}
