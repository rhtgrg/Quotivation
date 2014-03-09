package rg.free.quotivation;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

/*
 * This class is responsible for the actual rendering of the widget content
 * 
 * @author rgarg
 */
public class BitmapManager {
	Paint paint;
	Context context;
	int widgetHeight;
	int widgetWidth;
	int color = Color.WHITE;
	
	/*
	 * Constructor method
	 */
	public BitmapManager(Context context, float width, float height){
		// Set default paint settings
		this.context = context;
		initializePaint();
		widgetWidth = convertDiptoPix((float)width);
		widgetHeight = convertDiptoPix((float)height);
	}
	
	/*
	 * Utility method to initialize paint on the first run
	 */
	protected void initializePaint(){
	    paint = new Paint();
	    paint.setAntiAlias(true);
	    setTextColor(Color.WHITE);
	    setTextFont("Aver Italic");
	}
	
	/*
	 * Public method to set the color of the text in the generated bitmap
	 */
	public void setTextColor(int color){
	    paint.setColor(color);	
	}

	/*
	 * Public method to set the font of the text in the generated bitmap
	 */
	public void setTextFont(String fontName){
		Log.d("QUOT", "Font is set to " + fontName);
	    Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName+".ttf");
	    paint.setTypeface(typeface);		
	}
	
	/*
	 * This method creates a bitmap onto which the given text is rendered -
	 * somewhat hacky, because RemoteViews don't like custom fonts
	 */
	private Bitmap getFontBitmap(Bitmap sourceBitmap, String text) {
		// Created a scaled bitmap seems to make it a bit sharper
		//Bitmap bitmap = Bitmap.createScaledBitmap(sourceBitmap, widgetWidth, widgetHeight, true);
		Bitmap bitmap = sourceBitmap;
	    Canvas canvas = new Canvas(bitmap);
	    // Get an array of lines from the given text
	    ArrayList<String> lines = wrapText(text);
	    // Determine the line height we will use
	    for(int i=0; i<lines.size(); i++){
	    	Log.d("Quotivation", lines.get(i));
		    canvas.drawText(lines.get(i), (float)0, paint.getTextSize()*(i+1), paint);
	    }
	    return bitmap;
	}
	
	/*
	 * Public method to get text rendered onto a bitmap, given a string
	 */
	public Bitmap getRenderedText(String text){
	    Bitmap bitmap = Bitmap.createBitmap(widgetWidth, widgetHeight, Bitmap.Config.ARGB_4444);
	    return getFontBitmap(bitmap, text);
	}
	
	/*
	 * This method converts dips to pixels
	 */
	private int convertDiptoPix(float dp) {
	    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
	    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
	    return px;
	}
	
	/*
	 * This method figures out exactly how the lines should be wrapped given the paint size
	 */
	private ArrayList<String> wrapText(String originalText){
		// Initially, we will go with font size = 60
		paint.setTextSize(60);
		// Container for dimensions
		Rect dimensions = new Rect();
		// Break the text into lines
		ArrayList<String> lines;
		// Loop until we are happy with the size!
		do {
			String temporaryText = originalText;
			lines = new ArrayList<String>();
			while(paint.measureText(temporaryText)>widgetWidth){
				// Get width per character
				float charWidth = paint.measureText(temporaryText)/temporaryText.length();
				// Get number of characters to chop
				int numToChop = (int) Math.floor(widgetWidth/charWidth);
				// Try to chop into whole words if possible
				int spaceIndex = temporaryText.lastIndexOf(" ", numToChop);
				if(spaceIndex != -1){
					numToChop = spaceIndex;
				}
				// Chop characters and add to lines
				lines.add(temporaryText.substring(0, numToChop));
				temporaryText = temporaryText.substring(numToChop);
			}
			lines.add(temporaryText);
			paint.getTextBounds(originalText, 0, originalText.length(), dimensions);
			paint.setTextSize(paint.getTextSize()-1);
		} while(dimensions.height()*lines.size()>widgetHeight);
		return lines;
	}
}
