package com.android.settings.spa.app.appinfo

import android.content.pm.ApplicationInfo
import android.content.pm.GosPackageState
import android.content.pm.GosPackageStateFlag
import android.ext.cscopes.ContactScopesApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.android.settings.R
import com.android.settingslib.spa.widget.preference.Preference
import com.android.settingslib.spa.widget.preference.PreferenceModel
import com.android.settingslib.spaprivileged.model.app.userHandle
import com.android.settingslib.spaprivileged.model.app.userId

@Composable
fun AppContactScopesPreference(app: ApplicationInfo) {
    val pkgName = app.packageName
    if (!GosPackageState.get(pkgName, app.userId).hasFlag(GosPackageStateFlag.CONTACT_SCOPES_ENABLED)) {
        return
    }

    val context = LocalContext.current

    Preference(object : PreferenceModel {
        override val title = stringResource(R.string.contact_scopes)
        override val onClick = {
            val intent = ContactScopesApi.createConfigActivityIntent(pkgName)
            context.startActivityAsUser(intent, app.userHandle)
        }
    })
}
