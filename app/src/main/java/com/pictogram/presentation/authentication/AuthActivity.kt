package com.pictogram.presentation.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.pictogram.R
import com.pictogram.presentation.home.HomeActivity
import com.pictogram.utils.Constants
import com.pictogram.utils.SharedPrefFunctions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    @Inject
    lateinit var sharedPrefFunctions: SharedPrefFunctions


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val navControllerFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navControllerFragment.navController


        if (sharedPrefFunctions.getPref(Constants.IS_LOGIN, false)) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            return
        }

        val inflater = navController.navInflater
        val graph = inflater.inflate(R.navigation.auth_navigation)
        navControllerFragment.navController.graph = graph
    }
}