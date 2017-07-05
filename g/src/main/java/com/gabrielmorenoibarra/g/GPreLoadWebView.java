package com.gabrielmorenoibarra.g;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

/**
 * Custom <code>WebView</code> for pre-loading management.
 * Created by Gabriel Moreno on 2017-05-29.
 */
public class GPreLoadWebView {

    public static final String TAG = GPreLoadWebView.class.getSimpleName();

    private Context context;
    private int themeResId;
    private int okResId;
    private LinearLayout layout;
    private DialogInterface.OnClickListener dialogClickListener;

    public GPreLoadWebView(Context context, String url, int themeResId, int okResId) {
        this.context = context;
        this.themeResId = themeResId;
        this.okResId = okResId;

        dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        break;
                }
            }
        };

        if (context != null && !((Activity) context).isFinishing()) {
            WebView wv = new WebView(context);
            wv.setWebViewClient(new WebViewClient());
            wv.loadUrl(url);
            WebSettings webSettings = wv.getSettings();
            webSettings.setJavaScriptEnabled(true);

            layout = new LinearLayout(context);
            layout.addView(wv);
        }
    }

    public void showMatchParent() {
        if (layout != null) {
            ViewGroup parent = (ViewGroup) layout.getParent();
            if (parent != null) {
                parent.removeView(layout);
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context, themeResId);
        Dialog dialog = builder.setView(layout).create();
        builder.setPositiveButton(context.getString(okResId), dialogClickListener);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        if (window != null) {
            lp.copyFrom(window.getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            dialog.show();
            window.setAttributes(lp);
        }
    }

    public void show() {
        if (layout != null) {
            ViewGroup parent = (ViewGroup) layout.getParent();
            if (parent != null) {
                parent.removeView(layout);
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context, themeResId);
        Dialog dialog = builder.setView(layout).create();
        builder.setPositiveButton(context.getString(okResId), dialogClickListener);
        dialog.show();
    }
}