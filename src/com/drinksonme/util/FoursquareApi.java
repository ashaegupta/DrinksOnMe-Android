package com.drinksonme.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

//Class that houses all foursquare API calls 
public class FoursquareApi {
	
	String ROOT = "https://api.foursquare.com/v2/";
	Context mContext;
	ApiUtils mApi;
	ArrayList<User> mFriends;
	ArrayList<String> mFriendsIDs;
	ArrayList<String> mFriendsPhotos;


	// Initiate context and ApiUtils
	public FoursquareApi(Context ctxt) {
		mContext = ctxt;
		mApi = new ApiUtils();
		mFriendsPhotos = new ArrayList<String>();
		mFriendsIDs = new ArrayList<String>();
		mFriends = new ArrayList<User>();
	}
	
	
	// Get array of friends foursquare ids
	public ArrayList<String> getFriendsIDs(Context ctxt) throws JSONException{
		
		// Set token, url and do the HTTP Request
		String url = makeUrl(mContext, "users/self/friends");
		JSONObject rawJSON = mApi.doHTTPRequest(url);
		
		ArrayList<String> friendsIDs = new ArrayList<String>();
		
		// Parse the JSON
		try {
			JSONObject response = (JSONObject) rawJSON.get("response");
			JSONObject friends = (JSONObject) response.get("friends");
			JSONArray items = (JSONArray)friends.get("items");
			
			for(int i=0;i < items.length();i++) {   
				JSONObject userJSON = items.getJSONObject(i);
				friendsIDs.add((String)userJSON.get("id"));
			}
			
		} catch (JSONException e) {Log.v("ERROR", e.toString());}
		
		return friendsIDs;
		
	}
	
	
	// Get and set FS user info 
	public User getFsUserInfo(Context ctxt, String id) {
		
		// Initialize the user
		User mUser = new User();
		
		// Do the HTTP request
		String url = makeUrl(mContext, "users/" + id);
		JSONObject rawJSON = mApi.doHTTPRequest(url);
		
		// Parse the JSON
		try {
			JSONObject response = (JSONObject) rawJSON.get("response");
			JSONObject userJSON = (JSONObject) response.get("user");
			mUser = User.userFromJson(userJSON);
			
		} catch (JSONException e) {
			Log.v("ERROR", e.toString());
		}
		
		return mUser;
	}
	
	public ArrayList<String> getHereNow(String venue_id)
	{
		String url = makeUrl(mContext, "venues/" + venue_id + "/herenow");
		JSONObject rawJSON = mApi.doHTTPRequest(url);
		
		ArrayList<String> peopleAtVenueIDs = new ArrayList<String>();
		
		//parse the JSON
		try
		{
			JSONObject response = (JSONObject) rawJSON.get("response");
			JSONObject herenow = (JSONObject) response.get("hereNow");
			JSONArray items = (JSONArray) herenow.get("items");
			
			for(int i=0;i < items.length();i++) { 
				JSONObject itemJSON = items.getJSONObject(i);
				JSONObject userJSON = (JSONObject) itemJSON.get("user");
				peopleAtVenueIDs.add((String)userJSON.get("id"));
				Log.v("drinks", "id***: " + (String)userJSON.get("id"));
			}
		}
		catch(JSONException e)
		{
			Log.v("ERROR", e.toString());
		}
		return peopleAtVenueIDs;
	}
	
	
	
	
	// Helper method to set-up api url
	private String makeUrl(Context ctxt, String apiAction) {
		String token = DrinksSettings.getFoursquareToken(ctxt); 
		String url = ROOT + apiAction + "?" + "oauth_token=" + token;
		return url;
	}
	

}
