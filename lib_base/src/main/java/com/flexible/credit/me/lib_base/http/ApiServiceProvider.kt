package com.flexible.credit.me.lib_base.http

object ApiServiceProvider {
    val apiService: ApiService by lazy {
        RetrofitClient.create(ApiService::class.java)
    }
}