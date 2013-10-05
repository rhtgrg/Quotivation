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
		// Obtain widget dimensions
		Resources res = context.getResources();
		float density = res.getDisplayMetrics().density;
		float width = res.getDimension(R.dimen.widget_width)/density;
		float height = res.getDimension(R.dimen.widget_height)/density;
		float padding = res.getDimension(R.dimen.widget_padding)/density;
		// Initialize the bitmap manager
		Log.w("wt", ""+width);
		Log.w("height", ""+height);
		BitmapManager bman = new BitmapManager(context, width-(padding*2), height); // Padding is taken care of here exclusively
		// Set values from preferences
		bman.setTextColor(prefs.getInt("", Color.WHITE));
		bman.setTextFont(prefs.getString("", "Aver Italic"));
		// Set up the view
		RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.quotivation_widget);
		// Create and set the bitmap for all remote views
		views.setImageViewBitmap(R.id.quote_text, bman.getRenderedText("“This is some fancy quote by some fancy guy, except now it is a lot longer and now it is even longer I have no idea”"));
		for(int appWidgetId : appWidgetIds){
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
}
