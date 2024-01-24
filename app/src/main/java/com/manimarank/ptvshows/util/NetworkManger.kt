package com.manimarank.ptvshows.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat
import javax.inject.Inject

/**
 * Network manager class to identify the network connection
 */
class NetworkManger @Inject constructor(context : Context) {
     private val manager : ConnectivityManager? = ContextCompat.getSystemService(context , ConnectivityManager::class.java)
     
     fun isConnected() : Boolean {
          val activeNetwork : Network? = manager?.activeNetwork
          val capabilities : NetworkCapabilities? = manager?.getNetworkCapabilities(activeNetwork)
          return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
     }
}