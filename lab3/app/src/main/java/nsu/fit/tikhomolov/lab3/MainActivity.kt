package nsu.fit.tikhomolov.lab3

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import nsu.fit.tikhomolov.lab3.cbrf.CbrfInvoker
import nsu.fit.tikhomolov.lab3.cbrf.CurrencyMapper
import nsu.fit.tikhomolov.lab3.databinding.ActivityMainBinding


class MainActivity : ComponentActivity() {

    private var CODES: String = "CODES"
    private var CURRENCIES: String = "CURRENCIES"

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CurrencyAdapter
    private lateinit var adapterFrom: ArrayAdapter<String>
    private lateinit var adapterTo: ArrayAdapter<String>
    private lateinit var currencyContext: CurrencyContext
    private lateinit var calculateService: CurrencyCalculateService
    private lateinit var currencyMapper: CurrencyMapper
    private lateinit var cbrfInvoker: CbrfInvoker
    private val currencyService: CurrencyService get() = (applicationContext as App).currencyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val manager = LinearLayoutManager(this)
        adapter = CurrencyAdapter()
        adapter.data = currencyService.getCurrency()

        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = adapter

        val savedCurrencies = savedInstanceState?.getParcelableArrayList<Currency>(CURRENCIES)
        val savedCodes = savedInstanceState?.getStringArrayList(CODES)

        currencyContext = CurrencyContext(
            savedCurrencies ?: listOf(),
            savedCodes ?: listOf()
        )
        calculateService = CurrencyCalculateService(this, currencyContext)

        adapterFrom = ArrayAdapter(
            this,
            R.layout.spinner_list,
            mutableListOf("RUB")
        )
        adapterTo = ArrayAdapter(this, R.layout.spinner_list, mutableListOf("RUB"))

        adapterFrom.setDropDownViewResource(R.layout.spinner_dropdown_item)
        adapterTo.setDropDownViewResource(R.layout.spinner_dropdown_item)

        binding.currencyChange.currencyFrom.adapter = adapterFrom
        binding.currencyChange.currencyTo.adapter = adapterTo

        if (savedCurrencies != null && savedCodes != null) {
            adapter.data = currencyContext.currencies
            updateAdapterValues(adapterTo, savedCodes)
            updateAdapterValues(adapterFrom, savedCodes)
        } else {
            currencyMapper = CurrencyMapper(currencyService)
            cbrfInvoker =
                CbrfInvoker(currencyMapper, currencyContext, adapter, adapterFrom, adapterTo)
            cbrfInvoker.createRequest()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(CURRENCIES, ArrayList(currencyContext.currencies))
        outState.putStringArrayList(CODES, ArrayList(currencyContext.codes))
    }

    private fun updateAdapterValues(adapter: ArrayAdapter<String>, values: List<String>) {
        adapter.clear()
        adapter.addAll(values)
    }

}
