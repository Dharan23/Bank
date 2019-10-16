package com.application.bank.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProviders
import com.application.bank.BR
import com.application.bank.R
import com.application.bank.databinding.ActivityLoginBinding
import com.application.bank.ui.base.BaseActivity
import com.application.bank.ui.statement.StatementActivity.Companion.statementInit
import com.application.bank.util.hideKkeyboard
import com.application.bank.util.setLightActionAndNavigationBar
import com.application.bank.util.showSnackBar
import com.application.bank.util.showToastShort
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(), LoginNavigator,
    KodeinAware {

    override val kodein by closestKodein()

    private val factory: LoginViewModelFactory by instance()

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    companion object {
        fun loginInit(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setLightActionAndNavigationBar()
        binding = getDataBinding()
        bindUIAction()
    }

    override fun getViewModel(): LoginViewModel {
        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
        viewModel.setNavigator(this)
        return viewModel
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_login
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    private fun bindUIAction() {
        password.setOnEditorActionListener { _, actionId: Int, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                viewModel.onLogin()
                val view = this.currentFocus
                view?.hideKkeyboard()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    override fun loginResponse(response: String) {
        showToastShort(response)
    }

    override fun loginSuccess() {
        startActivity(statementInit(this))
        finish()
    }

    override fun loginError() {
        login_btn.showSnackBar(getString(R.string.login_failed))
    }
}
