package com.example.circularplayground

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.circularplayground.data.Resource
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

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "creditInfo")

@Singleton
class DataRepository @Inject constructor(
    private val circularService: CircularService,
    private val mapper: NetworkMapper,
    private val context: Context
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

    suspend fun saveDetailData(creditInfo: CreditInfo) {
        context.dataStore.edit { pref ->
            pref[KEY_CURRENT_SHORT_TERM_UTIL] = creditInfo.currentShortTermCreditUtilisation ?: 0
            pref[KEY_CHANGE_IN_SHORT_TERM] = creditInfo.changeInShortTermDebt ?: 0
            pref[KEY_CURRENT_LONG_TERM_UTIL] = creditInfo.currentLongTermCreditUtilisation ?: 0
            pref[KEY_CHANGE_IN_LONG_TERM] = creditInfo.changeInLongTermDebt ?: 0
            pref[KEY_DAYS_UNTIL_NEXT_REPORT] = creditInfo.daysUntilNextReport
        }
    }

    fun getDetailData(): Flow<DetailData> {
        return context.dataStore.data.map { pref ->
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
