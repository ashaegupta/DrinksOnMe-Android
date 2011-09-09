package com.drinksonme;

import com.drinksonme.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//Connects to Foursquare Oauth
public class FoursquareOauthActivity extends Activity {
	
    private static final String TAG = "FoursquareConnectActivity";
	
    //Venmo Foursquare oauth values
    public static final String CALLBACK_URL = "";  //TODO Enter fill this out
    public static final String CLIENT_ID = ""; //TODO Enter fill this out
    
    public String mUserAuthorized = "0";
    public String accessToken;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.v("drinks", "FoursquareConnectActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        
        String url =
            "https://foursquare.com/oauth2/authenticate" + 
                "?client_id=" + CLIENT_ID + 
                "&response_type=token" + 
                "&redirect_uri=" + CALLBACK_URL;
        
        
        WebView webview = (WebView)findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            
        	@Override
        	public boolean shouldOverrideUrlLoading(WebView view, String url) {
        		
        		int isCallback = url.indexOf(CALLBACK_URL);
        		String fragment = "#access_token=";
	            int start = url.indexOf(fragment);
        		
        		//If redirecting to Venmo callback site, return to app
	            if (isCallback == 0){
	            	 Log.v(TAG, "redirect detected");
		            // If URL contains access token, store it
		            if (start > -1) {
		                
		            	mUserAuthorized = "1";
		            	
		            	// Get the access token and make the API call
		                accessToken = url.substring(start + fragment.length(), url.length());
		                
		                Log.v("drinks", accessToken);
		                Log.v("drinks", mUserAuthorized);
		                
		            } 
		            Intent intent = new Intent();
		            intent.putExtra("user_allowed_fs_connection", mUserAuthorized);
		            intent.putExtra("user_token", accessToken);
		            setResult(RESULT_OK, intent);
		            FoursquareOauthActivity.this.finish();
		            return true;
        		}     		
                else {
                	return false;
                }
        	}
        });
        Log.v(TAG, "url");
        webview.loadUrl(url);
    }
}