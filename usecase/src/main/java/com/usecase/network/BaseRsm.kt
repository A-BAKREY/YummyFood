package com.usecase.network

import com.google.gson.annotations.SerializedName

/**

 */
data class BaseRsm<T>(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: T?,
    @SerializedName("Status")
    val statusbase:StatusBase,
    @SerializedName("pagination")
    val pagination: Pagination?
) {

  data class Pagination(
      @SerializedName("total")
      val total: Int,
      @SerializedName("currentPage")
      val currentPage: Int,
      @SerializedName("lastPage")
      val lastPage: Int,
      @SerializedName("perPage")
      val perPage: Int
  )
}
data class  BaseModel<T>(
    @SerializedName("status")
    val status:StatusBase,
    val data:T?,
    @SerializedName("pagination")
    val pagination: BaseRsm.Pagination?

)
data class StatusBase(
    val code: Int,
    var messages: String,
    val status: Boolean,
    val validation_message: String

)