package com.application.bank.ui.splash

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.application.bank.BR
import com.application.bank.R
import com.application.bank.databinding.ActivitySplashBinding
import com.application.bank.ui.base.BaseActivity
import com.application.bank.ui.login.LoginActivity.Companion.loginInit
import com.application.bank.ui.statement.StatementActivity.Companion.statementInit
import com.application.bank.util.setLightActionAndNavigationBar
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), SplashNavigator,
    KodeinAware {
    override fun getViewModel(): SplashViewModel {
        viewModel = ViewModelProviders.of(this, factory).get(SplashViewModel::class.java)
        return viewModel
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_splash
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override val kodein by closestKodein()
    private val factory: SplashViewModelFactory by instance()
    private lateinit var viewModel: SplashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        decideNextActivity()
        window.setLightActionAndNavigationBar()
    }

    private fun decideNextActivity() = launch {
        val isLoggedIn = viewModel.userExist.await()

        isLoggedIn.observe(this@SplashActivity, Observer {
            if (it)
                openMainActivity()
            else
                openLoginActivity()
        })
    }

    override fun openLoginActivity() {
        startActivity(loginInit(this))
        finish()
    }

    override fun openMainActivity() {
        startActivity(statementInit(this))
        finish()
    }
}