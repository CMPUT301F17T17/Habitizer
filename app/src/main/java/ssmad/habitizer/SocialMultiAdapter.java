package ssmad.habitizer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andoryu on 2017-12-04.
 */

public class SocialMultiAdapter extends ArrayAdapter<Account> {
    public static final String SOCIAL2ACCOUNT = "fromSocial";
    public static final String ACCOUNTMODE = "fromSocial_Mode";




    SocialMultiAdapter(Context context, ArrayList<Account> accounts) {
        super(context, R.layout.myfeed_list_view, accounts);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Inflating from xml
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View custom = inflater.inflate(R.layout.social_list_view, parent, false);
        Account account = getItem(position);
        ((TextView) custom.findViewById(R.id.username)).setText(account.getUserName());

        final int fpos = position;
        (custom.findViewById(R.id.outer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                intent.putExtra(SOCIAL2ACCOUNT, fpos);
                //intent.putExtra(SOCIAL2ACCOUNT, EditProfileActivity.LIMITEDMODE);
                ((Activity) getContext()).startActivityForResult(intent, 123);
            }
        });


        return custom;
    }
}
