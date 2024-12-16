package nsu.fit.tikhomolov.lab2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nsu.fit.tikhomolov.lab2.databinding.ItemSongBinding

class SongAdapter : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    var data: List<Song> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class SongViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSongBinding.inflate(inflater, parent, false)

        return SongViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = data[position]
        val context = holder.itemView.context

        with(holder.binding) {
            songInfo.nameTextView.text = song.name
            songInfo.companyTextView.text = song.author

            playingImageView.setImageResource(if (song.isPlayed) R.drawable.ic_pauce else R.drawable.ic_play)

            playingImageView.setOnClickListener {
                data.stream()
                    .filter { s -> s.isPlayed && s.id != song.id }
                    .forEach { s ->
                        s.isPlayed = false
                        notifyItemChanged(s.id.toInt())
                    }
                song.isPlayed = !song.isPlayed
                playingImageView.setImageResource(if (song.isPlayed) R.drawable.ic_pauce else R.drawable.ic_play)
            }
            imageView.setImageResource(R.drawable.preview)
        }
    }

}