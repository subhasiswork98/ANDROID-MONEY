package com.expance.manager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.expance.manager.R;
import com.expance.manager.Utility.SharePreferenceHelper;
import java.util.List;

/* loaded from: classes3.dex */
public class SettingWeekDayAdapter extends BaseAdapter {
    Context context;
    private LayoutInflater inflater;
    private List<String> list;

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return 0L;
    }

    public SettingWeekDayAdapter(Context context, List<String> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.list.size();
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        String str = this.list.get(position);
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.list_setting_radio, (ViewGroup) null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        int firstDayOfWeek = SharePreferenceHelper.getFirstDayOfWeek(this.context);
        viewHolder.radioButton.setText(str);
        if (firstDayOfWeek - 1 == position) {
            viewHolder.radioGroup.check(R.id.radioButton);
        } else {
            viewHolder.radioGroup.clearCheck();
        }
        return convertView;
    }

    /* loaded from: classes3.dex */
    private class ViewHolder {
        RadioButton radioButton;
        RadioGroup radioGroup;

        ViewHolder(View itemView) {
            this.radioGroup = (RadioGroup) itemView.findViewById(R.id.radioGroup);
            this.radioButton = (RadioButton) itemView.findViewById(R.id.radioButton);
        }
    }
}
