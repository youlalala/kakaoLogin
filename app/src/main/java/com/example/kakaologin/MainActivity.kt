package com.example.kakaologin
import android.content.Intent
import com.kakao.sdk.common.util.Utility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kakaologin.MySharedPreferences.clearTOKEN
import com.example.kakaologin.databinding.ActivityLoginBinding
import com.example.kakaologin.databinding.ActivityMainBinding
import com.example.kakaologin.network.LoginModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import org.techtown.login.ApiWrapper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val method = MySharedPreferences.getMethod(this)
        val token = MySharedPreferences.getToken(this)
        Log.i("sss", "method "+method)
        Log.i("SSS",token)
        if (method == "kakao") {
            val userInfo = LoginModel(
                id="111",
                token=MySharedPreferences.getToken(this)
            )

            Log.i("sss","userInfo : "+userInfo)

            ApiWrapper.postToken(userInfo){}

            // 사용자 정보 요청 (기본)
            if(token != null){
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.d("sss", "사용자 정보 요청 실패", error)
                        val intent= Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    else if (user != null) {
                        Log.d(
                            "sss", "사용자 정보 요청 성공" +
                                    "\n회원번호: ${user.id}" +
                                    "\n이메일: ${user.kakaoAccount?.email}" +
                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                    "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                        )
                        binding.name.text = user.kakaoAccount?.profile?.nickname
                    }
                }

            }
        }else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.logoutBtn.setOnClickListener {
            clearTOKEN(this)
            if (method == "kakao") {
                kakaoLogout()
            }
        }


    }
    private fun kakaoLogout(){
        UserApiClient.instance.logout{
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}