package com.naughtyenigma.intracranialmajor.api

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

open class BaseRepository {

    suspend fun <T : Any> processRequest(
        requestBlock: suspend CoroutineScope.() -> ApiResponse<T>
    ): Result<T> {
        try {
            val response = coroutineScope {
                requestBlock()
            }
            return when (response.code) {
                ApiConstant.RESPONSE_CODE_SUCCESS -> {
                    if (response.data != null) {
                        Result.Success(data = response.data)
                    } else {
                        Result.Error(Exception(response.msg))
                    }
                }
                else -> {
                    Result.Error(BizException(response.code, response.msg.orEmpty()))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Error(e)
        }
    }
}