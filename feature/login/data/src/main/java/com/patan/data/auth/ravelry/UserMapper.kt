package com.patan.data.auth.ravelry

import com.patan.domain.auth.model.User

fun RUser.toDomain(): User = User(
    id = id,
    username = username
)