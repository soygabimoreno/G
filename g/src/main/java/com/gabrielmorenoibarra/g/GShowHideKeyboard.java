package com.gabrielmorenoibarra.g;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Utility to show or hide the soft keyboard.
 * Created by Gabriel Moreno on 2018-05-28.
 */
public class GShowHideKeyboard {

    private Context context;
    private EditText et;

    public GShowHideKeyboard(Context context, EditText et) {
        this.context = context;
        this.et = et;
    }

    public void show() {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    public void hide() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        et.clearFocus();
    }
}