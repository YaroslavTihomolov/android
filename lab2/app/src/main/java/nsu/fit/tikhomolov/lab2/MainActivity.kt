package nsu.fit.tikhomolov.lab2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import nsu.fit.tikhomolov.lab2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SongAdapter
    private val songService: SongService
        get() = (applicationContext as App).songService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val manager = LinearLayoutManager(this)
        adapter = SongAdapter()
        adapter.data = songService.getSongs()

        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = adapter
    }
}