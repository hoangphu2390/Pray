package com.kantek.pray.ui.detail_setting.dialog;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.kantek.pray.R;
import com.kantek.pray.base.BaseAdapter;
import com.kantek.pray.base.BaseHolder;

import butterknife.Bind;
import butterknife.OnCheckedChanged;

/**
 * Created by Kiet Nguyen on 18-Dec-16.
 */

public class SettingTitleAdapter extends BaseAdapter<String, BaseHolder> {

    public interface SelectedItem {
        void selectedInfidelity(String title);
    }

    SelectedItem listener;

    public SettingTitleAdapter(LayoutInflater inflater, SelectedItem listener) {
        super(inflater);
        this.listener = listener;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.item_setting_title_dialog;
        return new ViewHolder(inflater.inflate(layoutId, parent, false));
    }

    public class ViewHolder extends BaseHolder<String> {
        @Bind(R.id.tv_setting_title)
        TextView tv_setting_title;

        protected String title;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(String title) {
            this.title = title;
            tv_setting_title.setText(title);
        }

        @Override
        public void bindEvent() {
            itemView.setOnClickListener(v -> {
                listener.selectedInfidelity(title);
            });
        }
    }
}

