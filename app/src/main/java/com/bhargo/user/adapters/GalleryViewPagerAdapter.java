package com.bhargo.user.adapters;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bhargo.user.R;
import com.bumptech.glide.Glide;



import java.util.List;
import java.util.Objects;


public class GalleryViewPagerAdapter extends PagerAdapter {
//public class GalleryViewPagerAdapter extends PagerAdapter implements View.OnTouchListener {

    private static final String TAG = "GalleryViewPagerAdapter";
    // Context object
    Context context;

    // Array of images
    List<String> images;

    // Layout Inflater

   /* private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
*/

    private float mScaleFactor = 1.0f;
    // These matrices will be used to move and zoom image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    String savedItemClicked;


    // Viewpager Constructor
    public GalleryViewPagerAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;


    }

    @Override
    public int getCount() {
        // return the number of images
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // inflating the item.xml
        LayoutInflater mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.item_gallery_view, container, false);

        // referencing the image view from the item.xml file
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageViewMain);
        Log.d(TAG, "instantiateItem_SC: "+position+" - "+images.get(position));
        // setting the image in the imageView
        Glide.with(context).load(images.get(position).trim()).into(imageView);
        // Adding the View
        container.addView(itemView);
//        imageView.setOnTouchListener(this);
//        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener(imageView));

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }

    /*@Override
    public boolean onTouch(View v, MotionEvent event)
    {
        ImageView view = (ImageView) v;
        view.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;

        dumpEvent(event);
        // Handle touch events here...

        switch (event.getAction() & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:   // first finger down only
                matrix.set(view.getImageMatrix());
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                Log.d(TAG, "mode=DRAG"); // write to LogCat
                mode = DRAG;
                break;

            case MotionEvent.ACTION_UP: // first finger lifted

            case MotionEvent.ACTION_POINTER_UP: // second finger lifted

                mode = NONE;
                Log.d(TAG, "mode=NONE");
                break;

            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down

                oldDist = spacing(event);
                Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist > 5f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                    Log.d(TAG, "mode=ZOOM");
                }
                break;

            case MotionEvent.ACTION_MOVE:

                if (mode == DRAG)
                {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y); // create the transformation in the matrix  of points
                }
                else if (mode == ZOOM)
                {
                    // pinch zooming
                    float newDist = spacing(event);
                    Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 5f)
                    {
                        matrix.set(savedMatrix);
                        scale = newDist / oldDist; // setting the scaling of the
                        // matrix...if scale > 1 means
                        // zoom in...if scale < 1 means
                        // zoom out
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }

        view.setImageMatrix(matrix); // display the transformation on screen

        return true; // indicate event was handled
    }

    *//*
     * --------------------------------------------------------------------------
     * Method: spacing Parameters: MotionEvent Returns: float Description:
     * checks the spacing between the two fingers on touch
     * ----------------------------------------------------
     *//*

    private float spacing(MotionEvent event)
    {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    *//*
     * --------------------------------------------------------------------------
     * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
     * Description: calculates the midpoint between the two fingers
     * ------------------------------------------------------------
     *//*

    private void midPoint(PointF point, MotionEvent event)
    {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }


    private void dumpEvent(MotionEvent event)
    {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE","POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);

        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP)
        {
            sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }

        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++)
        {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }

        sb.append("]");
        Log.d("Touch Events ---------", sb.toString());
    }*/
}

