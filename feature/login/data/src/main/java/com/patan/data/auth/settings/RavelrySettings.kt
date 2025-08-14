package com.patan.data.auth.settings

data class RavelrySettings(
    val clientId: String,
    val clientSecret: String,
    val baseUrl: String = "https://www.ravelry.com/", // oauth için
    val apiBaseUrl: String = "https://www.ravelry.com/" // API için (projendeki mevcut tabanı koy)
)