package ssmad.habitizer;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by Sadman on 2017-12-03.
 */

public class ElasticUtils {
    private static final String queryYByX = "{\n" +
            "    \"query\" : {\n" +
            "       \"constant_score\" : {\n" +
            "           \"filter\" : {\n" +
            "               \"term\" : {\"%s\": %s}\n" +
            "             }\n" +
            "         }\n" +
            "    }\n" +
            "}";
    private static JestDroidClient client;
    private static final String teamIndex = "cmput301f17t17";


    public static class GetItembyId extends
            AsyncTask<String, Void, JsonObject> {
        /**
         * Assumes that item exists
         *
         * @param params
         * @return
         */
        @Override
        protected JsonObject doInBackground(String... params) {
            verifySettings();
            JsonObject obj = null;
            /**
             * params[0] is "habit" or "event" or "user"
             * param[1] id
             *
             */
            String query = String.format(queryYByX, "_id" ,String.format("\"%s\"", params[1]));
            Search userSearch = new Search.Builder(query)
                    .addIndex(teamIndex)
                    .addType(params[0])
                    .build();
            try{
                SearchResult result = client.execute(userSearch);
                JsonObject hits = result.getJsonObject()
                        .getAsJsonObject("hits");
                if(result.isSucceeded()){
                    obj = hits.getAsJsonArray("hits")
                            .get(0)
                            .getAsJsonObject();
                }
            }catch (Exception e){

            }
            return obj;
        }
    }
    public static class GetItemsByX extends AsyncTask<String, Void, JsonArray> {
        // get habits and events
        @Override
        protected JsonArray doInBackground(String... params) {
            verifySettings();
            JsonArray arr = null;
            /**
             * params[0] is "habit" or "event"
             * param[1] the query term
             * param[2] is the value
             *
             */
            String query = String.format(queryYByX, params[1] , String.format("\"%s\"", params[2]));
            Search Search = new Search.Builder(query)
                    .addIndex(teamIndex)
                    .addType(params[0])
                    .build();
            try{
                SearchResult result = client.execute(Search);
                JsonObject hits = result.getJsonObject().getAsJsonObject("hits");
                if(result.isSucceeded()){
                    arr =  hits.getAsJsonArray("hits");
                }else{
                    Log.d("ESC", "no results");
                }
            }catch (Exception e){
                Log.d("ESC", "execute fail");
            }
            return arr;
        }
    }

    public static class PostItem extends
            AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            verifySettings();
            /**
             * params[0] is "habit" or "event" or "user"
             * param[1] a JsonString
             *
             */
            Index index = new Index.Builder(params[1])
                    .index(teamIndex)
                    .type(params[0])
                    .build();
            try{
                DocumentResult result = client.execute(index);
                if(result.isSucceeded()){
                    return result.getId();
                }else{
                    Log.d("ESC", "no results");
                }

            }catch (Exception e){
                Log.d("ESC", "execute fail");
            }
            return "FAIL";
        }


    }



    public static class AddUser extends AsyncTask<UserProfile, Void, String> {
        // assumes that user does not exist
        // returns userID
        private static final String queryPostNewUser = "{\"name\": \"%s\"," +
                "\"username\": \"%s\"," +
                "\"otherInfo\": \"%s\"," +
                "\"habitsList\": []," +
                "\"friendsList\": []," +
                "\"sentRequestList\": []," +
                "\"receivedRequestsList\": []}";
        @Override
        protected String doInBackground(UserProfile... userProfile) {
            verifySettings();
            UserProfile user = userProfile[0];
            String postQuery = String.format(queryPostNewUser,
                    user.getName(),
                    user.userId(),
                    user.getOtherInfo());
            Index index = new Index.Builder(postQuery)
                    .index(teamIndex)
                    .type("User")
                    .build();
            try{
                DocumentResult result = client.execute(index);
                if(result.isSucceeded()){
                    return result.getId();
                }

            }catch (Exception e){

            }
            return "";
        }
    }
    public static class GetUser extends AsyncTask<String, Void, UserProfile> {
        // assumes that user exists
        // returns UserProfile
        @Override
        protected UserProfile doInBackground(String... params) {
            verifySettings();
            String username = params[0];
            UserProfile user = null;
            String query = String.format(queryYByX, "username" , "\""+username+"\"");
            Search userSearch = new Search.Builder(query)
                    .addIndex(teamIndex)
                    .addType("User")
                    .build();
            try{
                SearchResult result = client.execute(userSearch);
                JsonObject hits = result.getJsonObject().getAsJsonObject("hits");
                if(result.isSucceeded()){
                    JsonObject userData = hits.getAsJsonArray("hits")
                            .get(0).getAsJsonObject();
                    user = new UserProfile(userData);


                }
            }catch (Exception e){

            }
            return user;
        }
    }


    public static class CheckUserExists extends AsyncTask<String, Void, Boolean> {
        // return T/F

        @Override
        protected Boolean doInBackground(String... params) {
            verifySettings();
            /*
            params
                0 -> username ""
             */
            UserProfile user = null;
            String query = String.format(queryYByX, "username" , "\""+params[0]+"\"");
            Search userSearch = new Search.Builder(query)
                    .addIndex(teamIndex)
                    .addType("User")
                    .build();
            Boolean userExists = false;
            try{
                SearchResult result = client.execute(userSearch);
                JsonObject hits = result.getJsonObject().getAsJsonObject("hits");
                if(result.isSucceeded() && hits.get("total").getAsInt() == 1){
                    userExists = true;
                }
            }catch (Exception e){

            }


            return userExists;
        }
    }


    /**
     * from labs
     */
    public static void verifySettings() {
        if (client == null) {
            // classes that build other classes for you
            DroidClientConfig.Builder builder = new DroidClientConfig
                    .Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
