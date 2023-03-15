package com.nurayyenilmez.koininjection.service

import com.nurayyenilmez.koininjection.model.CryptoModel
import retrofit2.Response
import retrofit2.http.GET

interface CryptoAPI {
    // BASE_URL -> https://raw.githubusercontent.com/
    // atilsamancioglu/K21-JSONDataSet/master/crypto.json

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")

    suspend fun getData():Response<List<CryptoModel>>
}