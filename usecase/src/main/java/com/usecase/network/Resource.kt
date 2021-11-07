package com.usecase.network


data class Resource<out T>(
    @Status val status: Int,
    val message: String? = null,
    val validation_message: String? = null,
    val data: T? = null,
    val pagination: BaseRsm.Pagination? = null,
      val      exception: Exception?=null
) {

    companion object {

        fun loading(): Resource<Nothing> = Resource(LOADING)

        fun <T> success(
            data: T?,
            message: String = "",
            pagination: BaseRsm.Pagination? = null
        ): Resource<T> =
            Resource(status = SUCCESS, data = data, message = message, pagination = pagination)

        fun failure(msg: String): Resource<Nothing> = Resource(status = ERROR, message = msg)
        fun unauthorized(msg: String): Resource<Nothing> = Resource(status = LogIn, message = msg)
        fun failureConnect(e: Exception): Resource<Nothing> = Resource(status = ERRORNet, exception = e)
        fun serverMessage(msg: String): Resource<Nothing> = Resource(status = serverMsg, message = msg)
    }
}
