package com.gabrielmorenoibarra.g;

import android.graphics.drawable.Drawable;
import android.widget.Button;

/**
 * Utility to wrap enable/disable colors and more of main buttons inside a ViewPager.
 * Created by Gabriel Moreno on 2017-10-11.
 */
public class GButtonViewPagerPerform {

    private Drawable bgEnabled;
    private Drawable bgDisabled;
    private int textColorEnabled;
    private int textColorDisabled;
    private Button[] buttons;

    private boolean[] selected;

    public GButtonViewPagerPerform(Drawable bgEnabled, Drawable bgDisabled, int textColorEnabled, int textColorDisabled, Button... buttons) {
        this.bgEnabled = bgEnabled;
        this.bgDisabled = bgDisabled;
        this.textColorEnabled = textColorEnabled;
        this.textColorDisabled = textColorDisabled;
        this.buttons = buttons;
        selected = new boolean[buttons.length];
    }

    public void switchPerform(int pos) {
        for (Button button : buttons) {
            button.setEnabled(true);
            button.setBackground(bgDisabled);
            button.setTextColor(textColorDisabled);
        }
        buttons[pos].setEnabled(false);
        buttons[pos].setBackground(bgEnabled);
        buttons[pos].setTextColor(textColorEnabled);

        for (int i = 0; i < selected.length; i++) {
            selected[i] = false;
        }
        selected[pos] = true;
    }

    public boolean getSelected(int pos) {
        return selected[pos];
    }

    public void setSelected(int pos, boolean enabled) {
        this.selected[pos] = enabled;
    }
}