package nsu.fit.tikhomolov.lab3

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import java.math.BigDecimal
import java.math.RoundingMode

class CurrencyCalculateService(
    context: Context,
    private val currencyContext: CurrencyContext
) {

    private val amount1: EditText = (context as Activity).findViewById(R.id.amount1)
    private val amount1Currency: Spinner = (context as Activity).findViewById(R.id.currency_from)
    private val amount2: TextView = (context as Activity).findViewById(R.id.amount2)
    private val amount2Currency: Spinner = (context as Activity).findViewById(R.id.currency_to)

    private var isAmount2Updating = false

    init {
        amount1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable?) {
                updateCalculation()
            }
        })

        amount1Currency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                updateCalculation()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }
    }

    private fun updateCalculation() {
        if (isAmount2Updating) return

        val inputText = amount1.text.toString()
        if (inputText.isEmpty()) {
            updateAmount("")
            return
        }
        val floatValue = floatParser(inputText)
        val cur1 =
            currencyContext.getCurrencyByCode(amount1Currency.selectedItem.toString())
        val cur2 =
            currencyContext.getCurrencyByCode(amount2Currency.selectedItem.toString())
        val amount2Value = floatValue * floatParser(cur1?.value) / floatParser(cur2?.value)

        updateAmount(
            BigDecimal(amount2Value.toDouble()).setScale(
                1,
                RoundingMode.HALF_UP
            ).toFloat().toString()
        )
    }

    private fun updateAmount(value: String?) {
        isAmount2Updating = true
        amount2.text = value
        isAmount2Updating = false
    }

    private fun floatParser(value: String?): Float {
        return value?.replace(",", ".")?.toFloat() ?: 0F

    }

}
