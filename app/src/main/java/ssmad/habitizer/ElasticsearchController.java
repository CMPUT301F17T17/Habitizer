/*
 *  Class Name: ElasticsearchController
 *  Version: 1.0
 *  Date: November 13th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 */
package ssmad.habitizer;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Controller for elastic search
 * @author Andrew
 * @version 1.0
 * @since 1.0
 */
public class ElasticsearchController {
    private static JestDroidClient client;

    private static final String INDEX = "team17";
    private static final String USER_TYPE = "user";

    // TODO we need a function which adds tweets to elastic search
    public static class AddUsersTask extends AsyncTask<Account, Void, Void> {

        @Override
        protected Void doInBackground(Account... users) {
            verifySettings();

            for (Account user : users) {
                Index index = new Index.Builder(user).index(INDEX).type(USER_TYPE).id(user.getUserName()).build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        result.getId();
                    }
                    else {
                        Log.i("Error", "Elasticsearch was not able to add the user");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the user info.");
                }

            }
            return null;
        }
    }

    // TODO we need a function which gets tweets from elastic search
    public static class GetUsersTask extends AsyncTask<String, Void, ArrayList<Account>> {
        @Override
        protected ArrayList<Account> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Account> users = new ArrayList<Account>();

            // TODO Build the query
            String query = "{\n" +
                    "     \"query\" : {\n" +
                    "        \"term\": { \"_id\": \"" + search_parameters[0] + "\"}\n" +
                    "     }\n" +
                    "}";

            Search search = new Search.Builder(query)
                    .addIndex(INDEX)
                    .addType(USER_TYPE)
                    .build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()){
                    List<Account> foundUsers = result.getSourceAsObjectList(Account.class);
                    users.addAll(foundUsers);
                }
                else {
                    Log.i("Error", "The search query failed to find any users that matched");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return users;
        }
    }

    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
