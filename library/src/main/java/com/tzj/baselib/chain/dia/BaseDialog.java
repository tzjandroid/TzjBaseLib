package com.tzj.baselib.chain.dia;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tzj.baselib.R;
import com.tzj.baselib.chain.activity.BaseLibActivity;


public abstract class BaseDialog extends Dialog {
    protected View mRoot;

    public BaseDialog(Context context) {
        this(context, R.style.dialog_base);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mRoot = createView());
        init();
    }

    protected void init() {
    }

    protected abstract View createView();

    protected void setWindowAnimations(int a){
        Window window = getWindow();
        if (window!=null){
            window.setWindowAnimations(a);
        }
    }
    /**
     * 无背景色
     */
    public void clearBack(){
        Window window = getWindow();
        if (window!=null){
            window.clearFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND | WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
    }
    /**
     *
     */
    protected void setGravity(int gravity) {
        Window window = getWindow();
        if (window!=null){
            window.setGravity(Gravity.BOTTOM);
        }
    }
    protected void setPadding(int p){
        setPadding(p,p,p,p);
    }
    protected void setPadding(int l,int t,int r,int b){
        Window window = this.getWindow();
        //去掉dialog默认的padding
        if (window!=null){
            window.getDecorView().setPadding(l, t, r, b);
        }
    }

    /**
     * 设置宽高
     */
    protected void setLayout(int w,int h){
        Window window = this.getWindow();
        if (window!=null){
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = w;
            lp.height = h;
            window.setAttributes(lp);
        }
    }
    /**
     * 从底部
     */
    protected void setFromBottom(){
        setPadding(0);
        setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        setGravity(Gravity.BOTTOM);
        setWindowAnimations(R.style.animation_bottom);
    }
    protected void setFromCenter(){
        setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Point outSize = new Point();
        wm.getDefaultDisplay().getSize(outSize);
        setPadding((int) (outSize.x * 0.1));
    }

    /**
     * @param b 是否可消失
     */
    public void show(boolean b){
        setCancelable(b);
        show();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public Activity getActivity(){
        Context context = getContext();
        if (context != null && context instanceof ContextWrapper){
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (context != null && context instanceof Activity){
            return (Activity) context;
        }
        return null;
    }

    public BaseDialog setDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(listener);
        return this;
    }

    //====================
    public void showProgress(){
        Activity activity = getActivity();
        if (activity != null && activity instanceof BaseLibActivity){
            ((BaseLibActivity) activity).showProgress();
        }
    }
    public void dismissProgress(){
        Activity activity = getActivity();
        if (activity != null && activity instanceof BaseLibActivity){
            ((BaseLibActivity) activity).dismissProgress();
        }
    }

}
