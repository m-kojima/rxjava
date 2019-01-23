package com.example.rxjava

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.*

class SecondViewModel {
    private val disposeBag = CompositeDisposable()
    private var myTimer: MyTimer? = null
    private val elapsedSubject = BehaviorSubject.createDefault(0.0)
    private val onStartSubject = PublishSubject.create<Unit>()
    private val onStopSubject = PublishSubject.create<Unit>()

    // out
    val elapsed: Observable<Double>

    // in
    val onStart: Observer<Unit>
    val onStop: Observer<Unit>

    init {
        this.elapsed = this.elapsedSubject.filter { 0 < it }.observeOn(AndroidSchedulers.mainThread())

        this.onStart = this.onStartSubject
        this.onStop = this.onStopSubject

        this.onStartSubject
            .subscribe { this.start() }
            .disposed(by = this.disposeBag)


        this.onStopSubject
            .subscribe { this.stop() }
            .disposed(by = this.disposeBag)
    }

    private fun start() {
        this.myTimer = MyTimer()
    }

    private fun stop() {
        this.myTimer?.stop()
    }

    inner class MyTimer: TimerTask() {
        private val timer: Timer
        private var startTime = 0L

        init {
            this.startTime = Date().time
            this.timer = Timer()
            this.timer.schedule(this, 0, 100)
        }

        fun stop() {
            this.cancel()
        }

        override fun run() {
            val elapsedLong = Date().time - this.startTime
            val elapsed = elapsedLong.toDouble() / 1000

            elapsedSubject.onNext(elapsed)
        }
    }

}

fun Disposable.disposed(by: CompositeDisposable) {
    by.add(this)
}
