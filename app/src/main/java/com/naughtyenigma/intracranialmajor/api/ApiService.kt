package com.naughtyenigma.intracranialmajor.api

import com.naughtyenigma.intracranialmajor.model.Match
import com.naughtyenigma.intracranialmajor.model.User
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    /**
     * Get user info
     **/
    @FormUrlEncoded
    @POST("userInfo")
    suspend fun getUserInfo(
        @Field("token") token: String = "380f684d2b3476d1032eb3079e9375e8",
    ): ApiResponse<User>

    /**
     * Get match list
     **/
    @FormUrlEncoded
    @POST("record_list")
    suspend fun getUserMatches(
        @Field("token") token: String = "380f684d2b3476d1032eb3079e9375e8",
        @Field("pageNum") pageNum: Int = 1,
        @Field("pageSize") pageSize: Int = 1000
    ): ApiResponse<PageResponse<Match>>
}
