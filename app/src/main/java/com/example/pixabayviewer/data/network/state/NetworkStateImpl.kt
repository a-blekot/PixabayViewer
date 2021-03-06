package com.example.pixabayviewer.data.network.state

import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import androidx.lifecycle.MutableLiveData

class NetworkStateImpl : NetworkState {
    override val isConnected: MutableLiveData<Boolean> = MutableLiveData(false)
    override var network: Network? = null
    override var linkProperties: LinkProperties? = null
    override var networkCapabilities: NetworkCapabilities? = null
}