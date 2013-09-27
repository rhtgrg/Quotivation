package rg.free.quotivation;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;

public class WidgetConfigure extends Activity {
	int mAppWidgetId = -1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	// Set the layout
    	setContentView(R.layout.activity_configure);
    	// Set this in case user quits
    	setResult(RESULT_CANCELED);
    	// Set the ID of the widget that called us
    	Intent intent = getIntent();
    	Bundle extras = intent.getExtras();
    	if (extras != null) {
    	    mAppWidgetId = extras.getInt(
    	            AppWidgetManager.EXTRA_APPWIDGET_ID, 
    	            AppWidgetManager.INVALID_APPWIDGET_ID);
    	}
    	
    	// TODO - Move this to AFTER the configuration is finished
    	Intent resultValue = new Intent();
    	resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
    	setResult(RESULT_OK, resultValue);
    	finish();
    }
}
