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
		BitmapManager bman = new BitmapManager(context, 250, 110);
		RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.quotivation_widget);
		views.setImageViewBitmap(R.id.quote_text, bman.getTextOnTransparentBitmap("“This is some fancy quote by some fancy guy, except now it is a lot longer and now it is even longer I have no idea”"));
		for(int appWidgetId : appWidgetIds){
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
}
