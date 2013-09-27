package rg.free.quotivation;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
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
	int fontSize;
	int widgetHeight;
	int widgetWidth;
	int color = Color.WHITE;
	
	public BitmapManager(Context context, int width, int height){
		// Set default paint settings
		this.context = context;
		initializePaint(Color.WHITE, 60);
		widgetWidth = convertDiptoPix((float)width);
		widgetHeight = convertDiptoPix((float)height);
	}
	
	public void initializePaint(int color, int fontSize){
		this.fontSize = fontSize;
	    paint = new Paint();
	    Typeface typeface = Typeface.createFromAsset(context.getAssets(), "Aver Italic.ttf");
	    paint.setAntiAlias(true);
	    paint.setTypeface(typeface);
	    paint.setColor(color);
	    paint.setTextSize(fontSize);		
	}
	
	// This is a hack because remote views are weird
	private Bitmap getFontBitmap(Bitmap bitmap, String text) {
	    Canvas canvas = new Canvas(bitmap);
	    // Get an array of lines from the given text
	    ArrayList<String> lines = wrapText(text);
	    // Determine the line height we will use
	    int offset = widgetHeight/lines.size();
	    for(int i=0; i<lines.size(); i++){
	    	Log.d("Quotivation", lines.get(i));
		    canvas.drawText(lines.get(i), (float)0, fontSize+(offset*i), paint);
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
	
	private int convertDiptoPix(float dip) {
	    int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
	    return value;
	}
	
	/*
	 * This method figures out exactly how the lines should be wrapped given the paint size
	 */
	private ArrayList<String> wrapText(String originalText){
		ArrayList<String> lines = new ArrayList<String>();
		while(paint.measureText(originalText)>widgetWidth){
			// Get width per character
			float charWidth = paint.measureText(originalText)/originalText.length();
			// Get number of characters to chop
			int numToChop = (int) Math.floor(widgetWidth/charWidth);
			// Try to chop into whole words if possible
			int spaceIndex = originalText.lastIndexOf(" ", numToChop);
			if(spaceIndex != -1){
				numToChop = spaceIndex;
			}
			// Chop characters and add to lines
			lines.add(originalText.substring(0, numToChop));
			originalText = originalText.substring(numToChop);
		}
		lines.add(originalText);
		return lines;
	}
}
