package www.theclaimapp.com.iinetusage.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import www.theclaimapp.com.iinetusage.Model.ConnectionData;

/**
 * Created by aschickert on 27/11/2014.
 */
public class JSONParseConnectionData {

    public static List<ConnectionData> parseFeed(String content) {

        try {
            JSONObject obj = new JSONObject(content);
            List<ConnectionData> conDataList = new ArrayList<>();

            ConnectionData connectionData = new ConnectionData();

            JSONObject response = obj.getJSONObject("response");

            JSONArray conArray = response.getJSONArray("connections");

            for (int i = 0; i < conArray.length(); i++) {
                JSONObject jObj = conArray.getJSONObject(i);
                connectionData.setOn_since(jObj.getString("on_since"));
                connectionData.setIp(jObj.getString("ip"));

            }

            conDataList.add(connectionData);

            return conDataList;


        } catch (JSONException e) {
            e.printStackTrace();

        }
        return null;
    }

}
