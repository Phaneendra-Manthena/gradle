package com.bhargo.user.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.bhargo.user.R;

public class RoundishImageView extends androidx.appcompat.widget.AppCompatImageView {


    public static final int CORNER_NONE = 0;
    public static final int CORNER_TOP_LEFT = 16;
    public static final int CORNER_TOP_RIGHT = 16;
    public static final int CORNER_BOTTOM_RIGHT = 0;
    public static final int CORNER_BOTTOM_LEFT = 0;
    public static final int CORNER_ALL = 15;

    private static final int[] CORNERS = {CORNER_TOP_LEFT,
            CORNER_TOP_RIGHT,
            CORNER_BOTTOM_RIGHT,
            CORNER_BOTTOM_LEFT};

    private final Path path = new Path();
    private int cornerRadius;
    private int roundedCorners;

    public RoundishImageView(Context context) {
        this(context, null);
    }

    public RoundishImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundishImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundishImageView);
        cornerRadius = a.getDimensionPixelSize(R.styleable.RoundishImageView_cornerRadius, 0);
        roundedCorners = a.getInt(R.styleable.RoundishImageView_roundedCorners, CORNER_NONE);
        a.recycle();
    }

    public void setCornerRadius(int radius) {
        if (cornerRadius != radius) {
            cornerRadius = radius;
            setPath();
            invalidate();
        }
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setRoundedCorners(int corners) {
        if (roundedCorners != corners) {
            roundedCorners = corners;
            setPath();
            invalidate();
        }
    }

    public boolean isCornerRounded(int corner) {
        return (roundedCorners & corner) == corner;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float radius = 24f;
        Path path = getPath(radius, true, true, false, false);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
    private Path getPath(float radius, boolean topLeft, boolean topRight,
                         boolean bottomRight, boolean bottomLeft) {

        final Path path = new Path();
        final float[] radii = new float[8];

        if (topLeft) {
            radii[0] = radius;
            radii[1] = radius;
        }

        if (topRight) {
            radii[2] = radius;
            radii[3] = radius;
        }

        if (bottomRight) {
            radii[4] = radius;
            radii[5] = radius;
        }

        if (bottomLeft) {
            radii[6] = radius;
            radii[7] = radius;
        }

        path.addRoundRect(new RectF(0, 0, getWidth(), getHeight()),
                radii, Path.Direction.CW);

        return path;
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setPath();
    }

    private void setPath() {
        path.rewind();

        if (cornerRadius >= 1f && roundedCorners != CORNER_NONE) {
            final float[] radii = new float[8];

            for (int i = 0; i < 4; i++) {
                if (isCornerRounded(CORNERS[i])) {
                    radii[2 * i] = cornerRadius;
                    radii[2 * i + 1] = cornerRadius;
                }
            }


            path.addRoundRect(new RectF(0, 0, getWidth(), getHeight()),
                    radii, Path.Direction.CW);
        }
    }

}

