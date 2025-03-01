package com.android.settings.network;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifPreferenceLayout;

/**
 * UI for Mobile network and Wi-Fi network setup.
 */
public class NetworkProviderSetup extends NetworkProviderSettings {
    public static final String EXTRA_SETUP_WIZARD_MODE_WIFI = "setup_wizard_mode_wifi";
    public static final String EXTRA_SETUP_WIZARD_TITLE = "setup_wizard_title";
    public static final String EXTRA_SETUP_WIZARD_DESCRIPTION = "setup_wizard_description";
    private static final String PREF_KEY_CONNECTED_ETHERNET_NETWORK = "connected_ethernet_network";
    private static final String EXTRA_PREFS_SET_SKIP_TEXT = "extra_prefs_set_skip_text";

    /**
     * Don't show media other than wifi, even if they might be available.
     */
    private boolean isSetupWizardModeWifi;
    private FooterButton nextButton;
    private FooterButton skipButton;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        isSetupWizardModeWifi = getIntent().getBooleanExtra(
                EXTRA_SETUP_WIZARD_MODE_WIFI, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = getActivity();
        if (activity == null) return;
        GlifPreferenceLayout layout = (GlifPreferenceLayout) view;
        layout.setDividerInsets(Integer.MAX_VALUE, 0);
        if (isSetupWizardModeWifi) {
            layout.setIcon(activity.getDrawable(R.drawable.baseline_wifi_glif));
            Intent intent = activity.getIntent();
            layout.setHeaderText(intent.getStringExtra(EXTRA_SETUP_WIZARD_TITLE));
            layout.setDescriptionText(intent.getStringExtra(EXTRA_SETUP_WIZARD_DESCRIPTION));
        }
        FooterBarMixin mixin = layout.getMixin(FooterBarMixin.class);
        // setup next button
        nextButton = new FooterButton.Builder(activity)
                .setButtonType(FooterButton.ButtonType.NEXT)
                .setTheme(com.google.android.setupdesign.R.style.SudGlifButton_Primary)
                .setText(R.string.next_label)
                .build();
        mixin.setPrimaryButton(nextButton);
        nextButton.setOnClickListener(v -> finish());
        // setup skip button
        skipButton = new FooterButton.Builder(activity)
                .setButtonType(FooterButton.ButtonType.SKIP)
                .setTheme(com.google.android.setupdesign.R.style.SudGlifButton_Secondary)
                .setText(R.string.skip_label)
                .build();
        String buttonText = getIntent().getStringExtra(EXTRA_PREFS_SET_SKIP_TEXT);
        if (!TextUtils.isEmpty(buttonText)) {
            skipButton.setText(buttonText);
        }
        mixin.setSecondaryButton(skipButton);
        skipButton.setOnClickListener(v -> finish());
    }

    @Override
    public void finish() {
        Activity activity = getActivity();
        if (activity == null) return;
        activity.setResult(Activity.RESULT_OK);
        activity.finish();
    }

    @Override
    void changeNextButtonState(boolean enabled) {
        nextButton.setEnabled(enabled);
        skipButton.setVisibility(enabled ? View.GONE : View.VISIBLE);
    }

    @Override
    public RecyclerView onCreateRecyclerView(LayoutInflater inflater, ViewGroup parent,
            Bundle savedInstanceState) {
        GlifPreferenceLayout layout = (GlifPreferenceLayout) parent;
        return layout.onCreateRecyclerView(inflater, parent, savedInstanceState);
    }

    private static void maybeSetVisible(@Nullable Preference p, boolean visible) {
        if (p != null) {
            p.setVisible(visible);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        maybeSetVisible(mConfigureWifiSettingsPreference, false);
        maybeSetVisible(mDataUsagePreference, false);
        maybeSetVisible(mAirplaneModeMsgPreference, false);
        maybeSetVisible(mResetInternetPreference, false);
        if (isSetupWizardModeWifi) {
            maybeSetVisible(findPreference(PREF_KEY_CONNECTED_ETHERNET_NETWORK), false);
            maybeSetVisible(findPreference(PREF_KEY_PROVIDER_MOBILE_NETWORK), false);
            if (mNetworkMobileProviderController != null) {
                mNetworkMobileProviderController.hidePreference(true, false);
            }
        }
    }

    @Override
    protected void setProgressBarVisible(boolean visible) {
        GlifPreferenceLayout glif = (GlifPreferenceLayout) getView();
        if (glif == null) return;
        glif.setProgressBarShown(visible);
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        // do nothing
    }

    @Override
    public void scrollToPreference(String key) {
        // do nothing
    }

    @Override
    public void scrollToPreference(Preference preference) {
        // do nothing
    }

    @Override
    public View setPinnedHeaderView(int layoutResId) {
        return null; // result unused
    }

    @Override
    public void setLoading(boolean loading, boolean animate) {
        // do nothing
    }
}
