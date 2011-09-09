package com.drinksonme.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

//Class that houses all venmo API calls 
public class VenmoApi {
	
	String ROOT = "https://api.foursquare.com/v2/";
	String VERSION = "&v=20110908";
	
	String CLIENT_ID = "";
	String SECRET = "";
	
	Context mContext;
	ApiUtils mApi;
	ArrayList<User> mFriends;
	ArrayList<String> mFriendsIDs;
	ArrayList<String> mFriendsPhotos;


	// Initiate context and ApiUtils
	public VenmoApi(Context ctxt) {
		mContext = ctxt;
		mApi = new ApiUtils();
		mFriendsPhotos = new ArrayList<String>();
		mFriendsIDs = new ArrayList<String>();
		mFriends = new ArrayList<User>();
	}
	
	
	// Determine if array of fsIds are on venmo
	public void checkIfOnVenmo(Context ctxt, ArrayList<String> fsIds) throws JSONException{
		
		Boolean onVenmo; 
		
		for (String id : fsIds) {
			
			onVenmo = (Integer.parseInt(id) % 2 == 0); // Pseudo check to see if on Venmo
				
				//Find the user who matches the user ID and make set their onVenmo attribute.
				
		}
	}
		
	
}
