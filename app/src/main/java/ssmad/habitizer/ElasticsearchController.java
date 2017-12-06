package ssmad.habitizer;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Update;

public class ElasticsearchController {
    private static JestDroidClient client;

    private static final String INDEX = "team17";
    private static final String USER_TYPE = "user3";

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
                    } else {
                        Log.i("Error", "Elasticsearch was not able to add the user");
                    }
                } catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the user info.");
                }

            }
            return null;
        }
    }

    public static class AddItemsTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            verifySettings();

            Index index = new Index.Builder(params[1]).index(INDEX).type(params[0]).build();
            try {
                // where is the client?
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    return result.getId();
                } else {
                    Log.i("Error", "Elasticsearch was not able to add the user");
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and send the user info.");
            }
            return null;
        }
    }

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
                if (result.isSucceeded()) {
                    List<Account> foundUsers = result.getSourceAsObjectList(Account.class);
                    users.addAll(foundUsers);
                } else {
                    Log.i("Error", "The search query failed to find any users that matched");
                }
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return users;
        }
    }

    public static class GetItemsTask extends AsyncTask<String, Void, JsonArray> {
        @Override
        protected JsonArray doInBackground(String... search_parameters) {
            verifySettings();
            JsonArray userData = null;

            // TODO Build the query
            String query = "{\n" +
                    "\"size\" : 1000,"+
                    "     \"query\" : {\n" +
                    "        \"term\": { \"" + search_parameters[1] + "\": \"" + search_parameters[2] + "\"}\n" +
                    "     }\n" +
                    "}";

            Search search = new Search.Builder(query)
                    .addIndex(INDEX)
                    .addType(search_parameters[0])
                    .build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                JsonObject hits = result.getJsonObject().getAsJsonObject("hits");
               /* Gson g = new Gson();
                String s = g.toJson(hits);
                Log.d("ESC.json", s);*/
                if (result.isSucceeded()) {
                    userData = hits.getAsJsonArray("hits");
                } else {
                    Log.i("Error", "The search query failed to find any users that matched");
                }
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return userData;
        }
    }
    public static class GetFeedTask extends AsyncTask<String, Void, JsonArray> {
        @Override
        protected JsonArray doInBackground(String... search_parameters) {
            verifySettings();
            JsonArray userData = null;

            // TODO Build the query
            String query = "{\n" +
                    "\"size\" : 1000,"+
                    "     \"query\" : {\n" +
                                 search_parameters[1] +
                    "     }\n" +
                    "}";

            Search search = new Search.Builder(query)
                    .addIndex(INDEX)
                    .addType(search_parameters[0])
                    .build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                Gson g = new Gson();
                String s = g.toJson(result.getJsonObject());
                Log.d("ESC.json", s);
                JsonObject hits = result.getJsonObject().getAsJsonObject("hits");
                if (result.isSucceeded()) {
                    userData = hits.getAsJsonArray("hits");
                } else {
                    Log.i("Error", "The search query failed to find any users that matched");
                }
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return userData;
        }
    }

    public static class DeleteItemsTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            verifySettings();
/**
 * params[0] = type
 * params[1] = id
 */
            Delete delete = new Delete.Builder(params[1]).index(INDEX).type(params[0]).build();
            Boolean success = false;
            try {
                // where is the client?
                DocumentResult result = client.execute(delete);
                if (result.isSucceeded()) {
                    success = true;
                } else {
                    Log.i("Error", "Elasticsearch was not able to add the user");
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and send the user info.");
            }
            return success;
        }
    }
    public static class UpdateItemsTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            verifySettings();
/**
 * params[0] = type
 * params[1] = id
 * params[2] = jsonstring
 */
            Update update = new Update.Builder("{\"doc\": "+params[2]+"}").index(INDEX).type(params[0]).id(params[1]).build();
            Boolean success = false;
            try {
                // where is the client?
                DocumentResult result = client.execute(update);
                if (result.isSucceeded()) {
                    success = true;
                } else {
                    Log.i("ESC.UP,Error", "Elasticsearch was not able to add the user");
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and send the user info.");
            }
            return success;
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
