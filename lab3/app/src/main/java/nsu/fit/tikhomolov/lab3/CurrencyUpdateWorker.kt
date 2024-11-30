package nsu.fit.tikhomolov.lab3

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.work.Worker
import androidx.work.WorkerParameters

class CurrencyUpdateWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    private val handler = Handler(Looper.getMainLooper())
    private var isJobCancelled = false

    override fun doWork(): Result {
        handler.post(object : Runnable {
            override fun run() {
                if (isJobCancelled) return

                updateCurrencyRates()
                handler.postDelayed(this, 15_000)
            }
        })
        updateCurrencyRates()

        return Result.success()
    }

    private fun updateCurrencyRates() {
        println("Курсы валют обновлены!")
    }
}
