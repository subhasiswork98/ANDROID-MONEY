package com.expance.manager.Database.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.expance.manager.Database.AppDatabaseObject;
import com.expance.manager.Model.Debt;
import com.expance.manager.Utility.SharePreferenceHelper;
import java.util.List;

/* loaded from: classes3.dex */
public class DebtViewModel extends AndroidViewModel {
    private LiveData<List<Debt>> debtList;

    public DebtViewModel(Application application) {
        super(application);
        AppDatabaseObject appDatabaseObject = AppDatabaseObject.getInstance(getApplication());
        this.debtList = appDatabaseObject.debtDaoObject().getAllDebt(SharePreferenceHelper.getAccountId(getApplication()), 0);
    }

    public LiveData<List<Debt>> getDebtList() {
        return this.debtList;
    }
}
