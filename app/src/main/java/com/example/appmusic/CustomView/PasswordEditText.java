package com.example.appmusic.CustomView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
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
public class PasswordEditText extends EditText {

    Drawable eyeVisibility, eyeInvisibility;
    boolean visible = false; //Store state of image
    boolean useStrike = false;
    Drawable drawable;
    int ALPHA = (int) (255 * .55f);

    public PasswordEditText(Context context) {
        super(context);
        init(null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        if (attrs != null){
            TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PasswordEditText, 0, 0);
            this.useStrike = array.getBoolean(R.styleable.PasswordEditText_useStrike, false);
        }
        eyeVisibility = ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_black).mutate();
        eyeInvisibility = ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_off_black).mutate();

        setUp();
    }

    private void setUp(){
        setInputType(InputType.TYPE_CLASS_TEXT | (visible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD));
        Drawable[] drawables = getCompoundDrawables();
        drawable = useStrike &&  !visible ? eyeInvisibility : eyeVisibility;
        drawable.setAlpha(ALPHA);
        setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawable, drawables[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && (event.getX() >= (getRight() - drawable.getBounds().width()))){
            visible = !visible;
            setUp();
            invalidate();
        }
        return super.onTouchEvent(event);
    }
}
