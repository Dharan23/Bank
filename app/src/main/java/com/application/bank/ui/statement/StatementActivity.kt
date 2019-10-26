package com.application.bank.ui.statement

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.test.espresso.idling.CountingIdlingResource
import com.application.bank.BR
import com.application.bank.R
import com.application.bank.databinding.ActivityStatementBinding
import com.application.bank.ui.base.BaseActivity
import com.application.bank.ui.login.LoginActivity.Companion.loginInit
import com.application.bank.ui.statement.adapter.StatementAdapter
import com.application.bank.util.NoConnectivityException
import com.application.bank.util.setLightNavigationBar
import kotlinx.android.synthetic.main.activity_statement.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class StatementActivity : BaseActivity<ActivityStatementBinding, StatementViewModel>(),
    KodeinAware, StatementNavigator {

    override val kodein by closestKodein()
    private val factory: StatementViewModelFactory by instance()

    private lateinit var binding: ActivityStatementBinding
    private lateinit var viewModel: StatementViewModel
    private lateinit var adapter: StatementAdapter

    companion object {
        fun statementInit(context: Context): Intent {
            return Intent(context, StatementActivity::class.java)
        }
    }

    override fun getViewModel(): StatementViewModel {
        viewModel = ViewModelProviders.of(this, factory).get(StatementViewModel::class.java)
        return viewModel
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_statement
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setLightNavigationBar()
        binding = getDataBinding()
        init()
        bindUI()
    }

    private fun init() {
        viewModel.setNavigator(this)
        statement_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        statement_rv.setHasFixedSize(true)
        adapter = StatementAdapter { view: View, id: Int ->

        }
        statement_rv.adapter = adapter
    }

    private fun bindUI() = launch {
        val userLiveData = viewModel.userLiveData.await()

        try {
            val statementLiveData = viewModel.statements.await()
            statementLiveData.observe(this@StatementActivity, Observer { statements ->
                if (statements != null && statements.statementList.isNotEmpty()) {
                    adapter.updateStatementList(statements = statements.statementList)
                }
            })
        } catch (e: Exception) {
            when (e) {
                is NoConnectivityException -> {
                    noInternetConnection()
                }
                else -> {
                    Log.d("Exception", e.let { it.message })
                }
            }
        }

        userLiveData.observe(this@StatementActivity, Observer
        { user ->
            if (user != null) {
                binding.user = user
                viewModel.userIdLivedata.postValue(user.userId)
            }
        })
    }

    override fun logOutUser() {
        startActivity(loginInit(this))
        finish()
    }
}