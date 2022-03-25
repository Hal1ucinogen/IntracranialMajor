package com.naughtyenigma.intracranialmajor.data

import com.naughtyenigma.intracranialmajor.api.ApiClient
import com.naughtyenigma.intracranialmajor.api.BaseRepository
import com.naughtyenigma.intracranialmajor.api.PageResponse
import com.naughtyenigma.intracranialmajor.api.Result
import com.naughtyenigma.intracranialmajor.model.Match

class MatchRepository : BaseRepository() {

    suspend fun getMatchList(): Result<PageResponse<Match>> =
        processRequest { ApiClient.service.getUserMatches() }
}