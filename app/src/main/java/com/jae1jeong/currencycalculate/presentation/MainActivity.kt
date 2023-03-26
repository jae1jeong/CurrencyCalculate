package com.jae1jeong.currencycalculate.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.jae1jeong.currencycalculate.R
import com.jae1jeong.currencycalculate.databinding.ActivityMainBinding
import com.jae1jeong.currencycalculate.presentation.base.BaseActivity
import com.jae1jeong.currencycalculate.utils.Validator
import com.jae1jeong.currencycalculate.utils.textChanges
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val viewModel: MainViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
    }

    override fun initView() {

    }

    override fun setEvent() {
        binding.tvReceiveCountryValue.setOnClickListener {
            showSelectDialog()
        }
        binding.btnReceiveCountryChange.setOnClickListener {
            showSelectDialog()
        }


        lifecycleScope.launch {
            combine(
                binding.etSendAmount.textChanges().filter(String::isNotEmpty),
                binding.tvReceiveCountryValue.textChanges()
            ) { amountStr, _ ->
                amountStr
            }
                .map(Validator::onlyNumberExceptDot)
                .debounce(500L)
                .collectLatest {
                    viewModel.currentReceiveCountry.value?.let {
                        viewModel.getExchangeRate(it)
                    }
                }
        }
        binding.etSendAmount.textChanges()
            .distinctUntilChanged()
            .filter(String::isNotEmpty)
            .onEach {
                viewModel.setCurrentSendAmount(it)
            }
            .launchIn(lifecycleScope)
    }

    override fun observeData() {
        viewModel.currentReceiveCountry.observe(this) {
            viewModel.getExchangeRate(it)
        }

        viewModel.unexpectedErrorEvent.observe(this){
            Toast.makeText(this@MainActivity, getString(R.string.unexpected_error_message), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showSelectDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.select_receive_country))
        builder.setItems(viewModel.receiveCountryList.map {
            val countryTextFormatted = String.format(
                getString(R.string.receive_country_format),
                it.countryName,
                it.countryCode
            )
            countryTextFormatted
        }.toTypedArray()) { dialog, which ->
            val selectedItem = viewModel.receiveCountryList[which]
            viewModel.selectReceiveCountry(selectedItem)
        }
        builder.show()
    }


}