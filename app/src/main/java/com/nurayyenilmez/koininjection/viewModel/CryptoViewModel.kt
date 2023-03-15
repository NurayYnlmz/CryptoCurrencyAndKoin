package com.nurayyenilmez.koininjection.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nurayyenilmez.koininjection.model.CryptoModel
import com.nurayyenilmez.koininjection.repository.CryptoDownload
import com.nurayyenilmez.koininjection.service.CryptoAPI
import com.nurayyenilmez.koininjection.util.Resource

import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CryptoViewModel(private val cryptoDownloadRepository:CryptoDownload):ViewModel() {

     val cryptpList=MutableLiveData<Resource<List<CryptoModel>>>()
     val cryptoError=MutableLiveData<Resource<Boolean>>()
     val cryptoLoading=MutableLiveData<Resource<Boolean>>()
    private var job : Job? = null

    val exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
        cryptoError.value=Resource.error(throwable.localizedMessage ?: "error",data = true)
    }

    fun getDataFromAP(){
        cryptoLoading.value=Resource.loading(data = true)


        //viewmodel scope da kullanabiliriz
      //  viewModelScope.launch (Dispatchers.IO+exceptionHandler){ }

        job= CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val resource=cryptoDownloadRepository.downloadCryptos()
            withContext(Dispatchers.Main){
                cryptpList.value=resource
                cryptoLoading.value=Resource.loading(data = false)
                cryptoError.value=Resource.error("",data = false)

                    }
                }
        }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
    }
