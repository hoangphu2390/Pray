package com.kantek.pray.ui.detail_setting.dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kantek.pray.R;
import com.kantek.pray.base.BaseAdapter;
import com.kantek.pray.base.BaseHolder;

import butterknife.Bind;

/**
 * Created by Kiet Nguyen on 13-Dec-16.
 */

public class SettingRepeatAdapter extends BaseAdapter<String, BaseHolder> {

    public interface SelectedItem {
        void selectedInfidelity(String date);
    }

    SelectedItem listener;

    public SettingRepeatAdapter(LayoutInflater inflater, SelectedItem listener) {
        super(inflater);
        this.listener = listener;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_repeat_setting;
        return new ViewHolder(inflater.inflate(layoutId, parent, false));
    }

    public class ViewHolder extends BaseHolder<String> {
        @Bind(R.id.tv_item_repeat)
        TextView tv_item_repeat;

        protected String date;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(String date) {
            this.date = date;
            tv_item_repeat.setText(date);
        }

        @Override
        public void bindEvent() {
            itemView.setOnClickListener(v -> {
                listener.selectedInfidelity(date);
            });
        }
    }
}

