/*
 *  Class Name: HabitEvent
 *  Version: 0.5
 *  Date: November 13th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 */
package ssmad.habitizer;

import android.app.Activity;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Sadman on 2017-12-05.
 */

public class SyncController {

    public static final int TASK_ADD = 0;
    public static final int TASK_UPDATE = 1;
    public static final int TASK_DELETE = 2;
    private static final int DS_HABIT = 1;
    private static final int DS_HABITEVENT = 1;
    private static final int DS_ACCOUNT = 2;

    public static class DoubleString {
        /**
         * s[0] -> namespace
         * s[1] -> add, update, delete, addpic, updatepic, deletepic
         * s[2] -> json (add/update), id(delete)
         * s[3] -> id(update)
         */
        private String[] s;
        private Habit h;
        private HabitEvent he;
        private Account a;
        private int type;

        public int getType() {
            return type;
        }

        public HabitEvent getHabitEvent() {
            return he;
        }

        public Account getAccount() {
            return a;
        }

        public Habit getHabit() {
            return h;
        }

        public DoubleString(String[] s, Habit h) {
            this.s = s;
            this.h = h;
            this.type = DS_HABIT;
        }
        public DoubleString(String[] s, HabitEvent h) {
            this.s = s;
            this.he = h;
            this.type = DS_HABITEVENT;
        }
        public DoubleString(String[] s, Account h) {
            this.s = s;
            this.a = h;
            this.type = DS_ACCOUNT;
        }
        public String[] getString(){
            return this.s;
        }



    }

    private static ArrayList<DoubleString> toSync;
    private static ArrayList verify(Activity context, ArrayList a, Type t, String filename){
        if (a == null) {
            a = FileController.loadFromFile(context, filename, t);
            if(a == null){
                FileController.saveInFile(context, filename,
                        new ArrayList<DoubleString>());
                a = FileController.loadFromFile(context, filename, t);
            }
        }
        return a;
    }
    public static void sync(Activity context) {
        Type t = new TypeToken<ArrayList<DoubleString>>(){}.getType();
        toSync = verify(context, toSync, t, "SyncItems.sav");


        ElasticsearchController.AddItemsTask addItemsTask = new ElasticsearchController.AddItemsTask();
        ElasticsearchController.UpdateItemsTask updateItemsTask = new ElasticsearchController.UpdateItemsTask();
        ElasticsearchController.DeleteItemsTask deleteItemsTask = new ElasticsearchController.DeleteItemsTask();

        for (int i = toSync.size() - 1; i >= 0; i--) {
            DoubleString ds = toSync.get(i);
            String[] s = ds.getString();
            String namespace = s[0];
            int tasktype = Integer.valueOf(s[1]);
            if(tasktype == TASK_ADD){
                String json = s[2];
                addItemsTask.execute(namespace,json);
                try{
                    String id = addItemsTask.get();
                    toSync.remove(i);
                }catch (Exception e){
                    Log.d("SyncFail","Adding");
                }
            }else if(tasktype == TASK_UPDATE){
                String json = s[2];
                String id = s[3];
                updateItemsTask.execute(namespace,id,json);
                try{
                    updateItemsTask.get();
                    toSync.remove(i);
                }catch (Exception e){
                    Log.d("SyncFail","Updating");
                }
            }else if(tasktype == TASK_DELETE){
                String id = s[2];
                deleteItemsTask.execute(namespace,id);
                try{
                    deleteItemsTask.get();
                    toSync.remove(i);
                }catch (Exception e){
                    Log.d("SyncFail","Delete");
                }
            }

        }

    }


    public static void addToSync(String[] s, Habit h) {
        DoubleString d = new DoubleString(s,h);
        toSync.add(d);
    }public static void addToSync(String[] s, HabitEvent h) {
        DoubleString d = new DoubleString(s,h);
        toSync.add(d);
    }public static void addToSync(String[] s, Account h) {
        DoubleString d = new DoubleString(s,h);
        toSync.add(d);
    }

}
