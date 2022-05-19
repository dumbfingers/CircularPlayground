package com.example.circularplayground.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.circularplayground.data.model.AccountStatus
import com.example.circularplayground.data.model.CreditInfo
import com.example.circularplayground.data.model.DetailData
import com.example.circularplayground.data.network.CircularService
import com.example.circularplayground.utils.Constants.KEY_CHANGE_IN_LONG_TERM
import com.example.circularplayground.utils.Constants.KEY_CHANGE_IN_SHORT_TERM
import com.example.circularplayground.utils.Constants.KEY_CURRENT_LONG_TERM_UTIL
import com.example.circularplayground.utils.Constants.KEY_CURRENT_SHORT_TERM_UTIL
import com.example.circularplayground.utils.Constants.KEY_DAYS_UNTIL_NEXT_REPORT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val circularService: CircularService,
    private val mapper: NetworkMapper,
    private val dataStore: DataStore<Preferences>
) {
    fun getAccountStatus(): Flow<Resource<AccountStatus>> {
        return flow {
            emit(Resource(Resource.Status.LOADING))
            val response = circularService.getAccountStatus()
            val accountDto = response.body()
            emit(
                if (response.isSuccessful && accountDto != null) {
                    val model = mapper.mapToModel(accountDto)
                    saveDetailData(model.creditReportInfo)
                    Resource(Resource.Status.SUCCESS, data = model)
                } else {
                    Resource(Resource.Status.ERROR, error = Throwable("Error: ${response.code()}"))
                }
            )
        }
    }

    private suspend fun saveDetailData(creditInfo: CreditInfo) {
        dataStore.edit { pref ->
            pref[KEY_CURRENT_SHORT_TERM_UTIL] = creditInfo.currentShortTermCreditUtilisation ?: 0
            pref[KEY_CHANGE_IN_SHORT_TERM] = creditInfo.changeInShortTermDebt ?: 0
            pref[KEY_CURRENT_LONG_TERM_UTIL] = creditInfo.currentLongTermCreditUtilisation ?: 0
            pref[KEY_CHANGE_IN_LONG_TERM] = creditInfo.changeInLongTermDebt ?: 0
            pref[KEY_DAYS_UNTIL_NEXT_REPORT] = creditInfo.daysUntilNextReport
        }
    }

    fun getDetailData(): Flow<DetailData> {
        return dataStore.data.map { pref ->
            DetailData(
                currentShortTermUtilisation = pref[KEY_CURRENT_SHORT_TERM_UTIL],
                changeInShortTerm = pref[KEY_CHANGE_IN_SHORT_TERM],
                currentLongTermUtilisation = pref[KEY_CURRENT_LONG_TERM_UTIL],
                changeInLongTerm = pref[KEY_CHANGE_IN_LONG_TERM],
                daysUntilNextReport = pref[KEY_DAYS_UNTIL_NEXT_REPORT]
            )
        }
    }
}
