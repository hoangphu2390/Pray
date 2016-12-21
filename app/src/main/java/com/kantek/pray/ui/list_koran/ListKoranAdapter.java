package com.kantek.pray.ui.list_koran;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kantek.pray.R;
import com.kantek.pray.base.BaseAdapter;
import com.kantek.pray.base.BaseHolder;
import com.kantek.pray.data.database.T_Koran;
import com.kantek.pray.define.Constants;
import com.kantek.pray.ui.detail_koran.DetailKoranActivity;
import com.kantek.pray.utils.Navigator;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Kiet Nguyen on 12-Dec-16.
 */

public class ListKoranAdapter extends BaseAdapter<T_Koran, BaseHolder> {

    public interface SelectedItem {
        void selectedInfidelity(T_Koran koran);
    }

    SelectedItem listener;
    protected Context context;

    public ListKoranAdapter(LayoutInflater inflater, Context context, SelectedItem listener) {
        super(inflater);
        this.listener = listener;
        this.context = context;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_pray;
        return new ListFactHolder(inflater.inflate(layoutId, parent, false));
    }

    public class ListFactHolder extends BaseHolder<T_Koran> {
        @Bind(R.id.tv_item_title_pray)
        TextView tv_item_title_pray;
        @Bind(R.id.tv_show_setting_alarm)
        TextView tv_show_setting_alarm;
        @Bind(R.id.iv_bell)
        ImageView iv_bell;

        protected T_Koran koran;

        public ListFactHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(T_Koran koran) {
            this.koran = koran;
            tv_item_title_pray.setText(koran.title);
            if (!koran.time.equals(Constants.NOT_YET)) {
                tv_show_setting_alarm.setVisibility(View.VISIBLE);
                iv_bell.setImageResource(R.drawable.icon_bell);
                tv_show_setting_alarm.setText(koran.time);
            } else {
                tv_show_setting_alarm.setVisibility(View.GONE);
                iv_bell.setImageResource(R.drawable.icon_bell_blur);
            }
        }

        @Override
        public void bindEvent() {
            itemView.setOnClickListener(v -> {
                listener.selectedInfidelity(koran);
            });
        }

        @OnClick(R.id.view_alarm)
        public void onClickAlarm(){
            Navigator.openDetailAlarmActivity((Activity)context, koran, Constants.LIST_KORAN_ADAPTER);
        }
    }
}
