package nsu.fit.tikhomolov.lab3

import CurrencyDto
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import nsu.fit.tikhomolov.lab3.databinding.ActivityMainBinding
import nsu.fit.tikhomolov.lab3.ui.theme.Lab3Theme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.stream.Collectors


class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CurrencyAdapter
    private lateinit var adapterFrom: ArrayAdapter<String>
    private lateinit var adapterTo: ArrayAdapter<String>
    private lateinit var currencyContext: CurrencyContext
    private lateinit var calculateService: CurrencyCalculateService
    private val currencyService: CurrencyService get() = (applicationContext as App).currencyService

    private val currencyRussia: Currency = Currency(
        id = "1",
        name = "Russia",
        charCode = "RUB",
        value = "1",
        image = null
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val manager = LinearLayoutManager(this)
        adapter = CurrencyAdapter()
        adapter.setHasStableIds(true)
        adapter.data = currencyService.getCurrency()

        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = adapter

        currencyContext = CurrencyContext(listOf())
        calculateService = CurrencyCalculateService(this, currencyContext)


        adapterFrom = ArrayAdapter(
            this,
            R.layout.spinner_list,
            mutableListOf("RUB")
        )
        adapterTo = ArrayAdapter(this, R.layout.spinner_list, mutableListOf("RUB"))

        adapterFrom.setDropDownViewResource(R.layout.spinner_dropdown_item)
        adapterTo.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.currencyChange.currencyFrom.adapter = adapterFrom
        binding.currencyChange.currencyTo.adapter = adapterTo


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
                    val currencies = currencyDto?.valutes?.stream()
                        ?.filter { valut ->
                            currencyService.getImage(valut.charCode.toString()) != null
                        }
                        ?.map { valut ->
                            Currency(
                                id = valut.id,
                                name = valut.name.toString(),
                                charCode = valut.charCode.toString(),
                                value = valut.value,
                                image = currencyService.getImage(valut.charCode.toString())
                            )
                        }?.collect(Collectors.toList()) ?: emptyList()
                    println("Currencies size: ${currencies.size}")
                    println("Adapter data size: ${adapter.data.size}")

                    currencyContext.currencies = currencies
                    adapter.data = currencies
                    val codes = currencyContext.currencies
                        .stream()
                        .map {
                            cur -> cur.charCode
                        }.collect(Collectors.toList()) ?: emptyList()
                    adapterFrom.clear()
                    adapterFrom.addAll(codes)
                    adapterTo.clear()
                    adapterTo.addAll(codes)
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