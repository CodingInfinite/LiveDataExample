package com.spartons.livedataexample

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import kotlinx.android.synthetic.main.live_data_example.*
import java.util.*
import java.util.concurrent.TimeUnit

class LiveDataExample : AppCompatActivity() {

    companion object {
        fun getLiveData(callingClassContext: Context) = Intent(callingClassContext, LiveDataExample::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.live_data_example)
        val timeChangerViewModel = ViewModelProviders.of(this).get(TimeChangerViewModel::class.java)
        val calendar = Calendar.getInstance()
        timeChangerViewModel.timerValue.observe(this, Observer<Long> { t ->
            calendar?.timeInMillis = t!!
            timTextView.text = calendar.time.toString()
        })
    }
}

class TimeChangerViewModel : ViewModel() {

    val timerValue = MutableLiveData<Long>()

    init {
        Log.e("Model", "initialize")
        timerValue.value = System.currentTimeMillis()
        startTimer()
    }

    private fun startTimer() {
        Observable.interval(2, 2, TimeUnit.SECONDS)
                .subscribe({
                    Log.e("Model", it.toString())
                    timerValue.postValue(System.currentTimeMillis())
                }, Throwable::printStackTrace)
    }

}