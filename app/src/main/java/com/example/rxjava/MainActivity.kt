package com.example.rxjava

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setBinding()
    }

    private fun setBinding() {
        RxView
            .clicks(main_next_button)
            .subscribe( {
                val intent = Intent(this, SecondActivity::class.java)
                this.startActivity(intent)
            })
    }
}
