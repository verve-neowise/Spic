package com.neowise.spic.api

import com.google.gson.Gson
import okhttp3.*

class HttpClient {
    private val client: OkHttpClient = OkHttpClient()
    private val gson: Gson = Gson()

    fun post(url: String, body: Any? = null, token: String? = null): Response {
        println("url: $url")

        val builder = Request.Builder().url(url)
        val json = if (body != null) gson.toJson(body) else "{}"
        builder.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json))

        if (token != null) {
            builder.addHeader("Authorization", "$token")
        }
        val request = builder.build()
        try {
            return client.newCall(request).execute()
        }
        catch(e: Exception) {
            throw e
//            throw ApiException("Cannot do request. Check connection.")
        }
    }

    fun put(url: String, body: Any? = null, token: String? = null): Response {
        println("url: $url")
        val builder = Request.Builder().url(url)
        val json = if (body != null) gson.toJson(body) else "{}"
        builder.put(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json))

        if (token != null) {
            builder.addHeader("Authorization", "$token")
        }
        val request = builder.build()
        try {
            return client.newCall(request).execute()
        }
        catch(e: Exception) {
            throw ApiException("Cannot do request. Check connection.")
        }
    }

    fun delete(url: String, token: String? = null): Response {
        println("url: $url")

        val builder = Request.Builder().url(url).delete()

        if (token != null) {
            builder.addHeader("Authorization", "$token")
        }
        val request = builder.build()
        try {
            return client.newCall(request).execute()
        }
        catch(e: Exception) {
            throw ApiException("Cannot do request. Check connection.")
        }
    }

    fun get(url: String, token: String? = null): Response {
        println("url: $url")
        val builder = Request.Builder().url(url).get()

        if (token != null) {
            builder.addHeader("Authorization", "$token")
        }
        val request = builder.build()
        try {
            return client.newCall(request).execute()
        }
        catch(e: Exception) {
            throw ApiException("Cannot do request. Check connection.")
        }
    }

}