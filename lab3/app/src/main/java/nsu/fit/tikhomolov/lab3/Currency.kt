package nsu.fit.tikhomolov.lab3

data class Currency(
    val id: Long,
    val name: String,
    val charCode: String,
    var value: Float
)