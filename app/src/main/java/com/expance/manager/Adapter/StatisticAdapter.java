package com.expance.manager.Adapter;

import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.expance.manager.Model.CalendarSummary;
import com.expance.manager.Model.Stats;
import com.expance.manager.R;
import com.expance.manager.Utility.Helper;
import com.expance.manager.Utility.SharePreferenceHelper;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes3.dex */
public class StatisticAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    long endingBalance;
    LayoutInflater inflater;
    OnItemClickListener listener;
    long openingBalance;
    private CalendarSummary summary = new CalendarSummary(0, 0);
    private List<Stats> pieStatsList = new ArrayList();

    /* loaded from: classes3.dex */
    public interface OnItemClickListener {
        void OnItemClick(View v, int position);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return 5;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }
        if (position == 1) {
            return 1;
        }
        return position == 3 ? 2 : 3;
    }

    public StatisticAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setOverviewSummary(CalendarSummary calendarSummary) {
        this.summary = calendarSummary;
    }

    public void setPieStatsList(List<Stats> list) {
        this.pieStatsList = list;
    }

    public void setBalance(long openingBalance, long endingBalance) {
        this.openingBalance = openingBalance;
        this.endingBalance = endingBalance;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new BalanceHolder(this.inflater.inflate(R.layout.list_statistic_balance, parent, false));
        }
        if (viewType == 1) {
            return new OverviewHolder(this.inflater.inflate(R.layout.list_statistic_overview, parent, false));
        }
        if (viewType == 2) {
            return new PieHolder(this.inflater.inflate(R.layout.list_statistic_pie, parent, false));
        }
        return new MoreHolder(this.inflater.inflate(R.layout.list_statistic_more, parent, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == 0) {
            BalanceHolder balanceHolder = (BalanceHolder) holder;
            String beautifyAmount = Helper.getBeautifyAmount(SharePreferenceHelper.getAccountSymbol(this.context), this.openingBalance);
            String beautifyAmount2 = Helper.getBeautifyAmount(SharePreferenceHelper.getAccountSymbol(this.context), this.endingBalance);
            balanceHolder.openLabel.setText(beautifyAmount);
            balanceHolder.endLabel.setText(beautifyAmount2);
        } else if (itemViewType == 1) {
            OverviewHolder overviewHolder = (OverviewHolder) holder;
            if (this.summary != null) {
                String beautifyAmount3 = Helper.getBeautifyAmount(SharePreferenceHelper.getAccountSymbol(this.context), this.summary.getExpense());
                String beautifyAmount4 = Helper.getBeautifyAmount(SharePreferenceHelper.getAccountSymbol(this.context), this.summary.getIncome());
                String beautifyAmount5 = Helper.getBeautifyAmount(SharePreferenceHelper.getAccountSymbol(this.context), this.summary.getNetIncome());
                overviewHolder.expenseLabel.setText(beautifyAmount3);
                overviewHolder.incomeLabel.setText(beautifyAmount4);
                overviewHolder.netLabel.setText(beautifyAmount5);
            }
        } else if (itemViewType == 2) {
            NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
            numberFormat.setMaximumFractionDigits(1);
            numberFormat.setMinimumFractionDigits(1);
            PieHolder pieHolder = (PieHolder) holder;
            if (this.pieStatsList.size() > 0) {
                pieHolder.pieStatLabel1.setText(this.pieStatsList.get(0).getName(this.context));
                pieHolder.pieStatPercentLabel1.setText("(" + numberFormat.format(this.pieStatsList.get(0).getPercent()) + "%)");
                if (Build.VERSION.SDK_INT >= 29) {
                    pieHolder.pieStatView1.getBackground().setColorFilter(new BlendModeColorFilter(Color.parseColor(this.pieStatsList.get(0).getColor()), BlendMode.SRC_OVER));
                } else {
                    pieHolder.pieStatView1.getBackground().setColorFilter(Color.parseColor(this.pieStatsList.get(0).getColor()), PorterDuff.Mode.SRC_OVER);
                }
                if (this.pieStatsList.size() >= 2) {
                    pieHolder.pieStat2.setVisibility(0);
                    pieHolder.pieStatLabel2.setText(this.pieStatsList.get(1).getName(this.context));
                    pieHolder.pieStatPercentLabel2.setText("(" + numberFormat.format(this.pieStatsList.get(1).getPercent()) + "%)");
                    if (Build.VERSION.SDK_INT >= 29) {
                        pieHolder.pieStatView2.getBackground().setColorFilter(new BlendModeColorFilter(Color.parseColor(this.pieStatsList.get(1).getColor()), BlendMode.SRC_OVER));
                    } else {
                        pieHolder.pieStatView2.getBackground().setColorFilter(Color.parseColor(this.pieStatsList.get(1).getColor()), PorterDuff.Mode.SRC_OVER);
                    }
                } else {
                    pieHolder.pieStat2.setVisibility(8);
                }
                if (this.pieStatsList.size() >= 3) {
                    pieHolder.pieStat3.setVisibility(0);
                    pieHolder.pieStatLabel3.setText(this.pieStatsList.get(2).getName(this.context));
                    pieHolder.pieStatPercentLabel3.setText("(" + numberFormat.format(this.pieStatsList.get(2).getPercent()) + "%)");
                    if (Build.VERSION.SDK_INT >= 29) {
                        pieHolder.pieStatView3.getBackground().setColorFilter(new BlendModeColorFilter(Color.parseColor(this.pieStatsList.get(2).getColor()), BlendMode.SRC_OVER));
                    } else {
                        pieHolder.pieStatView3.getBackground().setColorFilter(Color.parseColor(this.pieStatsList.get(2).getColor()), PorterDuff.Mode.SRC_OVER);
                    }
                } else {
                    pieHolder.pieStat3.setVisibility(8);
                }
                if (this.pieStatsList.size() >= 4) {
                    pieHolder.pieStat4.setVisibility(0);
                    pieHolder.pieStatLabel4.setText(this.pieStatsList.get(3).getName(this.context));
                    pieHolder.pieStatPercentLabel4.setText("(" + numberFormat.format(this.pieStatsList.get(3).getPercent()) + "%)");
                    if (Build.VERSION.SDK_INT >= 29) {
                        pieHolder.pieStatView4.getBackground().setColorFilter(new BlendModeColorFilter(Color.parseColor(this.pieStatsList.get(3).getColor()), BlendMode.SRC_OVER));
                    } else {
                        pieHolder.pieStatView4.getBackground().setColorFilter(Color.parseColor(this.pieStatsList.get(3).getColor()), PorterDuff.Mode.SRC_OVER);
                    }
                } else {
                    pieHolder.pieStat4.setVisibility(8);
                }
                if (this.pieStatsList.size() >= 5) {
                    float f = 0.0f;
                    for (int i = 0; i < this.pieStatsList.size(); i++) {
                        if (i >= 4) {
                            f = (float) (f + this.pieStatsList.get(i).getPercent());
                        }
                    }
                    pieHolder.pieStat5.setVisibility(0);
                    pieHolder.pieStatLabel5.setText(R.string.others);
                    pieHolder.pieStatPercentLabel5.setText("(" + numberFormat.format(f) + "%)");
                    if (Build.VERSION.SDK_INT >= 29) {
                        pieHolder.pieStatView5.getBackground().setColorFilter(new BlendModeColorFilter(Color.parseColor("#708090"), BlendMode.SRC_OVER));
                    } else {
                        pieHolder.pieStatView5.getBackground().setColorFilter(Color.parseColor("#708090"), PorterDuff.Mode.SRC_OVER);
                    }
                } else {
                    pieHolder.pieStat5.setVisibility(8);
                }
            } else {
                pieHolder.pieStat1.setVisibility(0);
                pieHolder.pieStatLabel1.setText("–");
                pieHolder.pieStatLabel2.setText("–");
                pieHolder.pieStatLabel3.setText("–");
                pieHolder.pieStatLabel4.setText("–");
                pieHolder.pieStatLabel5.setText("–");
                pieHolder.pieStatPercentLabel1.setText("(0.0%)");
                pieHolder.pieStatPercentLabel2.setText("(0.0%)");
                pieHolder.pieStatPercentLabel3.setText("(0.0%)");
                pieHolder.pieStatPercentLabel4.setText("(0.0%)");
                pieHolder.pieStatPercentLabel5.setText("(0.0%)");
                if (Build.VERSION.SDK_INT >= 29) {
                    pieHolder.pieStatView1.getBackground().setColorFilter(new BlendModeColorFilter(Color.parseColor("#03F7CA"), BlendMode.SRC_OVER));
                    pieHolder.pieStatView2.getBackground().setColorFilter(new BlendModeColorFilter(Color.parseColor("#FF4652"), BlendMode.SRC_OVER));
                    pieHolder.pieStatView3.getBackground().setColorFilter(new BlendModeColorFilter(Color.parseColor("#FFC137"), BlendMode.SRC_OVER));
                    pieHolder.pieStatView4.getBackground().setColorFilter(new BlendModeColorFilter(Color.parseColor("#537DEB"), BlendMode.SRC_OVER));
                    pieHolder.pieStatView5.getBackground().setColorFilter(new BlendModeColorFilter(Color.parseColor("#9457FA"), BlendMode.SRC_OVER));
                } else {
                    pieHolder.pieStatView1.getBackground().setColorFilter(Color.parseColor("#03F7CA"), PorterDuff.Mode.SRC_OVER);
                    pieHolder.pieStatView2.getBackground().setColorFilter(Color.parseColor("#FF4652"), PorterDuff.Mode.SRC_OVER);
                    pieHolder.pieStatView3.getBackground().setColorFilter(Color.parseColor("#FFC137"), PorterDuff.Mode.SRC_OVER);
                    pieHolder.pieStatView4.getBackground().setColorFilter(Color.parseColor("#537DEB"), PorterDuff.Mode.SRC_OVER);
                    pieHolder.pieStatView5.getBackground().setColorFilter(Color.parseColor("#9457FA"), PorterDuff.Mode.SRC_OVER);
                }
                pieHolder.pieStat1.setVisibility(0);
                pieHolder.pieStat2.setVisibility(0);
                pieHolder.pieStat3.setVisibility(0);
                pieHolder.pieStat4.setVisibility(0);
                pieHolder.pieStat5.setVisibility(0);
            }
            setPieChart(pieHolder.pieChart);
        }
    }

    /* loaded from: classes3.dex */
    public class MoreHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public MoreHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (StatisticAdapter.this.listener != null) {
                StatisticAdapter.this.listener.OnItemClick(view, getLayoutPosition());
            }
        }
    }

    /* loaded from: classes3.dex */
    public class OverviewHolder extends RecyclerView.ViewHolder {
        TextView expenseLabel;
        TextView incomeLabel;
        TextView netLabel;

        public OverviewHolder(View itemView) {
            super(itemView);
            this.incomeLabel = (TextView) itemView.findViewById(R.id.incomeLabel);
            this.expenseLabel = (TextView) itemView.findViewById(R.id.expenseLabel);
            this.netLabel = (TextView) itemView.findViewById(R.id.netLabel);
        }
    }

    /* loaded from: classes3.dex */
    public class BalanceHolder extends RecyclerView.ViewHolder {
        TextView endLabel;
        TextView openLabel;

        public BalanceHolder(View itemView) {
            super(itemView);
            this.openLabel = (TextView) itemView.findViewById(R.id.openingLabel);
            this.endLabel = (TextView) itemView.findViewById(R.id.endingLabel);
        }
    }

    /* loaded from: classes3.dex */
    public class PieHolder extends RecyclerView.ViewHolder {
        PieChart pieChart;
        ConstraintLayout pieStat1;
        ConstraintLayout pieStat2;
        ConstraintLayout pieStat3;
        ConstraintLayout pieStat4;
        ConstraintLayout pieStat5;
        TextView pieStatLabel1;
        TextView pieStatLabel2;
        TextView pieStatLabel3;
        TextView pieStatLabel4;
        TextView pieStatLabel5;
        TextView pieStatPercentLabel1;
        TextView pieStatPercentLabel2;
        TextView pieStatPercentLabel3;
        TextView pieStatPercentLabel4;
        TextView pieStatPercentLabel5;
        View pieStatView1;
        View pieStatView2;
        View pieStatView3;
        View pieStatView4;
        View pieStatView5;

        PieHolder(View itemView) {
            super(itemView);
            this.pieChart = (PieChart) itemView.findViewById(R.id.pieChart);
            this.pieStat1 = (ConstraintLayout) itemView.findViewById(R.id.pieStat1);
            this.pieStat2 = (ConstraintLayout) itemView.findViewById(R.id.pieStat2);
            this.pieStat3 = (ConstraintLayout) itemView.findViewById(R.id.pieStat3);
            this.pieStat4 = (ConstraintLayout) itemView.findViewById(R.id.pieStat4);
            this.pieStat5 = (ConstraintLayout) itemView.findViewById(R.id.pieStat5);
            this.pieStatView1 = itemView.findViewById(R.id.pieStatView1);
            this.pieStatView2 = itemView.findViewById(R.id.pieStatView2);
            this.pieStatView3 = itemView.findViewById(R.id.pieStatView3);
            this.pieStatView4 = itemView.findViewById(R.id.pieStatView4);
            this.pieStatView5 = itemView.findViewById(R.id.pieStatView5);
            this.pieStatLabel1 = (TextView) itemView.findViewById(R.id.pieStatLabel1);
            this.pieStatLabel2 = (TextView) itemView.findViewById(R.id.pieStatLabel2);
            this.pieStatLabel3 = (TextView) itemView.findViewById(R.id.pieStatLabel3);
            this.pieStatLabel4 = (TextView) itemView.findViewById(R.id.pieStatLabel4);
            this.pieStatLabel5 = (TextView) itemView.findViewById(R.id.pieStatLabel5);
            this.pieStatPercentLabel1 = (TextView) itemView.findViewById(R.id.pieStatPercentLabel1);
            this.pieStatPercentLabel2 = (TextView) itemView.findViewById(R.id.pieStatPercentLabel2);
            this.pieStatPercentLabel3 = (TextView) itemView.findViewById(R.id.pieStatPercentLabel3);
            this.pieStatPercentLabel4 = (TextView) itemView.findViewById(R.id.pieStatPercentLabel4);
            this.pieStatPercentLabel5 = (TextView) itemView.findViewById(R.id.pieStatPercentLabel5);
        }
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private void setPieChart(final PieChart pieChart) {
        pieChart.highlightValue(null);
        pieChart.setCenterText(getCenterAmount());
        pieChart.setCenterTextColor(Helper.getAttributeColor(this.context, R.attr.primaryTextColor));
        pieChart.setUsePercentValues(true);
        pieChart.setTransparentCircleRadius(0.0f);
        pieChart.setHoleRadius(70.0f);
        pieChart.setHoleColor(Helper.getAttributeColor(this.context, R.attr.secondaryBackground));
        pieChart.getDescription().setEnabled(false);
        pieChart.setRotationEnabled(true);
        pieChart.setDrawEntryLabels(false);
        pieChart.setUsePercentValues(true);
        pieChart.getLegend().setEnabled(false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new PieEntry(100.0f, "empty"));
        PieDataSet pieDataSet = new PieDataSet(arrayList, "");
        pieDataSet.setColors(Helper.getAttributeColor(this.context, R.attr.progressBar));
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(false);
        pieData.setValueTextSize(14.0f);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextColor(-1);
        pieChart.setData(pieData);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() { // from class: com.ktwapps.walletmanager.Adapter.StatisticAdapter.1
            @Override // com.github.mikephil.charting.listener.OnChartValueSelectedListener
            public void onValueSelected(Entry e, Highlight h) {
                if (StatisticAdapter.this.pieStatsList.size() != 0) {
                    PieEntry pieEntry = (PieEntry) e;
                    pieChart.setCenterText(StatisticAdapter.this.getCenterAmount((long) pieEntry.getValue(), pieEntry.getLabel()));
                }
            }

            @Override // com.github.mikephil.charting.listener.OnChartValueSelectedListener
            public void onNothingSelected() {
                pieChart.setCenterText(StatisticAdapter.this.getCenterAmount());
            }
        });
        setPieData(pieChart);
    }

    private void setPieData(PieChart pieChart) {
        ArrayList arrayList = new ArrayList();
        if (this.pieStatsList.size() > 0) {
            ArrayList arrayList2 = new ArrayList();
            int i = 0;
            while (true) {
                if (i >= this.pieStatsList.size()) {
                    break;
                } else if (i >= 4) {
                    float f = 0.0f;
                    for (int i2 = 0; i2 < this.pieStatsList.size(); i2++) {
                        if (i2 >= 4) {
                            arrayList2.add(Integer.valueOf(Color.parseColor("#708090")));
                            f += (float) this.pieStatsList.get(i2).getAmount();
                        }
                    }
                    if (0.0f > f) {
                        f = -f;
                    }
                    arrayList.add(new PieEntry(f, this.context.getResources().getString(R.string.others)));
                } else {
                    Stats stats = this.pieStatsList.get(i);
                    arrayList2.add(Integer.valueOf(Color.parseColor(stats.getColor())));
                    int i3 = (0L > stats.getAmount() ? 1 : (0L == stats.getAmount() ? 0 : -1));
                    long amount = stats.getAmount();
                    if (i3 > 0) {
                        amount = -amount;
                    }
                    arrayList.add(new PieEntry((float) amount, stats.getName(this.context)));
                    i++;
                }
            }
            PieDataSet pieDataSet = new PieDataSet(arrayList, "");
            pieDataSet.setColors(arrayList2);
            PieData pieData = new PieData(pieDataSet);
            pieData.setDrawValues(false);
            pieChart.setData(pieData);
            return;
        }
        arrayList.add(new PieEntry(100.0f, "empty"));
        PieDataSet pieDataSet2 = new PieDataSet(arrayList, "");
        pieDataSet2.setColors(Helper.getAttributeColor(this.context, R.attr.progressBar));
        PieData pieData2 = new PieData(pieDataSet2);
        pieData2.setDrawValues(false);
        pieChart.setData(pieData2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SpannableString getCenterAmount(long amount, String text) {
        long j = 0;
        for (Stats stats : this.pieStatsList) {
            j += stats.getAmount();
        }
        String accountSymbol = SharePreferenceHelper.getAccountSymbol(this.context);
        if (j < 0) {
            amount = -amount;
        }
        String beautifyAmount = Helper.getBeautifyAmount(accountSymbol, amount);
        SpannableString spannableString = new SpannableString(text + "\n" + beautifyAmount);
        spannableString.setSpan(new RelativeSizeSpan(1.0f), 0, spannableString.length() - beautifyAmount.length(), 0);
        if (beautifyAmount.length() <= 10) {
            spannableString.setSpan(new RelativeSizeSpan(1.35f), spannableString.length() - beautifyAmount.length(), spannableString.length(), 0);
        } else if (beautifyAmount.length() <= 15) {
            spannableString.setSpan(new RelativeSizeSpan(1.175f), spannableString.length() - beautifyAmount.length(), spannableString.length(), 0);
        } else if (beautifyAmount.length() <= 20) {
            spannableString.setSpan(new RelativeSizeSpan(1.0f), spannableString.length() - beautifyAmount.length(), spannableString.length(), 0);
        } else {
            spannableString.setSpan(new RelativeSizeSpan(0.825f), spannableString.length() - beautifyAmount.length(), spannableString.length(), 0);
        }
        return spannableString;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SpannableString getCenterAmount() {
        long j = 0;
        for (Stats stats : this.pieStatsList) {
            j += stats.getAmount();
        }
        String beautifyAmount = Helper.getBeautifyAmount(SharePreferenceHelper.getAccountSymbol(this.context), j);
        SpannableString spannableString = new SpannableString(this.context.getResources().getString(R.string.expense) + "\n" + beautifyAmount);
        spannableString.setSpan(new RelativeSizeSpan(1.0f), 0, spannableString.length() - beautifyAmount.length(), 0);
        if (beautifyAmount.length() <= 10) {
            spannableString.setSpan(new RelativeSizeSpan(1.35f), spannableString.length() - beautifyAmount.length(), spannableString.length(), 0);
        } else if (beautifyAmount.length() <= 15) {
            spannableString.setSpan(new RelativeSizeSpan(1.175f), spannableString.length() - beautifyAmount.length(), spannableString.length(), 0);
        } else if (beautifyAmount.length() <= 20) {
            spannableString.setSpan(new RelativeSizeSpan(1.0f), spannableString.length() - beautifyAmount.length(), spannableString.length(), 0);
        } else {
            spannableString.setSpan(new RelativeSizeSpan(0.825f), spannableString.length() - beautifyAmount.length(), spannableString.length(), 0);
        }
        return spannableString;
    }
}
