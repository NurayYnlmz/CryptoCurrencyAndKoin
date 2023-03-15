package com.nurayyenilmez.koininjection.di

import com.nurayyenilmez.koininjection.repository.CryptoDownload
import com.nurayyenilmez.koininjection.repository.CryptoDownloadImpl
import com.nurayyenilmez.koininjection.service.CryptoAPI
import com.nurayyenilmez.koininjection.viewModel.CryptoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule= module {
        single {
            val BASE_URL = "https://raw.githubusercontent.com/"
           Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CryptoAPI::class.java)


        }

    single<CryptoDownload>{
        CryptoDownloadImpl(get())

    }
    viewModel {
        CryptoViewModel(get())
    }
}