package com.printfthoughts.queuedprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;


/**
 * Loader with animated balls with user info or progress value as user message
 */
public class QueuedProgressBar extends View {

    private Paint mPaint;

    private float mCx;

    private int mAccentColor;

    private int mTotalBalls;

    private int mRunningBallIndex;

    private float mBallRadius;

    private float mRecentAddedBallPos;

    private boolean mCanRelease;

    private boolean isNewCycle;

    private float mIntervalValue;

    private int mBallColor;

    public QueuedProgressBar(Context context) {
        super(context);
        init(context, null, 0);
    }

    public QueuedProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public QueuedProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.QueuedProgressBar, defStyle, 0);

        mBallRadius = a.getFloat(R.styleable.QueuedProgressBar_ballRadius, 5);
        mIntervalValue = a.getFloat(R.styleable.QueuedProgressBar_interval, 3);
        mTotalBalls = a.getInteger(R.styleable.QueuedProgressBar_ballCount, 5);

        TypedValue typedValue = new TypedValue();
        TypedArray array = context.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
        mAccentColor = array.getColor(0, Color.BLACK);
        array.recycle();

        if (a.hasValue(R.styleable.QueuedProgressBar_ballColor)) {
            mBallColor = a.getColor(R.styleable.QueuedProgressBar_ballColor, mAccentColor);
        } else {
            mBallColor = mAccentColor;
        }

        a.recycle();

        // Set up a default TextPaint object
        mPaint = new TextPaint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mBallColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        int ballCount = mRunningBallIndex;
        float gap = 3 * mBallRadius;
        float cy = contentHeight / 2;
        float firstPosition = contentWidth / 2 + (mTotalBalls * gap) / 2;
        float lastPosition = firstPosition;
        int mPlacedBalls;

        //draw running ball
        canvas.drawCircle(mCx, cy, mBallRadius, mPaint);

        //place the idle ball based on the count
        if (mTotalBalls <= 1) {

            if (mCx >= contentWidth) {
                mCx = 0;
            }else {
                mCx += mIntervalValue;
            }
        }else if (mCanRelease) {

            if (contentWidth <= mCx && 0 >= mRunningBallIndex) {
                mCanRelease = false;
                mCx = 0;
            }else if (contentWidth <= mCx) {
                isNewCycle = true;
                mRunningBallIndex--;
            }else if (contentWidth > mCx) {
                mCx += mIntervalValue;
            }else {
                mCanRelease = false;
            }
            mPlacedBalls = 0;
            for (int i = 0; i < ballCount; i++) {

                float margin = mRecentAddedBallPos + i * gap;
                canvas.drawCircle(margin , cy, mBallRadius, mPaint);
                lastPosition = margin;
                mPlacedBalls++;
            }
            if (isNewCycle){
                mCx = lastPosition;
                isNewCycle = false;
            }
        } else {

            mPlacedBalls = 0;

            for (int i = 0; i < ballCount && i < mTotalBalls -1; i++) {

                canvas.drawCircle(lastPosition, cy, mBallRadius, mPaint);
                lastPosition -= gap;
                mPlacedBalls++;
                if (mTotalBalls -1 == mPlacedBalls) {
                    mRecentAddedBallPos = lastPosition;
                }
            }
            if (mTotalBalls -1 == mPlacedBalls && mCx >= mRecentAddedBallPos) {
                mCanRelease = true;
                mCx = firstPosition;
            }else if (mCx >= lastPosition) {
                mCx = 1;
                mRunningBallIndex++;
            } else if (lastPosition >= mCx || mCx < mRecentAddedBallPos) {
                mCx += mIntervalValue;
            }else {
                mCanRelease = false;
            }
        }
        postInvalidateDelayed(1);
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param ballColor The ball color attribute value to change the ball's color.
     */
    public void setBallColor(int ballColor) {

        mBallColor = ballColor == 0 ? mAccentColor : ballColor;
        mPaint.setColor(mBallColor);
        invalidate();
    }

    /**
     * Gets the ball color attribute value.
     *
     * @return mBallColor, returns the ball's color.
     */
    public int getBallColor() {
        return mBallColor;
    }

    /**
     * Gets the total no of balls on the loader
     * @return mTotalBalls
     */
    public int getTotalBalls() {
        return mTotalBalls;
    }

    /**
     * Sets the total no of ball on the loader animation
     * @param totalBalls
     */
    public void setTotalBalls(int totalBalls) {
        this.mTotalBalls = totalBalls;
        invalidate();
    }

    /**
     * Gets the raidus of ball
     * @return mBallRadius
     */
    public float getBallRadius() {
        return mBallRadius;
    }

    /**
     * sets the radius value of the ball on the loader
     * @param radius
     */
    public void setBallRadius(float radius) {
        this.mBallRadius = radius;
        invalidate();
    }

    /**
     * Gets the interval value of running animation
     * @return mIntervalValue
     */
    public float getIntervalValue() {
        return mIntervalValue;
    }

    /**
     * sets interval of running animate on the loader
     * @param interval
     */
    public void setIntervalValue(float interval) {
        this.mIntervalValue = interval;
        invalidate();
    }
}