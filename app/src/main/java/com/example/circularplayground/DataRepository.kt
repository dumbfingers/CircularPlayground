package com.example.circularplayground

import com.example.circularplayground.data.Resource
import com.example.circularplayground.data.network.CircularService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val circularService: CircularService,
    private val mapper: NetworkMapper
) {
    fun getCreditInfo(): Flow<Resource<AccountStatus>> {
        return flow {
            emit(Resource(Resource.Status.LOADING))
            val response = circularService.getAccountStatus()
            val accountDto = response.body()
            emit(
                if (response.isSuccessful && accountDto != null) {
                    Resource(Resource.Status.SUCCESS, data = mapper.mapToModel(accountDto))
                } else {
                    Resource(Resource.Status.ERROR, error = Throwable("Error: ${response.code()}"))
                }
            )
        }
    }
}