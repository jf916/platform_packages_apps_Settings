package com.android.settings.notification.zen;

import android.content.Context;
import android.ext.settings.ExtSettings;

import com.android.settings.ext.BoolSettingPrefController;

public class VibrateZenPreferenceController extends BoolSettingPrefController {

    public VibrateZenPreferenceController(Context ctx, String key) {
        super(ctx, key, ExtSettings.ENABLE_VIBRATE_ON_DO_NOT_DISTURB);
    }
}
