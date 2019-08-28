package com.gaston.accountkitsocandroide

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.facebook.accountkit.AccountKit

import kotlinx.android.synthetic.main.activity_logged.*

class LoggedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Toast.makeText(this,"Se cerro sesion",Toast.LENGTH_SHORT).show()
          AccountKit.logOut()
            finish()
        }
    }

}
