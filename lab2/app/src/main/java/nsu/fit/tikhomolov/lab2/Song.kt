package nsu.fit.tikhomolov.lab2

data class Song(
    val id: Long,
    val name: String,
    val author: String,
    val photo: String,
    var isPlayed: Boolean
)
