package nsu.fit.tikhomolov.lab3.cbrf

import nsu.fit.tikhomolov.lab3.CurrencyDto
import nsu.fit.tikhomolov.lab3.Currency
import nsu.fit.tikhomolov.lab3.CurrencyService
import java.util.stream.Collectors

class CurrencyMapper(private val currencyService: CurrencyService) {

    fun mapData(currencyDto: CurrencyDto): List<Currency> {
        return currencyDto.valutes?.stream()
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
    }

}