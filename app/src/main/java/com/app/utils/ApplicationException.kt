package com.app.utils

class ApplicationException(override val message: String) : Throwable() {
    var type: Type? = null


    enum class Type {
        NO_INTERNET, NO_DATA, VALIDATION
    }
}