package nsu.fit.tikhomolov.lab3.cbrf

import nsu.fit.tikhomolov.lab3.CurrencyDto
import retrofit2.Call
import retrofit2.http.GET

interface CbrfApiService {
    @GET("daily_json.js")
    fun getLatestRates(): Call<CurrencyDto>
}
