package com.naughtyenigma.intracranialmajor.api

import okio.IOException

class NoConnectivityException : IOException() {
    override val message: String
        get() = "网络断开连接，请检查网络"
}