package com.kantek.pray.ui.detail_setting.dialog;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kantek.pray.R;
import com.kantek.pray.base.BaseFragment;
import com.kantek.pray.data.database.DataMapper;
import com.kantek.pray.data.database.T_Koran;
import com.kantek.pray.listener.SettingTitleListener;
import com.kantek.pray.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Kiet Nguyen on 18-Dec-16.
 */

public class SettingTitleDialog extends BaseFragment implements SettingTitleAdapter.SelectedItem {

    @Bind(R.id.recycler_title)
    RecyclerView recycler_title;

    private List<String> listName;
    private SettingTitleAdapter adapter;
    private List<T_Koran> koranList;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_setting_title;
    }

    @Override
    protected void initComponent() {
        setupRecyclerView();
        setupData();
    }

    public void setupRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler_title.setLayoutManager(llm);
        adapter = new SettingTitleAdapter(getActivity().getLayoutInflater(), this);
        recycler_title.setAdapter(adapter);
    }

    private void setupData() {
        listName = new ArrayList<>();
        koranList = DataMapper.getList_InfoKoran();
        for (T_Koran koran : koranList) {
            if (listName.size() == 0) listName.add(koran.title);
            else {
                int i = 0, count = 0;
                for (; i < listName.size(); i++) {
                    if (!koran.title.equals(listName.get(i)))
                        count++;
                }
                if (count == listName.size()) listName.add(koran.title);
            }
        }
        adapter.setDataSource(listName);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void selectedInfidelity(String title) {
        detailAlarmActivity.showDialog(null);
        ((SettingTitleListener) detailAlarmActivity).onAcceptListener(title);
    }

    @OnClick(R.id.btnCancel)
    public void onClickCancel() {
        detailAlarmActivity.showDialog(null);
    }
}

