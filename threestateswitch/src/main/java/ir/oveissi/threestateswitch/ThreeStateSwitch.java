package ir.oveissi.threestateswitch;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by abbas on 8/6/16.
 */

@SuppressWarnings("unused")
public class ThreeStateSwitch extends View {
    private int state = 0;
    private boolean isDrag;
    private float startX;
    private boolean isAnimate;
    private boolean isPressed;


    int text_normal_color;
    int text_selected_color;
    int text_normal_size;
    int text_selected_size;

    boolean twoStateAfterInit = false;

    int colorStateUnSelected = Color.parseColor("#bfbfbf");
    int colorStateSelected = Color.parseColor("#5ab72e");

    public int backgroundColor;


    Paint ovalPaint, textSelectedPaint, textNormalPaint;
    Bitmap thumbIcon;
    RectF ovalRectF;
    Rect orginalBitmapRect, drawnBitmapRect;
    Rect textBounds;

    private float viewWidth;
    private float viewHeight;


    private int paddingRight;
    private int paddingLeft;
    private int paddingBottom;
    private int paddingTop;


    public int xCircle = 0;
    public int yCircle = 0;
    public int diameterSize = 0;


    String lessText = "";
    String moreText = "";


    public ThreeStateSwitch(Context context) {
        super(context);
        init(context, null);
    }

    public ThreeStateSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ThreeStateSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ThreeStateSwitch(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }


    public void init(Context context, AttributeSet attrs) {

//        int[] attrsArray = new int[] {
//                R.attr.background_selected_color, // 0
//                R.attr.background_normal_color, // 1
//                R.attr.text_normal_color, // 2
//                R.attr.text_selected_color, // 3
//                R.attr.text_normal_size, // 4
//                R.attr.text_selected_size, // 5
//                R.styleable.ThreeStateSwitch_text_left, // 6
//                R.styleable.ThreeStateSwitch_text_right // 7
//        };
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ThreeStateSwitch);

        colorStateSelected = ta.getColor(R.styleable.ThreeStateSwitch_background_selected_color, getResources().getColor(R.color.background_selected_color));
        colorStateUnSelected = ta.getColor(R.styleable.ThreeStateSwitch_background_normal_color, getResources().getColor(R.color.background_normal_color));

        backgroundColor = colorStateUnSelected;


        text_normal_color = ta.getColor(R.styleable.ThreeStateSwitch_text_normal_color, getResources().getColor(R.color.text_normal_color));
        text_selected_color = ta.getColor(R.styleable.ThreeStateSwitch_text_selected_color, getResources().getColor(R.color.text_selected_color));

        text_normal_size = ta.getDimensionPixelSize(R.styleable.ThreeStateSwitch_text_normal_size, (int) getResources().getDimension(R.dimen.text_normal_size));
        text_selected_size = ta.getDimensionPixelSize(R.styleable.ThreeStateSwitch_text_selected_size, (int) getResources().getDimension(R.dimen.text_selected_size));

        twoStateAfterInit = ta.getBoolean(R.styleable.ThreeStateSwitch_two_state_after_init, false);


        lessText = ta.getString(R.styleable.ThreeStateSwitch_text_left);
        if (lessText == null) {
            lessText = getResources().getString(R.string.text_left);
        }
        moreText = ta.getString(R.styleable.ThreeStateSwitch_text_right);
        if (moreText == null) {
            moreText = getResources().getString(R.string.text_right);
        }


        ta.recycle();


        ovalPaint = new Paint();
        ovalPaint.setColor(backgroundColor);
        ovalPaint.setAntiAlias(true);

