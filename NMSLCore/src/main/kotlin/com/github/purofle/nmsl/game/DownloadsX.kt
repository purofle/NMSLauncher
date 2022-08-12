package com.github.purofle.nmsl.game


import com.google.gson.annotations.SerializedName

data class DownloadsX(
    @SerializedName("artifact")
    val artifact: Artifact? = null,
    @SerializedName("classifiers")
    val classifiers: Classifiers? = null
)