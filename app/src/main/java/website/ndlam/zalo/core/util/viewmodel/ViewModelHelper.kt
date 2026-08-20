package website.ndlam.zalo.core.util.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

@Composable
inline fun <reified T : ViewModel> initViewModelWithDependencies(vararg dependencies: Any): T {
    val clazz = T::class.java

    var instance: T? = null
    for (constructor in clazz.constructors) {
        try {
            instance = constructor.newInstance(*dependencies) as T?
            break
        } catch (_: Exception) {

        }
    }

    if (instance == null) throw Exception("Don't have any constructor map with list dependency!")

    return viewModel<T>(
        factory = viewModelFactory {
            initializer {
                instance
            }
        }
    )
}