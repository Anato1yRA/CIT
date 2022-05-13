package com.example.cit.model

import com.google.android.gms.maps.model.LatLng

data class Mission(
    val id:Int,
    val type: String,
    var nameMission: String,
    val forestAddress: String,
    val date: String,
    val dot: Int,
    val dotCount: Int,
    val timeFlight: String,
    val length: Float,
    val visibleItem:Boolean,
    val latitude: Double,
    val longitude: Double,
    var openPanel:Boolean = false,
    var searchStr: String = ""
)
