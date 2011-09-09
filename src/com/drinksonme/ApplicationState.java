package com.drinksonme;

import android.app.Application;
import android.util.Log;

import com.drinksonme.util.FoursquareManager;


// Used to access global variables within the app, by using the Application.getApplicationContext()
public class ApplicationState extends Application{

	private FoursquareManager mFoursquareManager;
	
    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("drinks", "in ApplicationState onCreate");
        mFoursquareManager = new FoursquareManager(this);
    }
     

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
    
    // Method to get the FoursquareManager
    public FoursquareManager getFoursquareManager() {
    	return mFoursquareManager;
    }
	
}
     