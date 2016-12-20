package com.kantek.pray.ui.list_alarm;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kantek.pray.R;
import com.kantek.pray.base.BaseAdapter;
import com.kantek.pray.base.BaseHolder;
import com.kantek.pray.data.database.DataMapper;
import com.kantek.pray.data.database.T_Koran;
import com.kantek.pray.define.Constants;
import com.kantek.pray.utils.Utils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Kiet Nguyen on 12-Dec-16.
 */

public class ListAlarmAdapter extends BaseAdapter<T_Koran, BaseHolder> {

    private Context context;

    public interface SelectedItem {
        void selectedInfidelity(T_Koran koran);
    }

    private SelectedItem listener;

    public ListAlarmAdapter(LayoutInflater inflater, Context context, SelectedItem listener) {
        super(inflater);
        this.context = context;
        this.listener = listener;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_setting;
        return new ListFactHolder(inflater.inflate(layoutId, parent, false));
    }

    public class ListFactHolder extends BaseHolder<T_Koran> {
        @Bind(R.id.tv_setting_time)
        TextView tv_setting_time;
        @Bind(R.id.tv_setting_title)
        TextView tv_setting_title;
        @Bind(R.id.switchCompat)
        SwitchCompat switchCompat;

        protected T_Koran koran;

        public ListFactHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(T_Koran koran) {
            this.koran = koran;
            if (Utils.formatTimeTo24hours(koran.time) != null) {
                tv_setting_time.setText(Utils.formatTimeTo24hours(koran.time));
            }
            if (koran.description.equals(""))
                tv_setting_title.setText(koran.title);
            else
                tv_setting_title.setText(koran.description);
            setStateItem(koran.is_enable);

            switchCompat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (koran.is_enable == 1)
                        koran.is_enable = 0;
                    else
                        koran.is_enable = 1;
                    setStateItem(koran.is_enable);
                    DataMapper.updateEnable(koran.koran_id, koran.is_enable);
                }
            });
        }

        @Override
        public void bindEvent() {
            itemView.setOnClickListener(v -> {
                listener.selectedInfidelity(koran);
            });
        }

        private void setStateItem(int isSnooze) {
            String color = null;
            if (isSnooze == 1) {
                color = context.getString(R.string.color_active_snooze);
                switchCompat.setChecked(true);
            } else {
                color = context.getString(R.string.color_deactive_snooze);
                switchCompat.setChecked(false);
            }
            tv_setting_time.setTextColor(Color.parseColor(color));
            tv_setting_title.setTextColor(Color.parseColor(color));
        }
    }
}
