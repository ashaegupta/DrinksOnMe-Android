package com.drinksonme.util;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;


public class ViewTools {
    
	private static ProgressDialog sProgressDialog;
	
	public static Dialog showProgressDialog(Context ctxt, String title, String message) {
        ProgressDialog dialog = new ProgressDialog(ctxt);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                //TODO: Change this.
            	//ctxt.finish();
            }
        });
        sProgressDialog = dialog;
        sProgressDialog.show();
        return sProgressDialog;
    }
	
	

	public static AlertDialog createDialog(Context ctxt, String message) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(ctxt);
    	builder.setMessage(message)
    		     .setNeutralButton("OK", new DialogInterface.OnClickListener() {
    		     		public void onClick(DialogInterface dialog, int id) {
    		        	dialog.cancel();
    		        }
    		      });
    	return builder.create();
	}
	
    public static void dismissProgressDialog() {
    	try {
    		if (sProgressDialog != null)
    			sProgressDialog.dismiss();
    	} catch (IllegalArgumentException e) {
    		// We don't mind. android cleared it for us.
    	}
    }    

}
