package com.expance.manager.Database.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.expance.manager.Database.AppDatabaseObject;
import com.expance.manager.Model.Trans;
import com.expance.manager.Utility.SharePreferenceHelper;

/* loaded from: classes3.dex */
public class TransactionDetailViewModel extends AndroidViewModel {
    private LiveData<Trans> trans;

    /* JADX INFO: Access modifiers changed from: package-private */
    public TransactionDetailViewModel(Application application, int transId) {
        super(application);
        AppDatabaseObject appDatabaseObject = AppDatabaseObject.getInstance(getApplication());
        this.trans = appDatabaseObject.transDaoObject().getLiveTransById(transId, SharePreferenceHelper.getAccountId(application));
    }

    public LiveData<Trans> getTrans() {
        return this.trans;
    }
}
