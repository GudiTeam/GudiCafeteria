package com.duzi.gudicafeteria_a.service

import retrofit2.Response

@Suppress("unused")
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(-1, error.message ?: "unknown error..")
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if(response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body)
                }
            } else {
                val msg = "error:(  ${response.code()} \n ${response.errorBody()?.string()} )"
                ApiErrorResponse(response.code(), msg)
            }
        }

        fun <T> create(response: T): ApiResponse<T> {
            return ApiSuccessResponse(response)
        }
    }
}

class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T): ApiResponse<T>()

data class ApiErrorResponse<T>(val code: Int, val errorMessage: String): ApiResponse<T>()