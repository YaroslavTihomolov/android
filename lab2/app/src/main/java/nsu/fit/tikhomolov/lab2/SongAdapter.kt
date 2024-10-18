package nsu.fit.tikhomolov.lab2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
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

            nameTextView.text = song.name
            companyTextView.text = song.author
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

            Glide.with(context)
                .load(song.photo)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(16)))
                .error(
                    Glide.with(context).load(R.drawable.preview)
                        .apply(RequestOptions.bitmapTransform(RoundedCorners(16)))
                )
                .placeholder(R.drawable.rounded_background)
                .into(imageView)
        }
    }

}