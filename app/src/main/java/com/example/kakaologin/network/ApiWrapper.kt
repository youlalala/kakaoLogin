package org.techtown.login

import android.util.Log
import com.example.kakaologin.MySharedPreferences
import com.example.kakaologin.network.LoginModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiWrapper {
    companion object {
        fun postToken(userData: LoginModel , onResult: (LoginModel?)->Unit){
            val modelCall = NetWorkService.api.requestLogin(userData)
            modelCall.enqueue(object : Callback<LoginModel> {
                override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>
                ) {
                    Log.i("sss","call : "+call.toString())
                    Log.i("sss","response : "+response.toString())
                    onResult(response.body())
                }

                override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                    Log.i("sss","cancel : "+"t:"+t)
                    modelCall.cancel()
                }
            })
        }

    }

}
