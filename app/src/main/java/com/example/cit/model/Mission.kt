package com.example.cit.model

data class Mission(
    val id:Int,
    val type: String,
    val name: String,
    val forestAddress: String,
    val date: String,
    val dot: Int,
    val dotCount: Int,
    val timeFlight: String,
    val length: Float,
    val visible:Boolean
)
