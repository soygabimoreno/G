package com.gabrielmorenoibarra.g;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;

/**
 * Utility to enable or disable a View.
 * Created by Gabriel Moreno on 2017-04-11.
 */
public class GEnableDisablePerform {

    private View v;
    private Drawable drawableActivated;
    private Drawable drawableDeactivated;
    private Drawable drawableDisabled;
    private int textColorDeactivated;
    private int textColorActivated;
    private int textColorDisabled;

    public GEnableDisablePerform(View v, Drawable drawableActivated, Drawable drawableDeactivated, Drawable drawableDisabled, int textColorActivated, int textColorDeactivated, int textColorDisabled) {
        this.v = v;
        this.drawableActivated = drawableActivated;
        this.drawableDeactivated = drawableDeactivated;
        this.drawableDisabled = drawableDisabled;
        this.textColorActivated = textColorActivated;
        this.textColorDeactivated = textColorDeactivated;
        this.textColorDisabled = textColorDisabled;
        disable();
    }

    public void activate() {
        v.setEnabled(true);
        v.setBackground(drawableActivated);
        if (v instanceof Button) {
            ((Button) v).setTextColor(textColorActivated);
        }
    }

    public void deactivate() {
        v.setEnabled(true);
        v.setBackground(drawableDeactivated);
        if (v instanceof Button) {
            ((Button) v).setTextColor(textColorDeactivated);
        }
    }

    public void disable() {
        v.setEnabled(false);
        v.setBackground(drawableDisabled);
        if (v instanceof Button) {
            ((Button) v).setTextColor(textColorDisabled);
        }
    }
}