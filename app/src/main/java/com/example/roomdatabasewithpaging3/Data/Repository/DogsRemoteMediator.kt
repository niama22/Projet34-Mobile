package com.example.roomdatabasewithpaging3.Data.Repository

import androidx.paging.*
import com.example.roomdatabasewithpaging3.Data.Database.Database
import com.example.roomdatabasewithpaging3.Data.Dogs
import com.example.roomdatabasewithpaging3.Network.ApiService
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagingApi::class)
class DogsRemoteMediator(
    private val database: Database,
    private val apiService: ApiService
) : RemoteMediator<Int, Dogs>() {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Dogs>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {

                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    val key = state.lastItemOrNull()?.id

                    (key?.toInt() ?: 1) + 1
                }
            }


            val dogs = apiService.getAllDogs(page, state.config.pageSize)


            database.getDao().insertAll(dogs)

            MediatorResult.Success(endOfPaginationReached = dogs.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }


}