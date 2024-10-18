package nsu.fit.tikhomolov.lab2

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.recyclerview.widget.LinearLayoutManager
import nsu.fit.tikhomolov.lab2.databinding.ActivityMainBinding
import nsu.fit.tikhomolov.lab2.ui.theme.Lab2Theme

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SongAdapter
    private val songService: SongService
        get() = (applicationContext as App).songService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Lab2Theme {
                Surface {
                    SongListScreen()
                }
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val manager = LinearLayoutManager(this)
        adapter = SongAdapter()
        adapter.data = songService.getSongs()

        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = adapter
    }
}

@Composable
fun SongListScreen() {
    Text("Song List")
}