package com.kantek.pray.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kantek.pray.ui.detail_setting.DetailAlarmActivity;

import butterknife.ButterKnife;

/**
 * The base fragment include some util methods
 * <p>
 * Created by LoiHo on 1/4/2016.
 */
public abstract class BaseFragment extends Fragment {

    protected static DetailAlarmActivity detailAlarmActivity;

    @LayoutRes
    public abstract int getLayoutId();

    protected abstract void initComponent();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        detailAlarmActivity = (DetailAlarmActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initComponent();
    }
}