package com.jae1jeong.currencycalculate.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<T:ViewDataBinding,VM:BaseViewModel>:AppCompatActivity() {
    protected lateinit var binding:T
    protected abstract val viewModel:VM
    abstract val layoutResourceId:Int

    abstract fun initView()
    abstract fun setEvent()
    abstract fun observeData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,layoutResourceId)
        binding.lifecycleOwner = this
        initView()
    }

    override fun onStart() {
        super.onStart()
        setEvent()
        observeData()
    }
}