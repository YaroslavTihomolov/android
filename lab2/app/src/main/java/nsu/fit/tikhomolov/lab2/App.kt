package nsu.fit.tikhomolov.lab2

import android.app.Application

class App : Application() {

    lateinit var songService: SongService // Initialize this as needed

    override fun onCreate() {
        super.onCreate()
        songService = SongService() // Assuming this is how you initialize it
    }
}