package rg.free.quotivation;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Spinner;

/*
 * This is the activity associated with the main application
 * This is also where the shared preferences can be manipulated
 * 
 * @author rgarg
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateFontPreview();
        activateFormListeners();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /*
     * This method will handle the selection of menu items
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_place_widget:
            	// 1. Instantiate an AlertDialog.Builder with its constructor
            	AlertDialog.Builder builder = new AlertDialog.Builder(this);

            	// 2. Chain together various setter methods to set the dialog characteristics
            	builder.setMessage(R.string.place_widget_message)
            	       .setTitle(R.string.place_widget_title);

            	// 3. Get the AlertDialog from create()
            	AlertDialog dialog = builder.create();
            	dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    protected void activateFormListeners(){
    	Spinner fontSpinner = (Spinner) findViewById(R.id.font_choice_spinner);
    	fontSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
    }
    
    protected void updateFontPreview(){
    	ImageView fontPreview = (ImageView) findViewById(R.id.font_preview);
    	BitmapManager bman = new BitmapManager(getBaseContext(), 500, 110);
    	float hsv[] = {0.0f,0.0f,1.0f};
    	fontPreview.setImageBitmap(bman.getTextOnColoredBitmap("Sample", Color.HSVToColor(128,hsv)));

    }
}
