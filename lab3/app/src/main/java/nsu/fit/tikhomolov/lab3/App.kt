package nsu.fit.tikhomolov.lab3

import android.app.Application

class App : Application() {

    lateinit var currencyService: CurrencyService

    override fun onCreate() {
        super.onCreate()
        currencyService = CurrencyService()
    }
}