package website.ndlam.zalo.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import website.ndlam.zalo.convert.serializer.SettingsSerializer
import website.ndlam.zalo.domains.dto.Settings

class SettingRepository(val context: Context) {
    val Context.dataStore: DataStore<Settings> by dataStore(
        fileName = "settings.json",
        serializer = SettingsSerializer,
    )

}