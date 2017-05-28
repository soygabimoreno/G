package com.gabrielmorenoibarra.g;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class contains static methods that use permissions.
 * There is to include them on Manifest if they are called.
 * Created by Gabriel Moreno on 2017-05-27.
 */
public class GPermission {

    /**
     * Require permission: GET_ACCOUNTS.
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
     * Require permission: READ_CONTACTS.
     * @param context Application context to ensure it is enabled.
     * @return all the emails from contact provider.
     */
    public static ArrayList<String> getEmails(Context context) {
        final String TAG = Thread.currentThread().getStackTrace()[2].getMethodName();
        ArrayList<String> emails = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor cur1 = cr.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                        new String[]{id}, null);
                if (cur1 != null) {
                    while (cur1.moveToNext()) {
//                        String name = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)); // To get the name
                        String email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        Log.d(TAG, "email: " + email);
                        if (email != null) {
                            emails.add(email);
                        }
                    }
                    cur1.close();
                }
            }
            cursor.close();
        }
        return emails;
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