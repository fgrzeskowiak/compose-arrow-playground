package com.filippo.either.account.domain

import com.filippo.either.common.domain.SessionWriter
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val sessionWriter: SessionWriter,
) {
    suspend operator fun invoke() {
        sessionWriter.clearSession()
    }
}
