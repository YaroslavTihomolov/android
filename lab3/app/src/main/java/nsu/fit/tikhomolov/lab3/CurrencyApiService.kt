package nsu.fit.tikhomolov.lab3

import CurrencyDto
import retrofit2.Call
import retrofit2.http.GET

interface CurrencyApiService {
    @GET("scripts/XML_daily.asp")
    fun getLatestRates(): Call<CurrencyDto>
}
