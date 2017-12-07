/*
 *  Class Name: SocialMultiAdapter
 *  Version: 1.0
 *  Date: December 6th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 *
 */

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
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Adapter for Social tab
 * @author Sadman
 * @version 1.0
 * @see SocialTabActivity
 * since 1.0
 */
public class SocialMultiAdapter extends ArrayAdapter<Account> {
    public static final String SOCIAL2ACCOUNT = "fromSocial";
    public static final String ACCOUNTMODE = "fromSocial_Mode";

    /**
     * Constructor for SocialMultiAdapter
     * @param context
     * @param accounts
     */
    SocialMultiAdapter(Context context, ArrayList<Account> accounts) {
        super(context, R.layout.myfeed_list_view, accounts);
    }

    /**
     * Gets view for social tab listings
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Inflating from xml
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View custom = inflater.inflate(R.layout.social_list_view, parent, false);
        Account account = getItem(position); //target account
        ((TextView) custom.findViewById(R.id.username)).setText(account.getUsername());

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

        if (SocialTabActivity.AdapterMode == SocialTabActivity.AdapterModeRequests) {
            custom.findViewById(R.id.request_layout).setVisibility(View.VISIBLE);
        } else if (SocialTabActivity.AdapterMode == SocialTabActivity.AdapterModeSentRequests){
            custom.findViewById(R.id.cancel_btn).setVisibility(View.VISIBLE);
        }

        (custom.findViewById(R.id.approve_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account targetAccount = getItem(fpos);
                Account currentAccount = DummyMainActivity.currentAccount;
                //TODO add target account to currentUser follower list AND add currentUser to target account following list
                currentAccount.setFollowers(EditProfileActivity.addOne(currentAccount.getFollowers(), targetAccount.getUsername()));
                targetAccount.setFollowing(EditProfileActivity.addOne(targetAccount.getFollowing(), currentAccount.getUsername()));
                //TODO remove target account from currentUser request list and remove currentUser from target account send_request list
                currentAccount.setRequests(EditProfileActivity.minusOne(currentAccount.getRequests(), targetAccount.getUserName()));
                targetAccount.setSent_requests(EditProfileActivity.minusOne(targetAccount.getSent_requests(), currentAccount.getUsername()));
                saveAccountChanges(currentAccount);
                saveAccountChanges(targetAccount);
                SocialTabActivity.SocialAccounts.remove(fpos);
                notifyDataSetChanged();
            }
        });

        (custom.findViewById(R.id.reject_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account targetAccount = getItem(fpos);
                Account currentAccount = DummyMainActivity.currentAccount;
                //TODO remove target account from currentUser request list and remove currentUser from target account send_request list
                currentAccount.setRequests(EditProfileActivity.minusOne(currentAccount.getRequests(), targetAccount.getUserName()));
                targetAccount.setSent_requests(EditProfileActivity.minusOne(targetAccount.getSent_requests(), currentAccount.getUsername()));
                saveAccountChanges(currentAccount);
                saveAccountChanges(targetAccount);
                SocialTabActivity.SocialAccounts.remove(fpos);
                notifyDataSetChanged();
            }
        });

        (custom.findViewById(R.id.cancel_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account targetAccount = getItem(fpos);
                Account currentAccount = DummyMainActivity.currentAccount;
                //TODO remove target account from currentUser request list and remove currentUser from target account send_request list
                currentAccount.setSent_requests(EditProfileActivity.minusOne(currentAccount.getSent_requests(), targetAccount.getUserName()));
                targetAccount.setRequests(EditProfileActivity.minusOne(targetAccount.getRequests(), currentAccount.getUsername()));
                saveAccountChanges(currentAccount);
                saveAccountChanges(targetAccount);
                SocialTabActivity.SocialAccounts.remove(fpos);
                notifyDataSetChanged();
            }
        });
        return custom;
    }

    /**
     * Saves changes to account
     * @param account
     */
    public void saveAccountChanges(Account account){
        ElasticsearchController.AddUsersTask addUsersTask = new ElasticsearchController.AddUsersTask();
        addUsersTask.execute(account);
    }
}
