package com.pictogram.data.remote.pagination

import androidx.paging.*
import com.pictogram.utils.Constants.DEFAULT_PAGE_SIZE
import kotlinx.coroutines.flow.Flow

private val pagerConfig = PagingConfig(
   pageSize =  DEFAULT_PAGE_SIZE,
    enablePlaceholders = false,
    initialLoadSize = DEFAULT_PAGE_SIZE
)

fun <Key :Int, Value : Any> pagingFlow(pagingSource: () -> PagingSource<Key, Value>): Flow<PagingData<Value>> {
    return Pager(
        config = pagerConfig,
        pagingSourceFactory = { pagingSource() }
    ).flow
}