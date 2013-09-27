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
	
	public BitmapManager(Context context, int width, int height){
		// Set default paint settings
		this.context = context;
		initializePaint(Color.WHITE);
		widgetWidth = convertDiptoPix((float)width);
		widgetHeight = convertDiptoPix((float)height);
	}
	
	public void initializePaint(int color){
	    paint = new Paint();
	    Typeface typeface = Typeface.createFromAsset(context.getAssets(), "Aver Italic.ttf");
	    paint.setAntiAlias(true);
	    paint.setTypeface(typeface);
	    paint.setColor(color);	
	}
	
	// This is a hack because remote views are weird
	private Bitmap getFontBitmap(Bitmap bitmap, String text) {
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
	
	public Bitmap getTextOnTransparentBitmap(String text){
	    Bitmap bitmap = Bitmap.createBitmap(widgetWidth, widgetHeight, Bitmap.Config.ARGB_4444);
	    return getFontBitmap(bitmap, text);
	}
	
	public Bitmap getTextOnColoredBitmap(String text, int color){
	    Bitmap bitmap = Bitmap.createBitmap(widgetWidth, widgetHeight, Bitmap.Config.ARGB_4444);
	    bitmap.eraseColor(color);
	    return getFontBitmap(bitmap, text);
	}
	
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
