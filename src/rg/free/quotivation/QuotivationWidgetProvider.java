package rg.free.quotivation;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.RemoteViews;
import android.widget.TextView;

public class QuotivationWidgetProvider extends AppWidgetProvider {
	public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int mAppWidgetId, Bundle newOptions){
		RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.quotivation_widget);
		views.setImageViewBitmap(R.id.quote_text, BitmapManager.getFontBitmap(context, "This is some fancy quote by some fancy guy", Color.GREEN,10));
		appWidgetManager.updateAppWidget(mAppWidgetId, views);
	}
}
