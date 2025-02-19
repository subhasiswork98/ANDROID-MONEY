package com.expance.manager.Database.Dao;

import androidx.lifecycle.LiveData;
import com.expance.manager.Database.Entity.MediaEntity;
import com.expance.manager.Database.Entity.TransEntity;
import com.expance.manager.Model.DailyTrans;
import com.expance.manager.Model.SearchTrans;
import com.expance.manager.Model.Trans;
import java.util.List;

/* loaded from: classes3.dex */
public interface TransDaoObject {
    void deleteMedia(String path);

    void deleteTrans(int id);

    void deleteTransMedia(int id);

    LiveData<List<DailyTrans>> getDailyTrans(int accountId);

    LiveData<Trans> getLiveTransById(int id, int accountId);

    Trans getTransById(int id, int accountId);

    TransEntity getTransEntityById(int id);

    Trans[] getTransFromDate(int accountId, long startDate, long endDate);

    int getTransferTrans(int id);

    void insertMedia(MediaEntity mediaEntity);

    long insertTrans(TransEntity transEntity);

    void removeSubcategory(int id);

    List<SearchTrans> searchAllTrans(int accountId);

    List<SearchTrans> searchAllTrans(int accountId, long startDate, long endDate);

    List<SearchTrans> searchAllTrans(String note, int accountId);

    List<SearchTrans> searchAllTrans(String note, int accountId, long startDate, long endDate);

    void updateTrans(TransEntity transEntity);
}
