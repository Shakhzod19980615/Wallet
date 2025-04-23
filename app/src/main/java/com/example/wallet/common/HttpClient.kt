package com.example.wallet.common

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val httpClient = HttpClient {
        defaultRequest {
            header(ApiConstants.HEADER_PHONE_KEY, ApiConstants.DEFAULT_PHONE)
            contentType(ContentType.Application.Json)
        }
        install(ContentNegotiation) {
            json(
                Json{
                    ignoreUnknownKeys = true
                }
            ) // kotlinx.serialization
        }
    }

