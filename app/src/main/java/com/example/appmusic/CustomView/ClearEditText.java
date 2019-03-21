package com.example.appmusic.CustomView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.example.appmusic.R;

//Custom Edit Text

@SuppressLint("AppCompatCustomView")
public class ClearEditText extends EditText {

    Drawable crossx, nonecrossx, drawable;
    Boolean visible = false; //Store state of image

    public ClearEditText(Context context) {
        super(context);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        crossx = ContextCompat.getDrawable(getContext(), R.drawable.ic_close_black).mutate();
        nonecrossx = ContextCompat.getDrawable(getContext(), android.R.drawable.screen_background_light_transparent).mutate();

        setUp();
    }

    private void setUp(){
        setInputType(InputType.TYPE_CLASS_TEXT);
        Drawable[] drawables = getCompoundDrawables();
        drawable = visible ? crossx : nonecrossx;
        setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawable, drawables[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && (event.getX() >= (getRight() - drawable.getBounds().width()))){
            setText("");
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

        if (lengthAfter == 0 && start == 0){
            visible = false;
            setUp();
        }else{
            visible = true;
            setUp();
        }
    }
}
