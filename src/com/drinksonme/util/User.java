package com.drinksonme.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;



// Holds all foursquare and venmo data for a user
public class User {
	public String mFsId;
	public String mEmail;
	public String mPhone;
	public String mTwitter;
	public String mFacebook;
	public String mFirstName;
	public String mLastName;
	public String mFullName;
	public String mPhoto;
	public LastCheckin mLastCheckin;
	public Boolean mOnVenmo;
	public String mVenmoId;

	
	// Class that holds all the data related to the users last checkin
	public class LastCheckin {
		public Integer mCreatedAt; //the last time they checked in, in msec from 1970 format 
		public String mPrettyDate; // THIS we need to create 
		public Double mLng;
		public Double mLat;
		public String mCity;
		public String mCrossStreet;
		public String mVenueId;
		public String mName;
		public String mState;
		public String mAddress;
	}
	
	
	// Parses all the user info from json into a user object
	public static User userFromJson(JSONObject json) {
		User mUser = new User();
		mUser.mLastCheckin = mUser.new LastCheckin();

		try {
			
			// Set general info
			mUser.mFsId = (String)json.get("id");
			mUser.mFirstName = (String)json.get("firstName");
			mUser.mLastName = (String)json.get("lastName");		
			mUser.mPhoto = (String)json.get("photo");
			mUser.mFullName = mUser.mFirstName + " " + mUser.mLastName;
			
			// Set contact info
			JSONObject contact = (JSONObject)json.get("contact");
				try {
					if (contact.has("email")) 
						mUser.mEmail = (String)contact.get("email");
					if (contact.has("facebook")) 
						mUser.mFacebook = (String)contact.get("facebook");
					if (contact.has("phone")) 
						mUser.mPhone = (String)contact.get("phone");
					if (contact.has("twitter")) 
						mUser.mTwitter = (String)contact.get("twitter");
				} catch (JSONException e) { Log.v("ERROR", e.toString());}
				
				
			// Set last checkin data
			JSONObject checkins = (JSONObject)json.get("checkins");
			
				JSONArray items = (JSONArray)checkins.get("items");
				JSONObject item = (JSONObject)items.get(0);
				mUser.mLastCheckin.mCreatedAt = (Integer)item.get("createdAt");
				
				JSONObject venue = (JSONObject)item.get("venue");
				
				mUser.mLastCheckin.mVenueId = (String)venue.get("id");
				mUser.mLastCheckin.mName = (String)venue.get("name");
				JSONObject location = (JSONObject)venue.get("location");
				mUser.mLastCheckin.mAddress = (String)location.get("address");
				mUser.mLastCheckin.mCrossStreet = (String)location.get("crossStreet");
				mUser.mLastCheckin.mLat = (Double)location.get("lat");
				mUser.mLastCheckin.mLng = (Double)location.get("lng");
				mUser.mLastCheckin.mCity = (String)location.get("city");

			} catch (JSONException e) { Log.v("ERROR", e.toString());}

		Log.v("drinks", "END" + mUser.mFirstName);
		
		return mUser;
	}
	
	
	

}
