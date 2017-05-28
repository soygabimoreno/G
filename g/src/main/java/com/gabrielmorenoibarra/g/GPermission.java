package com.gabrielmorenoibarra.g;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class contains static methods that uses permissions.
 * There is to include them on Manifest if they are called.
 * Created by Gabriel Moreno on 2017-05-27.
 */
public class GPermission {

    /**
     * Require GET_ACCOUNTS permission.
     * @param context Related context.
     * @return an <code>ArrayList</code> with all not repeated user's email addresses.
     */
    public static List<String> getAccountEmails(Context context) {
        List<String> emails = new ArrayList<>();
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(context).getAccounts();
        for (Account account : accounts) {
            if (pattern.matcher(account.name).matches()) {
                emails.add(account.name);
            }
        }
        return G.removeRepetitions(emails);
    }

    /**
     * This is not recommended.
     * Require READ_PHONE_STATE permission.
     * @param context Related context.
     * @return the device phone number if it has any or null if it is not available.
     */
    public static String getDevicePhoneNumber(Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
    }

    /**
     * This is not recommended.
     * Require READ_PHONE_STATE permission.
     * @param context Related context.
     * @return the device ID if it has any or null if it is not available.
     */
    public static String getDeviceID(Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }
}