package com.filippo.either.login.data

import arrow.core.raise.either
import com.filippo.either.common.domain.ApiResponse
import com.filippo.either.common.domain.SessionWriter
import com.filippo.either.common.domain.apiCall
import com.filippo.either.login.data.remote.SessionApi
import com.filippo.either.login.data.remote.model.SessionRequest
import com.filippo.either.login.data.remote.model.ValidateTokenRequest
import com.filippo.either.login.domain.Credentials
import com.filippo.either.login.domain.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: SessionApi,
    private val sessionWriter: SessionWriter,
) : LoginRepository {

    override suspend fun login(credentials: Credentials): ApiResponse<Unit> = either {
        val token = apiCall { api.createToken() }.bind()

        val validatedToken = apiCall {
            api.validateToken(
                ValidateTokenRequest(
                    requestToken = token.requestToken,
                    username = credentials.username.value,
                    password = credentials.password.value
                )
            )
        }.bind()

        val session = apiCall {
            api.createSession(
                SessionRequest(token = validatedToken.requestToken)
            )
        }.bind()

        sessionWriter.saveSessionId(session.sessionId.value)
    }
}
