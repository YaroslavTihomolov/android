package nsu.fit.tikhomolov.lab3.cbrf

import nsu.fit.tikhomolov.lab3.CurrencyDto
import android.widget.ArrayAdapter
import nsu.fit.tikhomolov.lab3.Currency
import nsu.fit.tikhomolov.lab3.CurrencyAdapter
import nsu.fit.tikhomolov.lab3.CurrencyContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.stream.Collectors

class CbrfInvoker(
    private val currencyMapper: CurrencyMapper,
    private val currencyContext: CurrencyContext,
    private val recycleAdapter: CurrencyAdapter,
    private val spinnerAdapterFrom: ArrayAdapter<String>,
    private val spinnerAdapterTo: ArrayAdapter<String>
) {

    private var CBRF_URL: String = "https://www.cbr.ru/"

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(CBRF_URL)
        .addConverterFactory(SimpleXmlConverterFactory.create())
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

    fun createRequest(retryCount: Int = 3) {
        println("!!!createRequest")
        apiService.getLatestRates().enqueue(object : Callback<CurrencyDto> {
            override fun onResponse(call: Call<CurrencyDto>, response: Response<CurrencyDto>) {
                if (response.isSuccessful) {
                    val currencies = response.body()?.let { currencyDto ->
                        currencyMapper.mapData(currencyDto)
                    } ?: emptyList()
                    currencyContext.currencies = currencies
                    recycleAdapter.data = currencies
                    val codes = (currencyContext.currencies + RUSSIA_CURRENCY)
                        .stream()
                        .map { cur ->
                            cur.charCode
                        }.collect(Collectors.toList()) ?: emptyList()
                    updateAdapterValues(spinnerAdapterFrom, codes)
                    updateAdapterValues(spinnerAdapterTo, codes)
                } else {
                    createRequest(retryCount - 1)
                }
            }

            override fun onFailure(call: Call<CurrencyDto>, t: Throwable) {
                createRequest(retryCount - 1)
            }
        })
    }

    private fun updateAdapterValues(adapter: ArrayAdapter<String>, values: List<String>) {
        adapter.clear()
        adapter.addAll(values)
        currencyContext.codes = values
    }

}