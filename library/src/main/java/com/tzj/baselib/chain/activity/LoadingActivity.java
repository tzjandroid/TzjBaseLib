package com.tzj.baselib.chain.activity;

import android.content.Context;
import android.support.design.widget.Snackbar;

import com.tzj.baselib.R;
import com.tzj.baselib.chain.dia.BaseDialog;
import com.tzj.baselib.chain.dia.DefaultCreateDialog;
import com.tzj.baselib.chain.dia.LoadingDialog;

/**
 * 有加载中，和 Toast
 */
public class LoadingActivity extends SelectPicActivity {
    private static DefaultCreateDialog createDialog = new DefaultCreateDialog() {
        @Override
        public BaseDialog createDialog(Context ctx) {
            return new LoadingDialog(ctx);
        }
    };

    public static void setCreateDialog(DefaultCreateDialog createDialog) {
        LoadingActivity.createDialog = createDialog;
    }

    private BaseDialog dialog;
    private volatile Integer number = 0;

    public void showProgress() {
        showProgress(getResources().getBoolean(R.bool.progress_can_cancel));
    }
    protected void showProgress(boolean canCancle) {
        synchronized (this) {
            if (dialog == null) {
                number = 0;
                dialog = createDialog.createDialog(this);
                dialog.setOnDismissListener(dialog->number = 0);
            }
            if (!dialog.isShowing()) {
                dialog.show(canCancle);
            }
            ++number;
        }
    }

    public void dismissProgress() {
        synchronized (this) {
            --number;
            if (number > 0 || dialog == null) {
                return;
            }
            dialog.dismiss();
            dialog = null;
        }
    }

    public void toast(Object obj) {
        Snackbar.make(getWindow().getDecorView(), "", Snackbar.LENGTH_LONG)
                .setText(obj + "").show();
    }

    public void toast(int r) {
        if (r > 0 && (r & 0x7f000000) == 0x7f000000) {
            Snackbar.make(getWindow().getDecorView(), "", Snackbar.LENGTH_LONG)
                    .setText(r).show();
        } else {
            toast(r + "");
        }
    }
}
