package nsu.fit.tikhomolov.lab3

import CurrencyDto
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.PeriodicWorkRequestBuilder
import nsu.fit.tikhomolov.lab3.ui.theme.Lab3Theme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SongAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        doRequest()
        val myWorkRequest = PeriodicWorkRequestBuilder<CurrencyUpdateWorker>(
            30,
            TimeUnit.MINUTES,
            25,
            TimeUnit.MINUTES
        ).build()
        enableEdgeToEdge()
        setContent {
            Lab3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        doRequest()
    }

    private fun doRequest() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.cbr.ru/")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        val apiService = retrofit.create(
            CurrencyApiService::class.java
        )

        apiService.getLatestRates().enqueue(object : Callback<CurrencyDto> {
            override fun onResponse(call: Call<CurrencyDto>, response: Response<CurrencyDto>) {
                if (response.isSuccessful) {
                    val currencyDto = response.body()
                    println(currencyDto)
                } else {
                    println("Error!!")
                }
            }

            override fun onFailure(call: Call<CurrencyDto>, t: Throwable) {
                println(t)
            }
        })
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab3Theme {
        Greeting("Android")
    }
}