package www.theclaimapp.com.iinetusage.Parser;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import www.theclaimapp.com.iinetusage.Model.UsageData;

public class JSONParseUsageData {

    public static List<UsageData> parseFeed(String content) {

        try {
            JSONObject obj = new JSONObject(content);
            List<UsageData> usageDataList = new ArrayList<>();

            UsageData usageData = new UsageData();

            JSONObject response = obj.getJSONObject("response");

            JSONObject usage = response.getJSONObject("usage");

            JSONArray traffic_types = usage.getJSONArray("traffic_types");

            for (int i = 0; i < traffic_types.length(); i++) {

                JSONObject jObj = traffic_types.getJSONObject(i);
                usageData.setName(jObj.getString("name"));
              }

            usageDataList.add(usageData);

            return usageDataList;


        } catch (JSONException e) {
            e.printStackTrace();

        }
        return null;
    }


}
