package edu.temple.mapchat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParse {
    public static String[] names;
    private JSONArray partners = null;
    List<Partners> Partners;
    private String json;

    public JSONParse(String json){

        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject = null;

        try {
            partners = new JSONArray(json);
            names = new String[partners.length()];
            Partners = new ArrayList<>();

            for(int i = 0; i< partners.length(); i++){
                Partners partnersObject =  new Partners();

                jsonObject = partners.getJSONObject(i);

                names[i] = jsonObject.getString("username");

                partnersObject.setUsername(names[i]);
                Partners.add(partnersObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    List<Partners> getPartners() {

        return Partners;
    }
}
