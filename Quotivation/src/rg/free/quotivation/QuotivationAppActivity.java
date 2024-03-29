package rg.free.quotivation;

import net.margaritov.preference.colorpicker.ColorPickerDialog;
import net.margaritov.preference.colorpicker.ColorPickerPanelView;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
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
public class QuotivationAppActivity extends Activity {
	// Maintain a reference to the shared preferences
	SharedPreferences prefs;
	// Maintain a reference to the bitmap manager
	BitmapManager fontPreviewBitmap;

	/*
	 * onCreate method
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the view
        setContentView(R.layout.activity_main);
        // Initialize shared variables
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        fontPreviewBitmap = new BitmapManager(getBaseContext(), 100, 50);
        // Update the font preview
        updateFontPreview();
        // Activate the form listeners
        activateFormListeners();
    }

    /*
     * onCreateOptionsMenu method
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
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
    
    /*
     * Method to activate form listeners
     */
    protected void activateFormListeners(){
    	// Set up the listener for the font choice
    	Spinner fontSpinner = (Spinner) findViewById(R.id.font_choice_spinner);
    	
    	// Set the default font choice to be Aver Italic
    	fontSpinner.setSelection(1);
    	// Set up the
    	fontSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// Get the font that was selected
				String fontName = (String) parent.getItemAtPosition(position);
				// Save it as a preference
				SharedPreferences.Editor prefsEditor = prefs.edit();
				prefsEditor.putString("render_font", fontName);
				// Update the font preview with the given font
				fontPreviewBitmap.setTextFont(fontName);
				// Redraw the preview
				updateFontPreview();
				// Commit the preference
				prefsEditor.commit();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// Do nothing
			}
		});
    	
    	// Set up listener for the foreground color panel
    	activateColorSwatch((ColorPickerPanelView) findViewById(R.id.foreground_color_panel), "foreground_color", 0xAAAAAA, false);
    	
    	// Set up listener for the background color panel
    	activateColorSwatch((ColorPickerPanelView) findViewById(R.id.background_color_panel), "background_color", 0xffffff, true);
    }
    
    /*
     * This method is for activating the color swatches in the form
     */
    protected void activateColorSwatch(final ColorPickerPanelView view, final String prefName, int defaultColor, final Boolean background){
    	// Get a handle for the font preview image view
    	final ImageView iv = (ImageView) findViewById(R.id.font_preview);
    	// Create the color dialog
		final ColorPickerDialog colorDialog = new ColorPickerDialog(this, defaultColor){			
			@Override
			public void onColorChanged(int color) {
				super.onColorChanged(color);
				// Save it as a preference
				SharedPreferences.Editor prefsEditor = prefs.edit();
				prefsEditor.putInt(prefName, color);
				if(background){
					// Update the font preview with the given color
					iv.setBackgroundColor(color);
				} else {
					// Update the font preview with the given font
					fontPreviewBitmap.setTextColor(color);
					// Redraw the preview
					updateFontPreview();
				}
				// Set the color of the swatch
				view.setColor(color);
				// Commit the preference
				prefsEditor.commit();
			}
		};
		// We want to be able to control alpha
		colorDialog.setAlphaSliderVisible(true);   	
    	// Set up listener for this swatch
    	view.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// Show the color picker dialog
				colorDialog.show();
			}
    	});    	
    }
    
    /*
     * Method to update the font preview within this activity
     */
    protected void updateFontPreview(){
    	ImageView fontPreview = (ImageView) findViewById(R.id.font_preview);
    	fontPreview.setImageBitmap(fontPreviewBitmap.getRenderedText("Sample"));
    }
    
    /*
     * This will be called when the user is leaving the activity, and is
     * essentially used to make the widgets update
     * 
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause(){
    	super.onPause();
    	Intent intent = new Intent(this,QuotivationWidgetProvider.class);
    	intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
    	int ids[] = AppWidgetManager.getInstance(getApplication())
    					.getAppWidgetIds(new ComponentName(getApplication(),
    									     QuotivationWidgetProvider.class));
    	intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
    	sendBroadcast(intent);    	
    }
}
