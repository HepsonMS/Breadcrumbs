package com.segfault.android.breadcrumbs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PreferencesConfig {
    //object
    public PreferencesConfig(){
        //Defaults
        this.contactFriendsList = new ArrayList<String>();
        this.authoritiesBool = false;
    }
    public PreferencesConfig(List<String> contactFriendsList, boolean authoritiesBool) {
        this.contactFriendsList = contactFriendsList;
        this.authoritiesBool = authoritiesBool;
    }
    //variables
    private List<String> contactFriendsList;
    private boolean authoritiesBool;

    //getters and setters
    public void setContactFriendsList(List<String> contactFriendsList){
        this.contactFriendsList = contactFriendsList;
    }
    public void setAuthoritiesBool(boolean authoritiesBool){
        this.authoritiesBool = authoritiesBool;
    }

    public String toString(){
        try{
            JSONObject obj = new JSONObject();
            JSONArray arr = new JSONArray(this.contactFriendsList);
            obj.put("Friend List", arr);
            obj.put("Authorities",this.authoritiesBool);
            return obj.toString();
        } catch (JSONException e){
            //pass
        }
        return "ERROR";
    }

}