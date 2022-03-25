package com.naughtyenigma.intracranialmajor.api

class BizException(val code: Int, message: String) : IllegalStateException(message)