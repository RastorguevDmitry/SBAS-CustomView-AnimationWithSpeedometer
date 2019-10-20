package com.rdi.spidometr;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


public class SpeedometerView extends View {
    private static final String TAG = "SpeedometerView";
    private static final int DEFAULT_COLOR = Color.BLACK;
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;

    private static final int DEFAULT_MAX_VELOCITY = 200;

    private static final int DEFAULT_SIZE = 500;

    private static final float STROKE_WIDTH = 64f;
    private static final float STROKE_DIAL_WIDTH = 12f;
    private static final float STROKE_DIAL_SMALL_WIDTH = 4f;
    private static final float STROKE_DIAL_ARROW_WIDTH = 20f;

    private static final int MAX_ANGLE = 270;
    private static final int START_ANGLE = 135;

    private static final double RATIO_FOR_CONVERSION_DEGREES_TO_RADIANS = 0.0175;

    private static final float FRACTION_SPEED_LIMIT_LOW_VELOCITY = 0.4f;
    private static final float FRACTION_SPEED_LIMIT_MEDIUM_VELOCITY = 0.7f;
    private static final int NUMBER_OF_SCALE = 10;
    private static final double RATIO_FOR_MAIN_SCALE_INDENT = 0.7;

    private int curentVeloсity;
    private int maxVeloсity;

    private int colorLowVelocity;
    private int colorMediumVelocity;
    private int colorMaxVelocity;
    private int colorArrow;

    private int textSize;

    private int xStartDial;
    private int yStartDial;
    private int xStopDial;
    private int yStopDial;

    private TextView textView;
    Rect textBounds = new Rect();

    private Paint dialColorPaint;
    private Paint dialPaint;
    private RectF dialRect;

    public SpeedometerView(Context context) {
        super(context);
        init(context, null);
    }

    public SpeedometerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SpeedometerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SpeedometerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = resolveSize(DEFAULT_SIZE, widthMeasureSpec);
        int height = resolveSize(DEFAULT_SIZE, heightMeasureSpec);
        dialRect = new RectF(0, 0, height - 4 * STROKE_WIDTH, width - 2 * STROKE_WIDTH);
        setMeasuredDimension(width, height);
    }


