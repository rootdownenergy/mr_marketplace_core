package com.rootdown.dev.notesapp.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.LogService
import com.rootdown.dev.notesapp.root.util.SnackbarManager
import com.rootdown.dev.notesapp.root.util.SnackbarMessage.Companion.toSnackbarMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class AppViewModel(private val logService: LogService) : ViewModel() {
    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    SnackbarManager.showMessage(throwable.toSnackbarMessage())
                }
                logService.logNonFatalCrash(throwable)
            },
            block = block
        )
}