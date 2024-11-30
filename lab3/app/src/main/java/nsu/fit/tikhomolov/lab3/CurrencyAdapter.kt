package nsu.fit.tikhomolov.lab3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    var data: List<Currency> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class CurrencyViewHolder(val binding: ItemCurr) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = .inflate(inflater, parent, false)

        return CurrencyViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
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