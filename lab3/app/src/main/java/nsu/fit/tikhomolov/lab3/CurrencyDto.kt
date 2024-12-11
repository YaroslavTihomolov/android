package nsu.fit.tikhomolov.lab3

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "ValCurs", strict = false)
data class CurrencyDto(
    @field:Attribute(name = "Date")
    var date: String? = null,

    @field:Attribute(name = "name")
    var name: String? = null,

    @field:ElementList(name = "Valute", inline = true, required = false)
    var valutes: List<ValuteDto>? = null
)

@Root(name = "Valute", strict = false)
data class ValuteDto(
    @field:Attribute(name = "ID")
    var id: String? = null,

    @field:Element(name = "NumCode")
    var numCode: String? = null,

    @field:Element(name = "CharCode")
    var charCode: String? = null,

    @field:Element(name = "Nominal")
    var nominal: Int? = null,

    @field:Element(name = "Name")
    var name: String? = null,

    @field:Element(name = "Value")
    var value: String? = null,

    @field:Element(name = "VunitRate")
    var vunitRate: String? = null
)
