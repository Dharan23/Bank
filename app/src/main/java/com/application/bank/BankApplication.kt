package com.application.bank

import android.app.Application
import com.application.bank.data.db.BankDatabase
import com.application.bank.data.network.ConnectivityInterceptor
import com.application.bank.data.network.ConnectivityInterceptorImpl
import com.application.bank.data.network.api.ApiService
import com.application.bank.data.network.api.ApiServiceImpl
import com.application.bank.data.network.api.LoginApiService
import com.application.bank.data.network.api.MainApiService
import com.application.bank.data.repository.*
import com.application.bank.ui.login.LoginActivity
import com.application.bank.ui.login.LoginNavigator
import com.application.bank.ui.login.LoginViewModelFactory
import com.application.bank.ui.splash.SplashViewModelFactory
import com.application.bank.ui.statement.StatementViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.*

class BankApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidModule(this@BankApplication))

        bind() from singleton { BankDatabase(instance()) }
        bind() from singleton { instance<BankDatabase>().userDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind<ApiService>() with singleton { ApiServiceImpl(instance()) }
        bind() from singleton { LoginApiService(instance()) }
        bind() from singleton { MainApiService(instance()) }
        bind<LoginRepository>() with singleton { LoginRepositoryImpl(instance(), instance()) }
        bind<UserRepository>() with singleton { UserRepositoryImpl(instance()) }
        bind<StatementRepository>() with singleton { StatementRepositoryImpl(instance()) }
        bind() from provider { LoginViewModelFactory(instance()) }
        bind() from provider { StatementViewModelFactory(instance(), instance()) }
        bind() from provider { SplashViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
    }
}