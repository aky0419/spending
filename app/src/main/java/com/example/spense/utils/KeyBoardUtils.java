package com.example.spense.utils;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.example.spense.R;

public class KeyBoardUtils {

    private KeyboardView mKeyboardView;
    private EditText mEditText;
    Keyboard keyboard;

    public interface OnEnsureListener{
        public void onEnsure();
    }

    OnEnsureListener mOnEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        mOnEnsureListener = onEnsureListener;
    }

    public KeyBoardUtils(KeyboardView keyboardView, EditText editText) {
        mKeyboardView = keyboardView;
        mEditText = editText;
        mEditText.setInputType(InputType.TYPE_NULL); //cancel system keyboard
        keyboard = new Keyboard(mEditText.getContext(), R.xml.key);

        this.mKeyboardView.setKeyboard(keyboard); //设置要显示的键盘的样式
        this.mKeyboardView.setEnabled(true); //
        this.mKeyboardView.setPreviewEnabled(false);
        this.mKeyboardView.setOnKeyboardActionListener(listener);


    }

    KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable text = mEditText.getText();
            int start = mEditText.getSelectionStart();
            switch(primaryCode){
                case Keyboard.KEYCODE_DELETE:
                    if(text != null && text.length() > 0){
                        if (start>0) {
                            text.delete(start-1, start);
                        }
                    }
                    break;

                case Keyboard.KEYCODE_CANCEL:
                    text.clear();
                    break;

                case Keyboard.KEYCODE_DONE:
                    mOnEnsureListener.onEnsure(); // 通过接口回调，当点解确定，可以调用这个方法
                    break;

                default: //其他数字直接插入
                    text.insert(start, Character.toString((char) primaryCode));
                    break;
            }

        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };

    public void showKeyboard(){
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.INVISIBLE || visibility == View.GONE) {
            mKeyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard(){
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.VISIBLE || visibility == View.INVISIBLE) {
            mKeyboardView.setVisibility(View.GONE);
        }
    }


}
