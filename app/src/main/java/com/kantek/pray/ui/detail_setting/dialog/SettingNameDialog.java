package com.kantek.pray.ui.detail_setting.dialog;

import android.widget.EditText;

import com.kantek.pray.R;
import com.kantek.pray.base.BaseFragment;
import com.kantek.pray.listener.SettingTitleListener;
import com.kantek.pray.utils.Utils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Kiet Nguyen on 15-Dec-16.
 */

public class SettingNameDialog extends BaseFragment {

    @Bind(R.id.edt_change_title)
    EditText edt_change_title;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_setting_name;
    }

    @Override
    protected void initComponent() {

    }

    @OnClick(R.id.tv_accept)
    public void onClickAccept() {
        String title = edt_change_title.getText().toString();
        Utils.hideSoftKeyboard(getActivity());
        detailAlarmActivity.showDialog(null);
        ((SettingTitleListener) detailAlarmActivity).onAcceptListener(title);
    }

    @OnClick(R.id.tv_cancel)
    public void onClickCancel() {
        detailAlarmActivity.showDialog(null);
    }
}


