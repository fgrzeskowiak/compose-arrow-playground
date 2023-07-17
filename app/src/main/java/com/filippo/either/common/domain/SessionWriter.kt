package com.filippo.either.common.domain

interface SessionWriter {
    suspend fun saveSessionId(sessionId: String)
    suspend fun clearSession()
}
