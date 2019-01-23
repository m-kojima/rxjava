package com.example.rxjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.widget.RxCompoundButton
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    private lateinit var viewModel: SecondViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        this.viewModel = SecondViewModel()
    }

    override fun onStart() {
        super.onStart()

        val switchChanged = RxCompoundButton.checkedChanges(activity2_switch).share()
        switchChanged.filter { it }.map { Unit }.subscribe(this.viewModel.onStart)
        switchChanged.filter { !it }.map { Unit }.subscribe(this.viewModel.onStop)

        this.viewModel.elapsed.map { it.toString() }.subscribe(RxTextView.text(activity2_label))
    }

    override fun onStop() {
        super.onStop()
    }

}
