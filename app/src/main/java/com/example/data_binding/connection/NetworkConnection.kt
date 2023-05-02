package com.example.data_binding.connection

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.LiveData

class NetworkConnection(private val cManager: ConnectivityManager) : LiveData<Boolean>() {

    constructor(context: Context) : this(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)

    private val networkCallback = object : ConnectivityManager.NetworkCallback(){
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            postValue(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            postValue(false)
        }
    }

    override fun onActive() {
        super.onActive()
        val networkRequest = NetworkRequest.Builder().build()
        cManager.registerNetworkCallback(networkRequest,networkCallback)
    }

    override fun onInactive() {
        super.onInactive()
        cManager.unregisterNetworkCallback(networkCallback)
    }
}