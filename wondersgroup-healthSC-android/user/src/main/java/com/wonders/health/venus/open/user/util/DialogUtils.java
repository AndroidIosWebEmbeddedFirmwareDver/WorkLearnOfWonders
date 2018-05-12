package com.wonders.health.venus.open.user.util;

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

    public static void showNormalChoiceMsessage(Context context, String title, String msg, String cancleTitle, String sureTitle,
                                                final DialogInterface.OnClickListener cancleOnClickListener,
                                                final DialogInterface.OnClickListener sureOnClickListener
    ) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setNegativeButton(null != cancleTitle ? cancleTitle : "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (null != cancleOnClickListener)
                            cancleOnClickListener.onClick(dialog, which);
                    }
                })
                .setPositiveButton(null != sureTitle ? sureTitle : "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (null != sureOnClickListener)
                            sureOnClickListener.onClick(dialog, which);
                    }
                })
                .create().show();
    }

}
