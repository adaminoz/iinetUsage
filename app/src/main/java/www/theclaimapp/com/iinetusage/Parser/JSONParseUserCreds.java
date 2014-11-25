package www.theclaimapp.com.iinetusage.Parser;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import www.theclaimapp.com.iinetusage.MainActivity;
import www.theclaimapp.com.iinetusage.Model.UserCreds;


public class JSONParseUserCreds {


    public static List<UserCreds> parseFeed(String content) {

        try {
            JSONObject obj = new JSONObject(content);

            List<UserCreds> userCredsList = new ArrayList<>();


            //Gat Auth Token.
            UserCreds userCreds = new UserCreds(); //create instance of userCreds Object.

            userCreds.setToken(obj.getString("token"));

            //Get Service Token.
            JSONObject response = obj.getJSONObject("response");
            JSONArray service_list = response.getJSONArray("service_list");

            String username = MainActivity.username;
            for (int i = 0; i < service_list.length(); i++) {

                JSONObject jObj = service_list.getJSONObject(i);
                if(jObj.getString("pk_v").matches(username)) {
               userCreds.setS_token(jObj.getString("s_token"));
                }
                userCredsList.add(userCreds);
            }
            return userCredsList;

        } catch (JSONException e) {
            e.printStackTrace();
            return  null;

        }

    }
}








