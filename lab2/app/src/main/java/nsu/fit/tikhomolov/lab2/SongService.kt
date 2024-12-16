package nsu.fit.tikhomolov.lab2

class SongService {

    private var songs = mutableListOf<Song>()

    fun getSongs(): MutableList<Song> {
        return songs;
    }

    init {

        songs = (0..49).map {
            Song(
                id = it.toLong(),
                name = "No Hay Ley",
                author = "Kali Uchis",
                photo = IMAGES[it % IMAGES.size],
                isPlayed = false
            )
        }.toMutableList()
    }

    companion object {
        private val IMAGES = mutableListOf(
            "res/drawable/preview.jpg"
        )
    }

}