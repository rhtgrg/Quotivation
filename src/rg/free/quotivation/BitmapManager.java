package rg.free.quotivation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.TypedValue;

public class BitmapManager {
	// This is a hack because remote views are weird
	public static Bitmap getFontBitmap(Context context, String text, int color, float fontSizeSP) {
	    int fontSizePX = convertDiptoPix(context, fontSizeSP);
	    int pad = (fontSizePX / 9);
	    Paint paint = new Paint();
	    Typeface typeface = Typeface.createFromAsset(context.getAssets(), "Aver.ttf");
	    paint.setAntiAlias(true);
	    paint.setTypeface(typeface);
	    paint.setColor(color);
	    paint.setTextSize(fontSizePX);

	    int textWidth = (int) (paint.measureText(text) + pad * 2);
	    int height = (int) (fontSizePX / 0.75);
	    Bitmap bitmap = Bitmap.createBitmap(textWidth, height, Bitmap.Config.ARGB_4444);
	    Canvas canvas = new Canvas(bitmap);
	    float xOriginal = pad;
	    canvas.drawText(text, xOriginal, fontSizePX, paint);
	    canvas.drawCircle(0, 0, 1000000000, paint);
	    return bitmap.copy(Bitmap.Config.ARGB_8888, true);
	}
	
	public static int convertDiptoPix(Context context, float dip) {
	    int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
	    return value;
	}
}
