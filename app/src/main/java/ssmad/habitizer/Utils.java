package ssmad.habitizer;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Sadman on 2017-12-03.
 */

public class Utils {
    public static void toastMe(String s, Context context) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
