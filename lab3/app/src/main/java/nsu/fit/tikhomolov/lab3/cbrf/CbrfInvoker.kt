package nsu.fit.tikhomolov.lab3.cbrf

import android.widget.ArrayAdapter
import nsu.fit.tikhomolov.lab3.Currency
import nsu.fit.tikhomolov.lab3.CurrencyAdapter
import nsu.fit.tikhomolov.lab3.CurrencyContext
import nsu.fit.tikhomolov.lab3.CurrencyDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.stream.Collectors

class CbrfInvoker(
    private val currencyMapper: CurrencyMapper,
    private val currencyContext: CurrencyContext,
    private val recycleAdapter: CurrencyAdapter,
    private val spinnerAdapterFrom: ArrayAdapter<String>,
    private val spinnerAdapterTo: ArrayAdapter<String>
) {

    private var CBRF_URL: String = "https://www.cbr-xml-daily.ru/"

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(CBRF_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(
        CbrfApiService::class.java
    )

    private val RUSSIA_CURRENCY: Currency = Currency(
        id = "1",
        name = "Russia",
        charCode = "RUB",
        value = "1",
        image = null
    )

    fun createRequest() {
        apiService.getLatestRates().enqueue(object : Callback<CurrencyDto> {
            override fun onResponse(call: Call<CurrencyDto>, response: Response<CurrencyDto>) {
                if (response.isSuccessful) {
                    val currencies = response.body()?.let { currencyDto ->
                        currencyMapper.mapData(currencyDto)
                    } ?: emptyList()
                    currencyContext.currencies = (currencies + RUSSIA_CURRENCY)
                    recycleAdapter.data = currencies
                    val codes = currencyContext.currencies.stream()
                        .map { cur ->
                            cur.charCode
                        }.collect(Collectors.toList()) ?: emptyList()
                    updateAdapterValues(spinnerAdapterFrom, codes)
                    updateAdapterValues(spinnerAdapterTo, codes)
                }
            }

            override fun onFailure(call: Call<CurrencyDto>, t: Throwable) {
                println("Get currency fail $t")
            }
        })
    }

    private fun updateAdapterValues(adapter: ArrayAdapter<String>, values: List<String>) {
        adapter.clear()
        adapter.addAll(values)
        currencyContext.codes = values
    }

}