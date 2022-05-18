package com.example.circularplayground.ui.detail

import androidx.lifecycle.viewModelScope
import com.example.circularplayground.DataRepository
import com.example.circularplayground.ui.common.ReduxViewModel
import com.example.circularplayground.utils.CoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
    private val dataRepository: DataRepository,
): ReduxViewModel<DetailViewState>(DetailViewState()) {

    init {
        onLoad()
    }

    private fun onLoad() {
        viewModelScope.launch(dispatchers.io) {
            dataRepository.getDetailData().collect {
                setState {
                    copy(
                        currentShortTermUtilisation = it.currentShortTermUtilisation,
                        changeInShortTerm = it.changeInShortTerm,
                        currentLongTermUtilisation = it.currentLongTermUtilisation,
                        changeInLongTerm = it.changeInLongTerm,
                        daysUntilNextReport = it.daysUntilNextReport,
                    )
                }
            }
        }
    }
}