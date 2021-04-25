package com.thanht.domain.base

data class BaseResult<out T> (val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T? = null): BaseResult<T> {
            return BaseResult(Status.SUCCESS, data, "")
        }

        fun <T> error(msg: String): BaseResult<T> {
            return BaseResult(Status.ERROR, null, msg)
        }

        fun <T> empty(msg: String): BaseResult<T> {
            return BaseResult(Status.EMPTY, null, msg)
        }

        fun <T> loading(msg: String): BaseResult<T> {
            return BaseResult(Status.LOADING, null, msg)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    EMPTY
}