package www.theclaimapp.com.iinetusage.Parser;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import www.theclaimapp.com.iinetusage.Model.UserCreds;

public class JSONParseUserCreds {

    public static List<UserCreds> parseFeed(String content) {

        try {
            List<UserCreds> userCredsList = new ArrayList<>();

            JSONObject obj = new JSONObject(content);

 //           JSONObject response = obj.getJSONObject("response");

  //          JSONObject service_list = response.getJSONObject("service_list");

            //Gat Auth Token.
            UserCreds userCreds = new UserCreds();
            userCreds.token = obj.getString("token");

            userCredsList.add(userCreds);

            return userCredsList;

        } catch (JSONException e) {
            e.printStackTrace();

        }return  null;

    }
}








