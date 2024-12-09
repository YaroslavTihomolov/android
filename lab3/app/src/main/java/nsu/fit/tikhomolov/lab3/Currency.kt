package nsu.fit.tikhomolov.lab3

data class Currency(
    val id: String?,
    val name: String,
    val charCode: String,
    var value: String?,
    val image: Int?
)