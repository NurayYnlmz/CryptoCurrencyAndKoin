package com.nurayyenilmez.koininjection.repository

import com.nurayyenilmez.koininjection.model.CryptoModel
import com.nurayyenilmez.koininjection.service.CryptoAPI
import com.nurayyenilmez.koininjection.util.Resource

interface CryptoDownload {

    suspend fun downloadCryptos():Resource<List<CryptoModel>>
}