        thumbIcon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.switch_circle);

        orginalBitmapRect = new Rect();
        drawnBitmapRect = new Rect();
        ovalRectF = new RectF();
        textBounds = new Rect();

        textSelectedPaint = new Paint();
        textSelectedPaint.setColor(text_selected_color);
        textSelectedPaint.setTextSize(text_selected_size);
        textSelectedPaint.setAntiAlias(true);


        textNormalPaint = new Paint();
        textNormalPaint.setColor(text_normal_color);
        textNormalPaint.setTextSize(text_normal_size);
        textNormalPaint.setAntiAlias(true);
    }


    public interface OnStateChangeListener {
        void OnStateChangeListener(int currentState);
    }

    public OnStateChangeListener onStateChangeListener;

    public void setOnChangeListener(OnStateChangeListener onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }


    public int getBackColor() {
        return this.backgroundColor;
    }

    public int getXCircle() {
        return xCircle;
    }

    public void setBackColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        invalidate();
    }

    public void setXCircle(int xCircle) {
        this.xCircle = xCircle;
        invalidate();
    }


    public int getState() {
        return this.state;
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int widthSize = MeasureSpec.getSize(widthSpec);
        int heightSize = MeasureSpec.getSize(heightSpec);

        paddingRight = getPaddingRight();
        paddingLeft = getPaddingLeft();
        paddingBottom = getPaddingBottom();
        paddingTop = getPaddingTop();


        textLeftNormalWidth = textNormalPaint.measureText(lessText);
        textRightNormalWidth = textNormalPaint.measureText(moreText);

        textLeftSelectedWidth = textSelectedPaint.measureText(lessText);
        textRightSelectedWidth = textSelectedPaint.measureText(moreText);

        maxNeededTextWidth = Math.max(textLeftNormalWidth, textRightNormalWidth);
        maxNeededTextWidth = Math.max(maxNeededTextWidth, textLeftSelectedWidth);
        maxNeededTextWidth = Math.max(maxNeededTextWidth, textRightSelectedWidth);
        maxNeededTextWidth += dpToPx(5);


        float minWidth = (2 * maxNeededTextWidth) + dpToPx(130) + paddingLeft + paddingRight;
        float minHeight = dpToPx(35) + paddingBottom + paddingTop;


        int pWidth = resolveSize((int) minWidth, widthSpec);
        int pHeight = resolveSize((int) minHeight, heightSpec);


        float LeftRect = maxNeededTextWidth + paddingLeft;
        float topRect = paddingTop;
        float rightRect = pWidth - maxNeededTextWidth - paddingRight;
        float bottomRect = pHeight - paddingBottom;

        ovalRectF.set(LeftRect, topRect, rightRect, bottomRect);


        diameterSize = Math.min(pWidth - paddingRight - paddingLeft - (int) (2 * maxNeededTextWidth)
                , pHeight - paddingTop - paddingBottom);

        switch (state) {
            case -1:
                if (!twoStateAfterInit)
                    xCircle = (int) (ovalRectF.left + (diameterSize / 2.0));
                else
                    xCircle = (int) (ovalRectF.centerX());
                break;
            case 0:
                xCircle = (int) (ovalRectF.centerX());
                break;
            case 1:
                xCircle = (int) (ovalRectF.right - (diameterSize / 2.0));
                break;
        }
        yCircle = (int) ovalRectF.centerY();


//        changeViewSize(widthSize,heightSize);
        viewWidth = pWidth;
        viewHeight = pHeight;
        setMeasuredDimension(pWidth, pHeight);
    }


    float textLeftSelectedWidth, textRightSelectedWidth, textLeftNormalWidth, textRightNormalWidth, maxNeededTextWidth;


    int delta = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                this.getParent().requestDisallowInterceptTouchEvent(true);
                startX = event.getX();
                if (state == whichStateClick(event.getX(), event.getY())) {
                    delta = -1 * ((int) event.getX() - xCircle);
                    isPressed = true;
                }
            }
            case MotionEvent.ACTION_MOVE:
                if (!isDrag && Math.abs(startX - event.getX()) > 15) {
                    isDrag = true;
                }
                if (isDrag) {
                    int endBoundry = (int) Math.max((diameterSize / 2f) - delta, event.getX());
                    int tempX = (int) Math.min(endBoundry, viewWidth - (diameterSize / 2f));
                    xCircle = tempX + delta;
                    xCircle = setBoundForXCircle(xCircle);
                }
                break;
            case MotionEvent.ACTION_UP:
                int tempState = whichStateClick(event.getX(), event.getY());
                isDrag = false;
                isPressed = false;
                changeState(tempState);
                break;
        }
        float xDrag = event.getX();
        float yDrag = event.getY();
        invalidate();
        return true;
    }

    public void setNormalTextTypeface(Typeface typeface) {
        textNormalPaint.setTypeface(typeface);
    }

    public void setSelectedTextTypeface(Typeface typeface) {
        textSelectedPaint.setTypeface(typeface);
    }


    private int whichStateClick(float x, float y) {
        if (twoStateAfterInit) {
            int half = (int) ((viewWidth - (2 * maxNeededTextWidth) - paddingLeft - paddingRight) / 2.0);
            if (x < half + maxNeededTextWidth + paddingLeft)
                return -1;
            else
                return 1;
        }
        int yekSevom = (int) ((viewWidth - (2 * maxNeededTextWidth) - paddingLeft - paddingRight) / 3.0);
        if (x < yekSevom + maxNeededTextWidth + paddingLeft)
            return -1;
        else if (x > (2 * yekSevom) + maxNeededTextWidth + paddingLeft)
            return 1;
        return 0;
    }


    private int setBoundForXCircle(float xCircle) {
        //Log.d("before",String.valueOf(xCircle));
        if (xCircle < ovalRectF.left + (diameterSize / 2.0))
            xCircle = (float) (ovalRectF.left + (diameterSize / 2.0));
        else if (xCircle > (float) (ovalRectF.right - (diameterSize / 2.0)))
            xCircle = (float) (ovalRectF.right - (diameterSize / 2.0));

        //Log.d("after",String.valueOf(xCircle));
        return (int) xCircle;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //ovalRectF.set(0,0,viewWidth,viewHeight);
        ovalPaint.setColor(backgroundColor);
        canvas.drawRoundRect(ovalRectF, diameterSize / 2f, diameterSize / 2f, ovalPaint);


        if (!isAnimate && !isDrag) {
            if (state == -1) {
                xCircle = (int) (diameterSize / 2.0) + (int) ovalRectF.left;
            } else if (state == 0) {
                xCircle = (int) (ovalRectF.centerX());
            } else if (state == 1) {
                xCircle = (int) (ovalRectF.right - (diameterSize / 2.0));
            }
            yCircle = (int) (ovalRectF.centerY());
        }


        if (isPressed)
            diameterSize += dpToPx(4);


        yCircle = (int) (ovalRectF.centerY());


        xCircle = setBoundForXCircle(xCircle);
        orginalBitmapRect.set(0, 0, thumbIcon.getWidth(), thumbIcon.getHeight());
        drawnBitmapRect.set(xCircle - (int) (diameterSize / 2.0), yCircle - (int) (diameterSize / 2.0)
                , xCircle + (int) (diameterSize / 2.0), yCircle + (int) (diameterSize / 2.0));

        canvas.drawBitmap(thumbIcon, orginalBitmapRect, drawnBitmapRect, null);
        if (isPressed)
            diameterSize -= dpToPx(4);

        if (state == -1)
            drawCenter(canvas, (int) (maxNeededTextWidth / 2.0) + paddingLeft, (int) (ovalRectF.centerY()), textSelectedPaint, lessText);
        else
            drawCenter(canvas, (int) (maxNeededTextWidth / 2.0) + paddingLeft, (int) (ovalRectF.centerY()), textNormalPaint, lessText);

        if (state == 1)
            drawCenter(canvas, (int) (viewWidth - (maxNeededTextWidth / 2.0) - paddingRight), (int) (ovalRectF.centerY()), textSelectedPaint, moreText);
        else
            drawCenter(canvas, (int) (viewWidth - (maxNeededTextWidth / 2.0) - paddingRight), (int) (ovalRectF.centerY()), textNormalPaint, moreText);

    }


    private final Rect r = new Rect();

    private void drawCenter(Canvas canvas, int drawX, int drawY, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(text, 0, text.length(), r);
        float x = drawX - r.width() / 2f - r.left;
        float y = drawY + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }


    public void changeState(int mState) {
        this.changeState(mState, true);
    }

    public void changeState(int mState, boolean animate) {
        if (!animate) {
            switch (mState) {
                case -1:
                    if (!twoStateAfterInit)
                        setBackColor(colorStateSelected);
                    else
                        setBackColor(colorStateUnSelected);

                    break;
                case 0:
                    setBackColor(colorStateUnSelected);
                    break;
                case 1:
                    setBackColor(colorStateSelected);
                    break;
            }
            this.state = mState;
            return;
        }
        if (onStateChangeListener != null)
            onStateChangeListener.OnStateChangeListener(mState);
        AnimatorSet animSet = new AnimatorSet();
        ArrayList<Animator> viewAnimList = new ArrayList<>();
        ObjectAnimator anim, anim1;
        switch (mState) {
            case -1:
                if (twoStateAfterInit) {
                    anim = ObjectAnimator.ofInt(this, "BackColor", colorStateUnSelected)
                            .setDuration(400);
                } else {
                    anim = ObjectAnimator.ofInt(this, "BackColor", colorStateSelected)
                            .setDuration(400);
                }
                anim.setEvaluator(new ArgbEvaluator());
                viewAnimList.add(anim);
                anim1 = ObjectAnimator.ofInt(this, "xCircle", setBoundForXCircle((float) (diameterSize / 2.0)))
                        .setDuration(400);
                viewAnimList.add(anim1);
                break;
            case 0:
                anim = ObjectAnimator.ofInt(this, "BackColor", colorStateUnSelected)
                        .setDuration(400);
                anim.setEvaluator(new ArgbEvaluator());
                viewAnimList.add(anim);

                anim1 = ObjectAnimator.ofInt(this, "xCircle", setBoundForXCircle(ovalRectF.centerX()))
                        .setDuration(400);
                viewAnimList.add(anim1);
                break;
            case 1:
                anim = ObjectAnimator.ofInt(this, "BackColor", colorStateSelected)
                        .setDuration(400);
                anim.setEvaluator(new ArgbEvaluator());
                viewAnimList.add(anim);

                anim1 = ObjectAnimator.ofInt(this, "xCircle", setBoundForXCircle((float) (viewWidth - (diameterSize / 2.0))))
                        .setDuration(400);
                viewAnimList.add(anim1);
                break;
        }
        animSet.playTogether(viewAnimList);
        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimate = true;

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimate = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimate = false;

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                isAnimate = false;

            }
        });

        animSet.start();
        this.state = mState;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, this.state);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        changeState(savedState.state, false);
    }


    /**
     * Convenience class to save / restore the lock combination picker state. Looks clumsy
     * but once created is easy to maintain and use.
     */
    protected static class SavedState extends BaseSavedState {

        public static final Parcelable.Creator<SavedState> CREATOR = new Creator<SavedState>() {

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }

        };
        public final Integer state;

        private SavedState(Parcelable superState, Integer state) {
            super(superState);
            this.state = state;
        }

        private SavedState(Parcel in) {
            super(in);
            state = in.readInt();
        }


        @Override
        public void writeToParcel(Parcel destination, int flags) {
            super.writeToParcel(destination, flags);
            destination.writeInt(state);
        }

    }
}
