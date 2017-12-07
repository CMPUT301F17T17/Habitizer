package ssmad.habitizer;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by cryst on 10/23/2017.
 */

public class SocialTabActivityTest extends ActivityInstrumentationTestCase2<SocialTabActivity> {
    private Solo solo;

    public SocialTabActivityTest() {
        super(SocialTabActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }


}