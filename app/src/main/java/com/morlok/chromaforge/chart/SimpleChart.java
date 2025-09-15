package com.morlok.chromaforge.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * ChromaForge - a lightweight custom chart library for Android Studio.
 *
 * Features:
 * - Draws line charts with axes, grid lines, and labels.
 * - Scales dynamically to fit data and view size.
 * - Customizable colors, grid density, and maximum Y value.
 * - Can be extended to support other chart types (bar, scatter, area).
 */
public class SimpleChart extends View {

    // ======== Paint objects (styles for different parts of the chart) ========
    private Paint axisPaint;   // Paint for X and Y axes
    private Paint gridPaint;   // Paint for background grid lines
    private Paint linePaint;   // Paint for connecting data points
    private Paint pointPaint;  // Paint for data points
    private Paint textPaint;   // Paint for axis labels and text

    // ======== Chart configuration ========
    private List<Float> data = new ArrayList<>(); // Input data points (0..maxValue)
    private float maxValue = 100f;                // Maximum Y value (for normalization)
    private int gridStep = 5;                     // Number of horizontal grid lines

    // ======== Colors (can be customized externally) ========
    private int axisColor = Color.BLACK;
    private int gridColor = Color.LTGRAY;
    private int lineColor = Color.BLUE;
    private int pointColor = Color.RED;
    private int textColor = Color.DKGRAY;

    // ======== Layout and spacing ========
    private float padding = 80f;

    // ======== Constructors ========
    public SimpleChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    // Initialize paint objects
    private void init() {
        axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        axisPaint.setColor(axisColor);
        axisPaint.setStrokeWidth(3f);

        gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gridPaint.setColor(gridColor);
        gridPaint.setStrokeWidth(1f);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(4f);

        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setColor(pointColor);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(28f);
    }

    // ======== Public setters (API for developers) ========

    /** Set data points for the chart (values will be scaled to maxValue). */
    public void setData(List<Float> newData) {
        this.data = newData;
        invalidate(); // Request redraw
    }

    /** Define the maximum Y value (data will be normalized to this). */
    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
        invalidate();
    }

    /** Set the number of horizontal grid divisions. */
    public void setGridStep(int step) {
        this.gridStep = step;
        invalidate();
    }

    /** Change line color. */
    public void setLineColor(int color) {
        this.lineColor = color;
        linePaint.setColor(color);
        invalidate();
    }

    /** Change point color. */
    public void setPointColor(int color) {
        this.pointColor = color;
        pointPaint.setColor(color);
        invalidate();
    }

    /** Change grid color. */
    public void setGridColor(int color) {
        this.gridColor = color;
        gridPaint.setColor(color);
        invalidate();
    }

    /** Change axis color. */
    public void setAxisColor(int color) {
        this.axisColor = color;
        axisPaint.setColor(color);
        invalidate();
    }

    // ======== Drawing logic ========
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();

        // Define drawing bounds
        float x0 = padding;
        float y0 = height - padding;
        float xMax = width - padding;
        float yMax = padding;

        // Draw X and Y axes
        canvas.drawLine(x0, y0, xMax, y0, axisPaint); // X-axis
        canvas.drawLine(x0, y0, x0, yMax, axisPaint); // Y-axis

        // Draw horizontal grid + Y labels
        float stepY = (y0 - yMax) / gridStep;
        for (int i = 1; i <= gridStep; i++) {
            float y = y0 - i * stepY;
            canvas.drawLine(x0, y, xMax, y, gridPaint);

            // Label text (scaled to maxValue)
            String label = String.valueOf(Math.round((maxValue / gridStep) * i));
            canvas.drawText(label, 10, y + 10, textPaint);
        }

        // No data → nothing to draw
        if (data == null || data.size() < 2) return;

        // Distance between points on X axis
        float stepX = (xMax - x0) / (data.size() - 1);

        // Draw line segments + data points
        for (int i = 0; i < data.size() - 1; i++) {
            float x1 = x0 + i * stepX;
            float y1 = y0 - (data.get(i) / maxValue * (y0 - yMax));

            float x2 = x0 + (i + 1) * stepX;
            float y2 = y0 - (data.get(i + 1) / maxValue * (y0 - yMax));

            // Draw line segment
            canvas.drawLine(x1, y1, x2, y2, linePaint);

            // Draw point
            canvas.drawCircle(x1, y1, 6f, pointPaint);
        }

        // Draw last point
        float xLast = x0 + (data.size() - 1) * stepX;
        float yLast = y0 - (data.get(data.size() - 1) / maxValue * (y0 - yMax));
        canvas.drawCircle(xLast, yLast, 6f, pointPaint);
    }
}
