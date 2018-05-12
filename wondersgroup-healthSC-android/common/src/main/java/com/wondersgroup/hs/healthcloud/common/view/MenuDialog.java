package com.wondersgroup.hs.healthcloud.common.view;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;

public class MenuDialog extends Dialog {
    
    public MenuDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    public MenuDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    public MenuDialog(Context context) {
        super(context);
        init();
    }
    
    private void init() {
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (this.isShowing()) {
                this.dismiss();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
