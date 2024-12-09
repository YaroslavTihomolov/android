package nsu.fit.tikhomolov.lab3

class CurrencyService {

    private var currencies = mutableListOf<Currency>()

    fun getCurrency(): MutableList<Currency> {
        return currencies;
    }

    fun getImage(currency: String): Int? {
        return IMAGES[currency]
    }

    companion object {
        private val IMAGES = mutableMapOf(
            "USD" to R.drawable.usd,
            "AUD" to R.drawable.aud,
            "AZN" to R.drawable.azn,
            "GBP" to R.drawable.gbp,
            "AMD" to R.drawable.amd,
            "BYN" to R.drawable.byn,
            "BGN" to R.drawable.bgn,
            "BRL" to R.drawable.brl,
            "HUF" to R.drawable.huf,
            "VND" to R.drawable.vnd,
            "HKD" to R.drawable.hkd,
            "GEL" to R.drawable.gel,
            "DKK" to R.drawable.dkk,
            "AED" to R.drawable.aed,
            "EUR" to R.drawable.eur,
            "EGP" to R.drawable.egp,
            "XDR" to R.drawable.xdr
        )
    }

}