package com.github.purofle.nmsl.ui.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext

class Login(
    private val componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val _model = mutableStateOf(Model())
    val model: State<Model> = _model

    fun onAddClicked() {
        changeState { copy(account = account) }
    }

    private inline fun changeState(reducer: Model.() -> Model): Model {
        val newModel = model.value.reducer()
        _model.value = newModel
        return newModel
    }

    data class Model(
        val account: AccountDTO = AccountDTO("", "")
    )
}