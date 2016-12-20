package com.kantek.pray.ui.detail_koran;

import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kantek.pray.R;
import com.kantek.pray.base.BaseActivity;
import com.kantek.pray.data.database.DataMapper;
import com.kantek.pray.data.database.T_Koran;
import com.kantek.pray.define.Constants;
import com.kantek.pray.ui.detail_setting.DetailAlarmActivity;
import com.kantek.pray.utils.Navigator;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Kiet Nguyen on 12-Dec-16.
 */

public class DetailKoranActivity extends BaseActivity {

    @Bind(R.id.btn_alarm)
    ImageButton btn_alarm;
    @Bind(R.id.txtTitle)
    TextView txtTitle;
    @Bind(R.id.tv_content)
    TextView tv_content;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_count)
    TextView tv_count;

    private T_Koran koran = null;
    private int tap_count = 0;
    private boolean isChange;

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail_pray;
    }

    @Override
    protected void initComponent() {
        if (getIntent().getExtras().containsKey(Constants.KORAN_ENTITY))
            koran = (T_Koran) getIntent().getSerializableExtra(Constants.KORAN_ENTITY);
        showDetailInfo();
        isChange = false;
    }

    private void showDetailInfo() {
        if (koran == null) return;
        btn_alarm.setVisibility(View.VISIBLE);
        txtTitle.setText(koran.title);
        tv_content.setText(koran.content);
        tv_count.setText(String.valueOf(koran.tap_count));
        tv_title.setText(koran.title);
        tap_count = Integer.parseInt(tv_count.getText().toString());
    }

    private void saveCountTap() {
        tap_count = Integer.parseInt(tv_count.getText().toString());
        DataMapper.updateCountTap(koran.koran_id, tap_count);
    }

    @OnClick(R.id.btnBack)
    public void onClickBackHeader() {
        onCheckStateTap();
    }

    @OnClick(R.id.tv_back)
    public void onClickBack() {
        onCheckStateTap();
    }

    @OnClick(R.id.icon_back)
    public void onClickIconBack() {
        onCheckStateTap();
    }

    @OnClick(R.id.btn_reset)
    public void onClickReset() {
        isChange = true;
        tv_count.setText("0");
    }

    @OnClick(R.id.icon_reset)
    public void onClickIconReset() {
        isChange = true;
        tv_count.setText("0");
    }

    @OnClick(R.id.btn_alarm)
    public void onClickAlarm() {
        Navigator.openDetailAlarmActivity(DetailKoranActivity.this, koran, Constants.DETAIL_KORAN);
    }

    @OnClick(R.id.btn_tap)
    public void onClickTap() {
        tap_count++;
        tv_count.setText(String.valueOf(tap_count));
        isChange = true;
    }

    private void onCheckStateTap() {
        if (isChange) {
            saveCountTap();
            isChange = false;
            Navigator.openListPrayActivity(DetailKoranActivity.this);
        } else {
            Navigator.openListPrayActivity(DetailKoranActivity.this);
        }
    }

    @Override
    public void onBackPressed() {
        Navigator.openListPrayActivity(DetailKoranActivity.this);
    }

    @OnClick(R.id.txtTitle)
    public void onClickChangeTitle(){
        final Dialog dialog = new Dialog(DetailKoranActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_setting_name);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        final EditText edt_change_title = (EditText) dialog.findViewById(R.id.edt_change_title);
        final TextView tv_accept = (TextView) dialog.findViewById(R.id.tv_accept);
        final TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_cancel);
        tv_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleOld = tv_title.getText().toString();
                String titleNew = edt_change_title.getText().toString();
                if(titleNew.isEmpty()) {
                    Toast.makeText(dialog.getContext(), R.string.toast_msg_enter_title, Toast.LENGTH_SHORT).show();
                    return;
                }
                DataMapper.updateTitle(titleOld, titleNew);
                dialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
