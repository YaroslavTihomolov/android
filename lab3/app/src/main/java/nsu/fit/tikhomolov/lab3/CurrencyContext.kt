package nsu.fit.tikhomolov.lab3

data class CurrencyContext(var currencies: List<Currency>) {
    fun getCurrencyByCode(code: String): Currency? {
        return currencies.stream()
            .filter { cur ->
                cur.charCode == code
            }.findFirst().orElseThrow();
    }
}