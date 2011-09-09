package com.drinksonme.util;

import android.content.Context;
import android.content.SharedPreferences;

public class DrinksSettings {

	public static final boolean DEBUG = false;
	
	// Check in VenmoApp where this is called -- see if necessary (might not be)
	public static void setUserCredentials(Context ctxt, String oauthToken) {
		SharedPreferences settings = ctxt.getSharedPreferences("DrinksOnMe", 0);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString("foursquare_token", oauthToken);
	    editor.commit();
	}
	
	
	// Call this upon logout
	public static void clear(Context ctxt) {
		SharedPreferences settings = ctxt.getSharedPreferences("DrinksOnMe", 0);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString("foursquare_token", "");
	    editor.commit();
	}

	public static String getFoursquareToken(Context ctxt){
		SharedPreferences settings = ctxt.getSharedPreferences("Venmo", 0);
		return settings.getString("foursquare_token", "");
	}
	
	public static void setFoursquareToken(Context ctxt, String token){
		SharedPreferences settings = ctxt.getSharedPreferences("Venmo", 0);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString("foursquare_token", token);
	    editor.commit();
	}
	
}
