package com.nurayyenilmez.koininjection.repository

import com.nurayyenilmez.koininjection.model.CryptoModel
import com.nurayyenilmez.koininjection.service.CryptoAPI
import com.nurayyenilmez.koininjection.util.Resource

class CryptoDownloadImpl(private val api: CryptoAPI):CryptoDownload {
    override suspend fun downloadCryptos(): Resource<List<CryptoModel>> {
         return try {
             val response = api.getData()
             if (response.isSuccessful) {
                 response.body()?.let {
                     return@let Resource.success(it)
                 } ?: Resource.error("Error",null)
             } else {
                 Resource.error("Error",null)
             }
         } catch (e: Exception) {
             Resource.error("No data!",null)
         }


    }


}