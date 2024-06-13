package com.android.settings.jOS;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.VisibleForTesting;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

public class jOSSoundLinkPreferenceController extends BasePreferenceController {

    private static final String TAG = "jSoundLinkCtrl";

    private static final String jOS_PACKAGE_NAME = "jOS.System";
    private static final String SOUND_ACTIVITY_CLASS =
            jOS_PACKAGE_NAME + ".AudioConfigActivity";

    public jOSSoundLinkPreferenceController(Context context, String key) {
        super(context, key);
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        final Intent intent = new Intent(Intent.ACTION_MAIN)
                 .setClassName(jOS_PACKAGE_NAME, SOUND_ACTIVITY_CLASS);
        try {
            Log.i(TAG, "Attempting to start activity " + intent.toString());
            mContext.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "Unable to start activity " + intent.toString());
            Log.e(TAG, e.toString());
        }
        return true;
    }
}
