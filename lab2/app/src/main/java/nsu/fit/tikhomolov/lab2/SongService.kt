package nsu.fit.tikhomolov.lab2

import com.github.javafaker.Faker

class SongService {

    private var songs = mutableListOf<Song>()

    fun getSongs(): MutableList<Song> {
        return songs;
    }

    init {
        val faker = Faker.instance()

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
            "res/drawable/preview1.jpg",
            "res/drawable/preview1.jpg",
            "res/drawable/preview1.jpg",
            "res/drawable/preview1.jpg",
            "res/drawable/preview1.jpg",
            "res/drawable/preview1.jpg",
            "res/drawable/preview1.jpg",
            "res/drawable/preview1.jpg",
            "res/drawable/preview1.jpg",
            "res/drawable/preview1.jpg"
        )
    }

}