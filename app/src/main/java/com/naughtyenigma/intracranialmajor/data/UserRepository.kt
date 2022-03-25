package com.naughtyenigma.intracranialmajor.data

import com.naughtyenigma.intracranialmajor.api.ApiClient
import com.naughtyenigma.intracranialmajor.api.BaseRepository
import com.naughtyenigma.intracranialmajor.api.Result
import com.naughtyenigma.intracranialmajor.model.User

class UserRepository : BaseRepository() {

    suspend fun getUserInfo(): Result<User> =
        processRequest { ApiClient.service.getUserInfo() }
}