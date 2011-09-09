package com.drinksonme.util;

import java.util.ArrayList;

import org.json.JSONException;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


// Class that houses all foursquare related calls 
public class FoursquareManager {
	
	Context mContext;
	FoursquareApi mFsApi;
	public static ArrayList<User> sFriends;
	public static ArrayList<String> sFriendsIDs;
	public static ArrayList<User> sPeopleAtVenue;
	

	// Initiate context and ApiUtils
	public FoursquareManager(Context ctxt) {
		mContext = ctxt;
		mFsApi = new FoursquareApi(mContext);
		sFriendsIDs = new ArrayList<String>();
		sFriends = new ArrayList<User>();
		sPeopleAtVenue = new ArrayList<User>();
	}
	
	
	// Get all foursquare info and set it, return boolean indicating success
	// Get all the data we need in order to populate the lists on the homescreen
	// May return users as they come in??
	public boolean GetFoursquareFriends(Context ctxt, User user) {
		
		// Get array of friends ids
		try { 
			sFriendsIDs = mFsApi.getFriendsIDs(mContext); 
			
		} catch (JSONException e) {Log.v("ERROR", e.toString());}
		
		// If array is not empty, check for venmo connection and get more fs_user data
		if (sFriendsIDs.size() != 0 || sFriendsIDs!=null){
			
			//new CheckIfOnVenmoTask().execute(sFriendsIDs);
			
			for (String id: sFriendsIDs) {
				
				//change to the following to make it asynchronous
				//new GetFsUserInfoTask().execute(); //this is the problem with passing in arguments 
				
				// Make API call to get FS user info based on id
				User mUser = mFsApi.getFsUserInfo(mContext, id); // TODO put this in an async class
				sFriends.add(mUser); 
				
				
			}
		}
		
		// Get last check-in venue of user (make async task)
		
		// Get people from last venue (make async task)

		return true;
    }
	
	/*
	 * Return people at venue
	 */
	public boolean getPeopleAtVenue(Context ctxt)
	{
		User self = mFsApi.getFsUserInfo(mContext,  "self");
		String venue_id = self.mLastCheckin.mVenueId;
		Log.v("drinks", "venue_id: " + venue_id);
		
		//make api call 
		ArrayList<String> peopleIDs = mFsApi.getHereNow(venue_id);
		
		for(String id: peopleIDs)
		{
			sPeopleAtVenue.add(mFsApi.getFsUserInfo(mContext, id));
		}
		
		return true;
	}
	
	// Method to Check if users are on Venmo
	
	// ERROR DUE TO INCORRECT ARGUMENT TYPES
	/**private class CheckIfOnVenmoTask extends AsyncTask<ArrayList<String>, Void, Void> {
		
		@Override
		protected Void doInBackground(ArrayList<String>... friendsIDs) {
			
			Log.v("drinks", "inCheckIfOnVenmo");
			// Make the VenmoAPI call to see if user is on Venmo
			VenmoApi mVenmoApi = new VenmoApi(mContext);
			mVenmoApi.checkIfOnVenmo(mContext, friendsIDs);
			return null;
		}

	}**/
	
	
	// SyncTask to get all FS info for a given contact
	/*private class GetFsUserInfoTask extends AsyncTask<String, Void, Void> {
		
		Boolean mResult; 
			
		@Override
		protected Void doInBackground(String... id) {
			
			Log.v("drinks", "inGetFsUserInfoTask");
			// Make the VenmoAPI call to see if user is on Venmo
			User mUser = mFsApi.getFsUserInfo(mContext, id);
			sFriends.add(mUser);
			
			return null;
		}
	
	}*/
	
	


}
