package com.application.bank.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.application.bank.R
import com.application.bank.util.showSnackBar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity<B : ViewDataBinding, V : ViewModel> : AppCompatActivity(),
    CoroutineScope {

    private lateinit var binding: B
    private lateinit var viewModel: V
    private lateinit var job: Job

    abstract fun getViewModel(): V

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun getBindingVariable(): Int

    fun getDataBinding(): B {
        return binding
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        job = Job()
    }

    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        viewModel = getViewModel()
        binding.setVariable(getBindingVariable(), viewModel)
        binding.executePendingBindings()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    fun noInternetConnection() {
        binding.root.showSnackBar(getString(R.string.no_internet))
    }
}