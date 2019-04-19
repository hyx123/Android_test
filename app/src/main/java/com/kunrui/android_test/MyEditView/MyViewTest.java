/* Created by hyx
 * In 2019.4.17
 * Touch on ListView
 * https://www.cnblogs.com/crashmaker/p/3521310.html 参考
 * https://www.cnblogs.com/crashmaker/p/3530213.html 参考
 */
package com.kunrui.android_test.MyEditView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


import com.kunrui.android_test.R;

public class MyViewTest extends View {
    private int color;
    private String mText;
    private int textSize, mAscent;
    private Paint mTextPaint;

    @SuppressLint("ResourceAsColor")
    public MyViewTest(Context context, AttributeSet attrs) {
        super(context);
        initLabelView();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomMenu);
        try {
            mText = a.getString(R.styleable.CustomMenu_textString);
            color = a.getColor(R.styleable.CustomMenu_colorValue, R.color.textRed);
            if (mText != null) {
                setCustomText(mText);
            }
            setTextColor(color);
            textSize = a.getDimensionPixelOffset(R.styleable.CustomMenu_textSize, 20);
            if (textSize > 0) {
                setTextSize(textSize);
            }
        } finally {
            a.recycle();
        }

        Log.e("MyViewTest:", "start");
    }

    private void setCustomText(String text) {
        Log.e("setCustomText:", text);
        mText = text;
        requestLayout();
        invalidate();
    }

    private void initLabelView() {
        Log.e("initLabelView:", "start");
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true); //抗锯齿
        mTextPaint.setTextSize(16 * getResources().getDisplayMetrics().density);
        mTextPaint.setColor(0xFF000000);
        setPadding(3, 3, 3, 3);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("onMeasure:", "start");
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        Log.e("measureWidth:", String.valueOf(measureSpec));
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) mTextPaint.measureText(mText) + getPaddingLeft() + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        Log.e("measureHeight:", String.valueOf(measureSpec));
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        mAscent = (int) mTextPaint.ascent();
        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text (beware: ascent is a negative number)
            result = (int) (-mAscent + mTextPaint.descent()) + getPaddingTop()
                    + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by
                // measureSpec
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    public void setTextSize(int size) {
        Log.e("setTextSize:", String.valueOf(size));
        mTextPaint.setTextSize(size);
        requestLayout();
        invalidate();
    }

    public void setTextColor(int color) {
        Log.e("setTextColor:", String.valueOf(color));
        mTextPaint.setColor(color);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e("onDraw:", "start");
        super.onDraw(canvas);
        canvas.drawText(mText, getPaddingLeft(), getPaddingTop() - mAscent, mTextPaint);
    }

    // @Override
    // protected void onMeasure(final int widthMeasureSpec,
    // final int heightMeasureSpec) {
    // int width = MeasureSpec.getSize(widthMeasureSpec);
    // // int height = (int) (width * heightScale / widthScale);
    // int height = MeasureSpec.getSize(heightMeasureSpec);
    // if (height == 0) {
    // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    // } else {
    // super.onMeasure(
    // MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
    // MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    // }
    // }
}



























