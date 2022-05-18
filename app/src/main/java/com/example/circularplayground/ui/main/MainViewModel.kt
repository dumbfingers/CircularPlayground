package com.example.circularplayground.ui.main

import androidx.lifecycle.viewModelScope
import com.example.circularplayground.data.DataRepository
import com.example.circularplayground.ui.common.ReduxViewModel
import com.example.circularplayground.data.Resource
import com.example.circularplayground.utils.CoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val dispatchers: CoroutineDispatchers,
): ReduxViewModel<MainViewState>(MainViewState()) {

    init {
        onLoad()
    }

    private fun onLoad() {
        viewModelScope.launch(dispatchers.io) {
            dataRepository.getAccountStatus().collect {
                when (it.status) {
                    Resource.Status.LOADING -> setState {
                        copy(isLoading = true, error = "")
                    }
                    Resource.Status.SUCCESS -> {
                        val creditInfo = it.data!!.creditReportInfo
                        setState {
                            copy(
                                isLoading = false,
                                currentScore = creditInfo.score,
                                totalScore = creditInfo.maxScoreValue,
                                error = ""
                            )
                        }
                    }
                    Resource.Status.ERROR -> {
                        val error = it.error!!.message ?: ""
                        setState {
                            copy(
                                isLoading = false,
                                error = error
                            )
                        }
                    }
                }
            }
        }
    }
}
