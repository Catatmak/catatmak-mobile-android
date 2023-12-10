package com.bangkit.catatmak.ui.authentication

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VerifSuccessViewModel : ViewModel() {

    private var timer: CountDownTimer? = null

    private val initialTime = MutableLiveData<Long>()
    private val currentTime = MutableLiveData<Long>()
    val currentTimeString = MutableLiveData<String>()

    private val _eventCountDownFinish = MutableLiveData<Boolean>()
    val eventCountDownFinish: LiveData<Boolean> = _eventCountDownFinish

    fun setInitialTime(duration: Long) {
        val initialTimeMillis = duration * 1000
        initialTime.value = initialTimeMillis
        currentTime.value = initialTimeMillis
        currentTimeString.value = duration.toString()

        timer = object : CountDownTimer(initialTimeMillis, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                currentTime.value = millisUntilFinished
                val seconds = millisUntilFinished / 1000
                currentTimeString.value = seconds.toString() // Perbarui string saat waktu berubah
            }

            override fun onFinish() {
                resetTimer()
            }
        }.start()
    }

    fun startTimer() {
        timer?.start()
    }

    fun resetTimer() {
        timer?.cancel()
        currentTime.value = initialTime.value
        _eventCountDownFinish.value = true
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

}