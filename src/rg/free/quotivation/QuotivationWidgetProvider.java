package rg.free.quotivation;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
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
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		Log.w("HEY", "I was called");
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		BitmapManager bman = new BitmapManager(context);
		RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.quotivation_widget);
		views.setImageViewBitmap(R.id.quote_text, bman.getFontBitmap("“This is some fancy quote by some fancy guy”"));
		for(int appWidgetId : appWidgetIds){
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
}
