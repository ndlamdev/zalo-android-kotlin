package website.ndlam.zalo.ui.feature.regioncode

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import website.ndlam.zalo.ui.common.textfield.viewmodel.LocalTextFieldViewModel

class RegionCodeViewModel : LocalTextFieldViewModel() {
    private var rootDataRegionCodes: Map<String, List<RegionCode>> = mapOf()
    private val _regionCodes = MutableStateFlow<Map<String, List<RegionCode>>>(mapOf())
    val regionCodes = _regionCodes.asStateFlow()


    fun loadRegionCode(
        context: Context
    ) {
        viewModelScope.launch {
            context.assets.open("MapCountryCodes.json").use { inputStream ->
                rootDataRegionCodes =
                    Gson().fromJson(
                        inputStream.bufferedReader(),
                        object : TypeToken<Map<String, List<RegionCode>>>() {}.type
                    )

                _regionCodes.value = rootDataRegionCodes
            }
        }
    }

    override fun setValue(text: String) {
        if (text.isEmpty()) {
            _regionCodes.value = rootDataRegionCodes
        } else {
            _regionCodes.value = rootDataRegionCodes
                .mapValues { (_, list) ->
                    list.filter { item -> item.name.contains(text, true) }
                }.filterValues { it.isNotEmpty() }
        }

        super.setValue(text)
    }

    @Serializable
    data class RegionCode(
        val name: String,
        @SerializedName("dial_code")
        val dialCode: String?,
        val code: String?
    ) : Comparable<RegionCode> {
        override fun compareTo(other: RegionCode): Int {
            return this.name.compareTo(other.name)
        }
    }
}