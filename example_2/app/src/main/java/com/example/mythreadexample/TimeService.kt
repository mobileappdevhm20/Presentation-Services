package com.example.mythreadexample

import retrofit2.Call
import retrofit2.http.GET

interface TimeService {

    // type returned by api
    data class CurrentTime(
        val UnixTime: Long,
        val TimeString: String
    )

    // api call to get the current time
    @GET("/myApiEndpoint")
    fun getTime(): Call<CurrentTime>
}