package com.tzj.baselib.chain.activity.delegate;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CloseHandler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 与生命周期想关的可以用Delegate放这里
 */
public class DelegateActivity extends AppCompatActivity {
    private List<ActivityDelegate> mList = new ArrayList<>();
    /**
     * 界面结束后，他将失效
     */
    protected CloseHandler mHandler = new CloseHandler();

    public void addDelegate(List<ActivityDelegate> list) {
        list.add(new LogDelegate(this));
        list.add(new HandlerDelegate(this));
        list.add(new SwipeBackDelegate(this));
        list.add(new ViewModelProvid(this));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //todo 有待验证
        if (savedInstanceState==null || !savedInstanceState.getBoolean("onSaveInstanceState", false)) {
            addDelegate(mList);
        }
        Iterator<ActivityDelegate> iterator = mList.iterator();
        while (iterator.hasNext()) {
            iterator.next().onCreate(savedInstanceState);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Iterator<ActivityDelegate> iterator = mList.iterator();
        while (iterator.hasNext()) {
            iterator.next().onStart();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Iterator<ActivityDelegate> iterator = mList.iterator();
        while (iterator.hasNext()) {
            iterator.next().onPostCreate(savedInstanceState);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Iterator<ActivityDelegate> iterator = mList.iterator();
        while (iterator.hasNext()) {
            iterator.next().onResume();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Iterator<ActivityDelegate> iterator = mList.iterator();
        while (iterator.hasNext()) {
            iterator.next().onPostResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Iterator<ActivityDelegate> iterator = mList.iterator();
        while (iterator.hasNext()) {
            iterator.next().onPause();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("onSaveInstanceState", true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Iterator<ActivityDelegate> iterator = mList.iterator();
        while (iterator.hasNext()) {
            iterator.next().onStop();
        }
    }

    @Override
    public void finish() {
        super.finish();
        Iterator<ActivityDelegate> iterator = mList.iterator();
        while (iterator.hasNext()) {
            iterator.next().onClear();
        }
        if (mHandler != null){
            mHandler.close();
        }
        mHandler = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Iterator<ActivityDelegate> iterator = mList.iterator();
        while (iterator.hasNext()) {
            iterator.next().onDestroy();
            iterator.remove();
        }
        if (mHandler != null && !mHandler.isClosed()) {
            mHandler.close();
            mHandler = null;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Iterator<ActivityDelegate> iterator = mList.iterator();
        while (iterator.hasNext()) {
            iterator.next().onNewIntent(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Iterator<ActivityDelegate> iterator = mList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().onActivityResult(requestCode, resultCode, data)) {
                return;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Iterator<ActivityDelegate> iterator = mList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().onRequestPermissionsResult(requestCode, permissions, grantResults)) {
                return;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Iterator<ActivityDelegate> iterator = mList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().onBackPressed()) {
                return;
            }
        }
    }

    @Override
    public Resources getResources() {
        Iterator<ActivityDelegate> iterator = mList.iterator();
        while (iterator.hasNext()) {
            Resources resources = iterator.next().getResources();
            if (resources != null) {
                return resources;
            }
        }
        return super.getResources();
    }

    @Override
    public <T extends View> T findViewById(int id) {
        View viewById = super.findViewById(id);
        if (viewById == null) {
            Iterator<ActivityDelegate> iterator = mList.iterator();
            while (iterator.hasNext()) {
                viewById = iterator.next().findViewById(id);
                if (viewById != null) {
                    return (T) viewById;
                }
            }
        }
        return (T) viewById;
    }


}
