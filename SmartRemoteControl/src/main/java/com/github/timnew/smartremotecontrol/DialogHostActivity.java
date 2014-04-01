package com.github.timnew.smartremotecontrol;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class DialogHostActivity extends Activity implements DialogInterface.OnDismissListener, DialogInterface.OnCancelListener {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.no_ir_dialog_title)
                .setMessage(R.string.no_ir_dialog_description)
                .setNeutralButton(R.string.quit_app, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();

        dialog.setOnDismissListener(this);
        dialog.setOnCancelListener(this);

        dialog.show();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        finish();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        finish();
    }
}