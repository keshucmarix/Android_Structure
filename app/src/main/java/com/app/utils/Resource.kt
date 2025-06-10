package com.app.utils

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val exception: Exception?
) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg, null)
        }

        fun <T> exception(exception: Exception): Resource<T> {
            return Resource(
                status = Status.EXEPTION,
                data = null,
                message = null,
                exception = exception
            )
        }
    }

}