package com.furkanekiz.retrofitkotlin.service

import com.furkanekiz.retrofitkotlin.model.CryptoModel
import io.reactivex.Observable
//import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {
    //GET, POST, UPDATE, DELETE

    //https://api.nomics.com/v1/currencies/ticker?key=YOUR_API_KEY

    @GET("currencies/ticker?key=YOUR_API_KEY")
    //fun getData(): Call<List<CryptoModel>>
    fun getData(): Observable<List<CryptoModel>>
}