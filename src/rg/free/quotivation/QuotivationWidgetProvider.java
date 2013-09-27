package rg.free.quotivation;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
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
		// Obtain widget dimensions
		Resources res = context.getResources();
		float width = res.getDimension(R.dimen.widget_width);
		float height = res.getDimension(R.dimen.widget_height);
		float padding = res.getDimension(R.dimen.widget_padding);
		// Initialize the bitmap manager and the remote view
		BitmapManager bman = new BitmapManager(context, width-(padding*2), height); // Padding is taken care of here exclusively
		RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.quotivation_widget);
		// Create and set the bitmap for all remote views
		views.setImageViewBitmap(R.id.quote_text, bman.getTextOnTransparentBitmap("“This is some fancy quote by some fancy guy, except now it is a lot longer and now it is even longer I have no idea”"));
		for(int appWidgetId : appWidgetIds){
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
}
