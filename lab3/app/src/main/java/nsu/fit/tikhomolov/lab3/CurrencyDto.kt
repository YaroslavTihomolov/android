package nsu.fit.tikhomolov.lab3

import com.google.gson.annotations.SerializedName

data class CurrencyDto(
    @SerializedName("Date")
    var date: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("Valute")
    var valutes: Map<String, ValuteDto>? = null
)

data class ValuteDto(
    @SerializedName("ID")
    var id: String? = null,

    @SerializedName("NumCode")
    var numCode: String? = null,

    @SerializedName("CharCode")
    var charCode: String? = null,

    @SerializedName("Nominal")
    var nominal: Int? = null,

    @SerializedName("Name")
    var name: String? = null,

    @SerializedName("Value")
    var value: String? = null,

    @SerializedName("VunitRate")
    var vunitRate: String? = null
)
