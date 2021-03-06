package com.reminders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.reminders.reminders.ReadReminderFragment
import com.reminders.reminders.ReadReminderFragmentDirections
import com.reminders.topics.ReadTopicFragment


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        //hides the back button on the app bar to utilize Android's native back button
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.readTopicFragment, R.id.readReminderFragment,
            R.id.readReminderFragment, R.id.createUpdateReminderFragment))

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {

        return navController.navigateUp() || super.onSupportNavigateUp()

    }

}