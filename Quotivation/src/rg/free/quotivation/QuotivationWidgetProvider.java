package rg.free.quotivation;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

/*
 * This class handles the app widget broadcasts
 * 
 * @author rgarg
 */
public class QuotivationWidgetProvider extends AppWidgetProvider {
	/*
	 * This method will update widgets when it receives the onUpdate broadcast
	 * 
	 * @see android.appwidget.AppWidgetProvider#onUpdate(android.content.Context, android.appwidget.AppWidgetManager, int[])
	 */
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		// Get the shared preferences
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		// Get resources
		Resources res = context.getResources();
		// Set up default text
		String defaultText = res.getString(R.string.default_quote_text);
		// Obtain widget dimensions
		float density = res.getDisplayMetrics().density;
		float width = res.getDimension(R.dimen.widget_width)/density;
		float height = res.getDimension(R.dimen.widget_height)/density;
		float padding = res.getDimension(R.dimen.widget_padding)/density;
		// Initialize the bitmap manager
		BitmapManager bman = new BitmapManager(context, width-(padding*2), height); // Padding is taken care of here exclusively
		// Set values from preferences
		bman.setTextColor(prefs.getInt("foreground_color", Color.WHITE));
		bman.setTextFont(prefs.getString("render_font", "Aver Italic"));
		// Set up the view
		RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.quotivation_widget);
		// Set the background color for the remote view
		ShapeDrawable bgDrawable = (ShapeDrawable) res.getDrawable(R.drawable.rounded_corners);
		bgDrawable.getPaint().setColor(prefs.getInt("background_color", Color.TRANSPARENT));
		views.setImageViewResource(R.id.quote_text, bgDrawable);
		//views.setInt(R.id.quote_text, "setBackgroundColor", prefs.getInt("background_color", Color.TRANSPARENT));
		// Create and set the bitmap for all affected widgets
		views.setImageViewBitmap(R.id.quote_text, bman.getRenderedText(prefs.getString("quote_text", defaultText)));
		for(int appWidgetId : appWidgetIds){
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
	
	/*
	 * This method creates the rounded corner shape that will be used for the background
	 */
}
