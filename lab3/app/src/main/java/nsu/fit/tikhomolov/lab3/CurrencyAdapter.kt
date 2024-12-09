package nsu.fit.tikhomolov.lab3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import nsu.fit.tikhomolov.lab3.databinding.ItemCurrencyBinding

class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    var data: List<Currency> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class CurrencyViewHolder(val binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCurrencyBinding.inflate(inflater, parent, false)

        return CurrencyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = data[position]
        val context = holder.itemView.context


        with(holder.binding) {
            currencyInfo.nameTextView.text = currency.name
            currencyInfo.companyTextView.text = currency.charCode
            currencyValue.text = String.format("%s%c", currency.value, '₽')

            Glide.with(context)
                .clear(imageView)
            Glide.with(context).load(currency.image).circleCrop()
                .error(R.drawable.preview)
                .placeholder(R.drawable.preview).into(imageView)
        }
    }

}