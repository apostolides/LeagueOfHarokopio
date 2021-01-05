package gr.hua.DistSysApp.ritoAPI.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultUtils {
    public static String getActiveGameResults(String summonerName, String APIKey) throws JSONException {

        String url = UrlUtils.getSummonersURL(summonerName,APIKey);

        String response;
        try {
            response = Requests.get(url);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error Accessing Url";
        }
        //TODO handle null exception
        if(response==null) return "Expired API KEY or Wrong Summoner Name";

        // JSONParser parser = new JSONParser(response);
        JSONObject jsonObj = new JSONObject(response);
        String summonerId=null;

        try{
            summonerId = JsonUtils.getSummonerId(jsonObj);
        } catch (JSONException e) {
            e.printStackTrace();
            return "Error JSON Parser";
        }

        if (summonerId==null) return "Error";

        String url2 = UrlUtils.getActiveGameURL(summonerId,APIKey);
        String activeGameResults = Requests.get(url2);

        if(activeGameResults != null) {
            return activeGameResults;
        }else {
            return "Error";
        }
    }
}
