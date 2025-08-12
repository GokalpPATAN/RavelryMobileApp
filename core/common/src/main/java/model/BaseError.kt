package com.patan.core.common.model

import com.google.gson.annotations.SerializedName

data class BaseError(
    @SerializedName("message")
    val errorMessage: String = "",
    val statusCode: Int = 500,
) : Throwable(errorMessage) {

    override val message: String
        get() = errorMessage
}