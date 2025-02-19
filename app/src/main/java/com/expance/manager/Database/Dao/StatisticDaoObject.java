package com.expance.manager.Database.Dao;

import com.expance.manager.Model.CalendarSummary;
import com.expance.manager.Model.DailyTrans;
import com.expance.manager.Model.Stats;
import com.expance.manager.Model.SubcategoryStats;
import com.expance.manager.Model.Trans;
import com.expance.manager.Model.WeeklyStats;
import java.util.List;

/* loaded from: classes3.dex */
public interface StatisticDaoObject {
    long getAccountBalance(int id, long date);

    List<DailyTrans> getAllOverviewTrans(int accountId);

    List<Stats> getAllPieStats(int accountId, int type);

    List<DailyTrans> getAllPieTrans(int accountId, int categoryId, int type);

    List<SubcategoryStats> getAllSubcategoryStats(int accountId, int categoryId);

    List<DailyTrans> getOverviewTrans(int accountId, long startDate, long endDate);

    List<Trans> getOverviewTransFromDate(int accountId, long startDate, long endDate);

    List<Stats> getPieStats(int accountId, long startDate, long endDate, int type);

    List<DailyTrans> getPieTrans(int accountId, int categoryId, long startDate, long endDate, int type);

    List<Trans> getPieTransFromDate(int accountId, int categoryId, long startDate, long endDate, int type);

    List<SubcategoryStats> getSubcategoryStats(int accountId, long startDate, long endDate, int categoryId);

    CalendarSummary getSummary(int accountId);

    CalendarSummary getSummary(long startDate, long endDate, int accountId);

    List<WeeklyStats> getWeeklyStat(int accountId, long startDate, long endDate);

    List<DailyTrans> getWeeklyTrans(int accountId, long startDate, long endDate);

    List<Trans> getWeeklyTransFromDate(int accountId, long startDate, long endDate);
}
