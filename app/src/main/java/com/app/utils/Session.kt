package com.app.utils

import com.app.data.model.User

interface Session {
    var apiKey: String
    var bearerToken: String?
    var userSession: String
    var user: User?
    var appLang: String

    fun clearSession()

    companion object {
        const val USER_SESSION = "token"
        const val APP_LANG = "app_lang"

    }
}
