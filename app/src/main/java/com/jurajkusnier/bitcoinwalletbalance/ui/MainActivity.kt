package com.jurajkusnier.bitcoinwalletbalance.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.MenuItem
import com.jurajkusnier.bitcoinwalletbalance.R
import com.jurajkusnier.bitcoinwalletbalance.di.ViewModelFactory
import com.jurajkusnier.bitcoinwalletbalance.ui.main.MainActivityViewModel
import com.jurajkusnier.bitcoinwalletbalance.ui.main.ParentFragment
import com.jurajkusnier.bitcoinwalletbalance.ui.settings.SettingsDialog
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity: DaggerAppCompatActivity() {

    @Inject lateinit var viewModelFactory: ViewModelFactory

    val TAG = MainActivity::class.simpleName

    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        //DI activity injection first
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ParentFragment.newInstance())
                    .commitNow()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                true
            }
            R.id.menu_settings -> {
                SettingsDialog().show(supportFragmentManager,SettingsDialog.TAG)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

}