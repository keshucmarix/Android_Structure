package com.app.utils

import android.content.Context
import com.app.data.model.User
import com.google.gson.Gson
import javax.inject.Inject

class AppSession @Inject
constructor(
    private val appPreferences: AppPreferences,
    private val context: Context,
    override var apiKey: String
) : Session {

    private val gson: Gson = Gson()

    override var user: User? = null
        get() {
            if (field == null) {
                val userJSON = appPreferences.getString(USER_JSON)
                field = gson.fromJson(userJSON, User::class.java)
            }
            return field
        }
        set(value) {
            field = value
            val userJson = gson.toJson(value)
            if (userJson != null)
                appPreferences.putString(USER_JSON, userJson)
        }

    override var bearerToken: String?
        get() = appPreferences.getString(token)
        set(value) {
            appPreferences.putString(token, value ?: "check token setting")
        }


    override var userSession: String
        get() = appPreferences.getString(Session.USER_SESSION)
        set(userSession) = appPreferences.putString(Session.USER_SESSION, userSession)

    override var appLang: String
        get() = appPreferences.getString(Session.APP_LANG)
        set(value) = appPreferences.putString(Session.APP_LANG, value)


    override fun clearSession() {
        appPreferences.clearAll()
    }

    companion object {
        const val USER_JSON = "user_response"
        const val token = "token"
        const val staticToken =
            "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJDdXN0b21lcklkIjoiMjAyNDAiLCJqdGkiOiJlZTczMjBlZS05YWJkLTQ2NjQtOGFmZC03N2U4NDdiMzI0ZDUiLCJuYmYiOjE3MDAyMjEyMDMsImV4cCI6MTcxNTk0NjAwMywiaWF0IjoxNzAwMjIxMjAzLCJpc3MiOiJIZWFsdGhMYXlCeUlzVGhlSXNzdWVyIiwiYXVkIjoiSGVhbHRoTGF5QnlJc1RoZUF1ZGllbmNlIn0.g-APraoRzD9hnlQ84uZXQuqZs_JM3OirB_Di-w2LQqJ2VE5_8G53o88eEs2bv-qPR5D8YRnNbTs6WSce7UntKw"
    }
}
