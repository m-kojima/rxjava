package com.example.rxjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.widget.RxCompoundButton
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    private val disposeBag = CompositeDisposable()
    private lateinit var viewModel: SecondViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }

    override fun onStart() {
        super.onStart()

        val switchChanged = RxCompoundButton.checkedChanges(activity2_switch).share()
        this.disposeBag.add(switchChanged.subscribe { println(it.toString()) })
    }

    override fun onStop() {
        super.onStop()

        this.disposeBag.clear()
    }
}
