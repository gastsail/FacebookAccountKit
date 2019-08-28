package com.gaston.accountkitsocandroide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.facebook.accountkit.AccountKit
import com.facebook.accountkit.ui.LoginType
import com.facebook.accountkit.ui.AccountKitConfiguration
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.facebook.accountkit.AccessToken
import com.facebook.accountkit.AccountKitLoginResult
import com.facebook.accountkit.LoggingBehavior
import com.facebook.accountkit.ui.AccountKitActivity


class MainActivity : AppCompatActivity() {

    companion object {
        private const val APP_REQUEST_CODE = 99
    }

    private var accessToken:AccessToken? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         accessToken = AccountKit.getCurrentAccessToken()

        if(accessToken != null){
            //Handle token
        }else{
            // Handle error
        }


    }

    fun phoneLogin(view: View) {
        val intent = Intent(this, AccountKitActivity::class.java)
        val configurationBuilder = AccountKitConfiguration.AccountKitConfigurationBuilder(
            LoginType.PHONE,
            AccountKitActivity.ResponseType.CODE
        ) // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        intent.putExtra(
            AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
            configurationBuilder.build()
        )
        startActivityForResult(intent, APP_REQUEST_CODE)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            val loginResult =
                data!!.getParcelableExtra<AccountKitLoginResult>(AccountKitLoginResult.RESULT_KEY)
            val toastMessage: String
            when {
                loginResult.error != null -> {
                    toastMessage = loginResult.error!!.errorType.message
                    Log.e("Login error:","$loginResult.error")
                }
                loginResult.wasCancelled() -> toastMessage = "Login Cancelled"
                else -> {
                    toastMessage = if (loginResult.accessToken != null) {
                        "Success:" + loginResult.accessToken!!.accountId
                    } else {
                        String.format(
                            "Success:%s...",
                            loginResult.authorizationCode!!.substring(0, 10)
                        )
                    }

                    // If you have an authorization code, retrieve it from
                    // loginResult.getAuthorizationCode()
                    // and pass it to your server and exchange it for an access token.

                    // Success! Start your next activity...
                    startActivity(Intent(this,LoggedActivity::class.java))
                }
            }

            // Surface the result to your user in an appropriate way.
            Toast.makeText(
                this,
                toastMessage,
                Toast.LENGTH_LONG
            )
                .show()
        }
    }
}