//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        dialColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        dialRect = new RectF(0, 0, DEFAULT_SIZE, DEFAULT_SIZE);

        extractAttributes(context, attrs);
        dialColorPaint.setStrokeWidth(STROKE_WIDTH);
        dialColorPaint.setStyle(Paint.Style.STROKE);

        dialPaint.setStrokeWidth(STROKE_DIAL_WIDTH);
        dialPaint.setStyle(Paint.Style.STROKE);
        dialPaint.setColor(DEFAULT_TEXT_COLOR);

        textView = new TextView(context);
        textView.setTextSize(textSize);
    }

    private void extractAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        final Resources.Theme theme = context.getTheme();
        final TypedArray typedArray = theme.obtainStyledAttributes(attrs, R.styleable.SpeedometerView, R.attr.SpeedometerViewStyle, 0);
        try {
            maxVeloсity = typedArray.getInteger(R.styleable.SpeedometerView_maxVeloсity, DEFAULT_MAX_VELOCITY);
            //       curentVeloсity = typedArray.getInteger(R.styleable.SpeedometerView_curentVeloсity, DEFAULT_MAX_VELOCITY);
            colorLowVelocity = typedArray.getColor(R.styleable.SpeedometerView_colorLowVelocity, DEFAULT_COLOR);
            colorMediumVelocity = typedArray.getColor(R.styleable.SpeedometerView_colorMediumVelocity, DEFAULT_COLOR);
            colorMaxVelocity = typedArray.getColor(R.styleable.SpeedometerView_colorMaxVelocity, DEFAULT_COLOR);
            colorArrow = typedArray.getColor(R.styleable.SpeedometerView_colorArrow, DEFAULT_COLOR);
            textSize = typedArray.getDimensionPixelSize(R.styleable.SpeedometerView_textSize, getResources().getDimensionPixelSize(R.dimen.big_size));
        } finally {
            typedArray.recycle();
            Log.d(TAG, "maxVeloсity = " + maxVeloсity + ", colorLowVelocity = " + colorLowVelocity + ", colorMediumVelocity = " + colorMediumVelocity +
                    "colorMaxVelocity = " + colorMaxVelocity + ", colorArrow = " + colorArrow + ", textSize = " + textSize + ", curentVeloсity = " + curentVeloсity);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawColoredDial(canvas);
        drawScaleOfDial(canvas);
        drawArrow(canvas);
        drawTextForScale(canvas);
        drawTextForCurrentVelocity(canvas);
    }

    private void drawTextForCurrentVelocity(Canvas canvas) {
        float x;
        float y;

        final String curentVeloсityString = String.valueOf(curentVeloсity);
        textView.getPaint()
                .getTextBounds(
                        curentVeloсityString, 0, curentVeloсityString.length(), textBounds);
        x = dialRect.width() / 2f - textBounds.width() / 2f - textBounds.left;
        y = dialRect.height() * 3 / 4;
        canvas.drawText(curentVeloсityString, x, y, textView.getPaint());
    }

    private void drawTextForScale(Canvas canvas) {
        // максимальная скорость
        String maxVeloсityString = String.valueOf(maxVeloсity);
        textView.getPaint()
                .getTextBounds( //замер размера текста
                        maxVeloсityString, 0, maxVeloсityString.length(), textBounds);
        float x = dialRect.width() - textBounds.width() / 2f - textBounds.left;
        float y = dialRect.height() + textBounds.height() / 2f - textBounds.bottom;
        canvas.drawText(maxVeloсityString, x, y, textView.getPaint());

        String mesurmentVelosity = getContext().getString(R.string.unit_mesurment_velosity);
        textView.getPaint()
                .getTextBounds( //замер размера текста
                        mesurmentVelosity, 0, mesurmentVelosity.length(), textBounds);
        x = dialRect.width() / 2f - textBounds.width() / 2f - textBounds.left;
        y = dialRect.height() / 4 + textBounds.height() / 2;
        canvas.drawText(mesurmentVelosity, x, y, textView.getPaint());
    }

    private void drawArrow(Canvas canvas) {
        double angle;
        dialPaint.setColor(colorArrow);
        dialPaint.setStrokeWidth(STROKE_DIAL_ARROW_WIDTH);
        angle = RATIO_FOR_CONVERSION_DEGREES_TO_RADIANS * (START_ANGLE + MAX_ANGLE * curentVeloсity / maxVeloсity);
        xStartDial = (int) (dialRect.right / 2 * Math.cos(angle) + dialRect.right / 2);
        yStartDial = (int) (dialRect.bottom / 2 * Math.sin(angle) + dialRect.bottom / 2);
        xStopDial = (int) (dialRect.right / 2);
        yStopDial = (int) (dialRect.bottom / 2);
        canvas.drawLine(xStartDial, yStartDial, xStopDial, yStopDial, dialPaint);
        Log.d(TAG, "onDraw: " + angle + xStartDial + yStartDial + xStopDial + yStopDial);
    }

    private void drawScaleOfDial(Canvas canvas) {
        double angle;
        // основная шкала
        for (int step = START_ANGLE; step <= START_ANGLE + MAX_ANGLE; step += MAX_ANGLE / NUMBER_OF_SCALE) {
            angle = RATIO_FOR_CONVERSION_DEGREES_TO_RADIANS * step;
            xStartDial = (int) (((dialRect.right + STROKE_WIDTH) * Math.cos(angle) + dialRect.right) / 2);
            yStartDial = (int) (((dialRect.bottom + STROKE_WIDTH) * Math.sin(angle) + dialRect.bottom) / 2);
            xStopDial = (int) (((dialRect.right - STROKE_WIDTH * RATIO_FOR_MAIN_SCALE_INDENT) * Math.cos(angle) + dialRect.right) / 2);
            yStopDial = (int) (((dialRect.bottom - STROKE_WIDTH * RATIO_FOR_MAIN_SCALE_INDENT) * Math.sin(angle) + dialRect.bottom) / 2);
            canvas.drawLine(xStartDial, yStartDial, xStopDial, yStopDial, dialPaint);

        }
        //тонкие штрихи
        dialPaint.setStrokeWidth(STROKE_DIAL_SMALL_WIDTH);
        for (int step = START_ANGLE + MAX_ANGLE / (2 * NUMBER_OF_SCALE); step <= START_ANGLE + MAX_ANGLE; step += MAX_ANGLE / NUMBER_OF_SCALE) {
            angle = RATIO_FOR_CONVERSION_DEGREES_TO_RADIANS * step;
            xStartDial = (int) (((dialRect.right + STROKE_WIDTH) * Math.cos(angle) + dialRect.right) / 2);
            yStartDial = (int) (((dialRect.bottom + STROKE_WIDTH) * Math.sin(angle) + dialRect.bottom) / 2);
            xStopDial = (int) ((dialRect.right * Math.cos(angle) + dialRect.right) / 2);
            yStopDial = (int) ((dialRect.bottom * Math.sin(angle) + dialRect.bottom) / 2);
            canvas.drawLine(xStartDial, yStartDial, xStopDial, yStopDial, dialPaint);
        }
    }

    private void drawColoredDial(Canvas canvas) {
        canvas.translate(STROKE_WIDTH, STROKE_WIDTH); //передвинуть
        dialColorPaint.setColor(colorLowVelocity);
        canvas.drawArc(dialRect, START_ANGLE, MAX_ANGLE * FRACTION_SPEED_LIMIT_LOW_VELOCITY, false, dialColorPaint);
        dialColorPaint.setColor(colorMediumVelocity);
        canvas.drawArc(dialRect, START_ANGLE + MAX_ANGLE * FRACTION_SPEED_LIMIT_LOW_VELOCITY,
                MAX_ANGLE * (FRACTION_SPEED_LIMIT_MEDIUM_VELOCITY - FRACTION_SPEED_LIMIT_LOW_VELOCITY), false, dialColorPaint);
        dialColorPaint.setColor(colorMaxVelocity);
        canvas.drawArc(dialRect, START_ANGLE + MAX_ANGLE * FRACTION_SPEED_LIMIT_MEDIUM_VELOCITY,
                (MAX_ANGLE - MAX_ANGLE * FRACTION_SPEED_LIMIT_MEDIUM_VELOCITY) + STROKE_DIAL_WIDTH / 4, false, dialColorPaint);
    }


    public int getCurentVeloсity() {
        return curentVeloсity;
    }

    public void setCurentVeloсity(int curentVeloсity) {
        this.curentVeloсity = curentVeloсity;
        invalidate();
    }

    public int getMaxVeloсity() {
        return maxVeloсity;
    }

    public void setMaxVeloсity(int maxVeloсity) {
        this.maxVeloсity = maxVeloсity;
    }

    public int getColorLowVelocity() {
        return colorLowVelocity;
    }

    public void setColorLowVelocity(int colorLowVelocity) {
        this.colorLowVelocity = colorLowVelocity;
    }

    public int getColorMediumVelocity() {
        return colorMediumVelocity;
    }

    public void setColorMediumVelocity(int colorMediumVelocity) {
        this.colorMediumVelocity = colorMediumVelocity;
    }

    public int getColorMaxVelocity() {
        return colorMaxVelocity;
    }

    public void setColorMaxVelocity(int colorMaxVelocity) {
        this.colorMaxVelocity = colorMaxVelocity;
    }

    public int getColorArrow() {
        return colorArrow;
    }

    public void setColorArrow(int colorArrow) {
        this.colorArrow = colorArrow;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }


}
