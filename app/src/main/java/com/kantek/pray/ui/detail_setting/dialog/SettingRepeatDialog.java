package com.kantek.pray.ui.detail_setting.dialog;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kantek.pray.R;
import com.kantek.pray.base.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Kiet Nguyen on 15-Dec-16.
 */

public class SettingRepeatDialog extends BaseFragment implements SettingRepeatAdapter.SelectedItem {

    @Bind(R.id.recycler_repeat)
    RecyclerView recycler_repeat;

    private final String[] listDate = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
    private List<String> saveDate;
    private SettingRepeatAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_setting_repeat;
    }

    @Override
    protected void initComponent() {
        setupRecyclerView();
        setupData();
    }

    public void setupRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recycler_repeat.setLayoutManager(llm);
        adapter = new SettingRepeatAdapter(getActivity().getLayoutInflater(), this);
        recycler_repeat.setAdapter(adapter);
    }

    private void setupData() {
        adapter.setDataSource(Arrays.asList(listDate));
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.start_alarm)
    public void onClickSetAlarm() {
        detailAlarmActivity.showDialog(null);
    }

    @Override
    public void selectedInfidelity(String date) {
        if (saveDate == null)
            saveDate = new ArrayList<>();
        saveDate.add(date);
    }
}

