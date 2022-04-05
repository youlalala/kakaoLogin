package com.example.kakaologin.network

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.auth.model.OAuthToken

data class LoginModel (
    var id: String,
    var token: String
)
