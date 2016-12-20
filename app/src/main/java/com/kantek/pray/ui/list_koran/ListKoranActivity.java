package com.kantek.pray.ui.list_koran;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kantek.pray.R;
import com.kantek.pray.base.BaseActivity;
import com.kantek.pray.data.database.DataMapper;
import com.kantek.pray.data.database.T_Koran;
import com.kantek.pray.define.Constants;
import com.kantek.pray.utils.Navigator;
import com.kantek.pray.utils.Utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Kiet Nguyen on 12-Dec-16.
 */

public class ListKoranActivity extends BaseActivity implements ListKoranAdapter.SelectedItem {

    @Bind(R.id.txtTitle)
    TextView txtTitle;
    @Bind(R.id.recycler_pray)
    RecyclerView recycler_pray;
    @Bind(R.id.btnBack)
    ImageButton btnBack;

    private ListKoranAdapter adapter;
    private List<T_Koran> koranList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_list_pray;
    }

    @Override
    protected void initComponent() {
        txtTitle.setText(Constants.TAG_NAME_LIST_KORAN_SCREEN);
        setupRecyclerView();
        showContentUI();
    }

    public void setupRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler_pray.setLayoutManager(llm);
        adapter = new ListKoranAdapter(getLayoutInflater(), this, this);
        recycler_pray.setAdapter(adapter);
    }


    public void showContentUI() {
        koranList = DataMapper.getList_InfoKoran();
        if (koranList == null || koranList.size() == 0) return;
        sortByTimer();
        Utils.setIntegerPreference(this, Constants.INDEX_INCREMENT, koranList.size());
        adapter.setDataSource(koranList);
        adapter.notifyDataSetChanged();
    }

    private void sortByTimer() {
        Collections.sort(koranList, new Comparator<T_Koran>() {
            @Override
            public int compare(T_Koran t_koran1, T_Koran t_koran2) {
                return t_koran1.time.compareTo(t_koran2.time) ;
            }
        });
    }

    @Override
    public void selectedInfidelity(T_Koran koran) {
        finish();
        Navigator.openDetailKoranActivity(ListKoranActivity.this, koran);
    }

    @OnClick(R.id.btnBack)
    public void OnClickBack() {
        Navigator.openMainActivity(ListKoranActivity.this);
    }

    @Override
    public void onBackPressed() {
        Navigator.openMainActivity(ListKoranActivity.this);
    }
}
