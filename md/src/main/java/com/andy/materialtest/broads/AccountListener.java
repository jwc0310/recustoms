package com.andy.materialtest.broads;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AccountListener extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (intent.getAction().equals(AccountManager.LOGIN_ACCOUNTS_CHANGED_ACTION)) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.e("AccountListener", intent.toString());
                    Log.e("AccountListener", intent.toString());

                    AccountManager accountManager = AccountManager.get(context);
                    Account[] accounts = accountManager.getAccounts();
                    for (Account account : accounts) {
                        Log.e("AccountListener", account.toString());
                    }
                }
            }).start();
        }
    }
}
