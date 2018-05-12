package com.wondersgroup.hs.healthcloud.common.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class DialogUtils {

    public static void showNormalMessage(Context context, String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("提示");
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {

            }
        });
        alertDialog.create();
        alertDialog.show();

    }

}
