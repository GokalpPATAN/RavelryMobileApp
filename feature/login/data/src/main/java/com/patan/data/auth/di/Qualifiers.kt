package com.patan.data.auth.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Authless   // Interceptor YOK → sadece oauth/token & public çağrılar

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Authed     // Interceptor VAR → uygulama API’leri