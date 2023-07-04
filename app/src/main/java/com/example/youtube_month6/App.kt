package com.example.youtube_month6

import android.app.Application
import com.example.youtube_month6.repository.Repository

class App: Application() {

    companion object {
        val repository = Repository()
    }

}