package com.drinksonme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.drinksonme.util.DrinksSettings;
import com.drinksonme.util.FoursquareManager;
import com.drinksonme.util.User;
import com.drinksonme.util.ViewTools;


public class MainActivity extends Activity {

	Context mContext;
	ApplicationState mAppState;
	User mUser = new User();
	
   
	// Launched when the app is first opened
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.v("drinks", "in Main Activity");
        
        mAppState = (ApplicationState)getApplicationContext();
        mContext = this;
        
        
        Log.v("drinks", DrinksSettings.getFoursquareToken(mContext));
        
       // If they are not already connected to foursquare, connect them
       if(DrinksSettings.getFoursquareToken(mContext).equals("")
    		   || DrinksSettings.getFoursquareToken(mContext)==null){ 
    	   foursquareOauth();
       }else{
    	   
    	   // Start foursquare API calls to get friends list and last check-in
           new GetFoursquareFriendsTask().execute();   
           new GetPeopleAtVenueTask().execute(); 
        	
       }
    	
        
    	 // Retrieve this user's data
    	 // Launch the task to load the contacts screen 
    	 
       
    }
	
	
	
    // Listen and process results from the foursquare oauth
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
    	/** Use this to process ANY activity results that come through; Call startActivityForResult(intent, requestCode)
    	 * For example, see TransactionActivity
    	switch(requestCode)
    		case 1:	*/
    			
    	// Check to see if connection was successful
		if (resultCode == RESULT_OK 
				&& data.getExtras().get("user_allowed_fs_connection") != null
				&& data.getExtras().get("user_allowed_fs_connection").equals("1")){
				
			// Store the token
			String token = (String) data.getExtras().get("user_token");
			DrinksSettings.setFoursquareToken(mContext, token);
			new GetFoursquareFriendsTask().execute();
			
			// new GetPeopleAtVenueTask().execute(); 
			
			
		} 
		
		// If not successful, pop up warning
		else {
			AlertDialog d = ViewTools.createDialog(mContext, "Sorry, we were unable to connect you to foursquare");
			d.show();
			// Create a listener to see when they have dismissed or clicked OK and then re prompt them to connect
			// foursquareOauth();
		}
    }
    
    
    
    // Async task to get the user's foursquare info
	// Verify password if user has security settings enabled.
	private class GetFoursquareFriendsTask extends AsyncTask<Void, Void, Void> {
		
		Boolean mResult;

		@Override
		protected Void doInBackground(Void... voids) {
			Log.v("drinks", "inGetFoursquareInfo");
			
			// Make the FoursquareAPI call to retrieve foursquare info
			
			// Unsure of what the return type should be, could be User and then you would populate the users as they come in
			// One way to get data would be to access the public array that's part of the FoursquareManager
			// mAppState.getFoursquareManager().sFriends;
			
			mResult = mAppState.getFoursquareManager().GetFoursquareFriends(mContext, mUser); // May not need to pass in user
			return null;
		}

		@Override
		protected void onPostExecute(Void v) {
			if (mResult == true) {
				// Run the task to show the contacts
			} 
			else {
				AlertDialog d = ViewTools.createDialog(mContext,
						"Unable to retreive your foursquare data :(");
				d.show();
				foursquareOauth();
			}
		} 
	}

	private class GetPeopleAtVenueTask extends AsyncTask<Void, Void, Void> 
	{
		private boolean mResult;
		protected Void doInBackground(Void...voids)
		{
			mResult = mAppState.getFoursquareManager().getPeopleAtVenue(mContext);
			return null;
		}
	}
	
	// Prompt foursquare Oauth Activity
	private void foursquareOauth() {
		Intent intent = new Intent(MainActivity.this, FoursquareOauthActivity.class);
			startActivityForResult(intent, 1);
	}
	
	// Method to populate the lists
	// get the lists from the foursquareManager
	// 
    
    
    
}