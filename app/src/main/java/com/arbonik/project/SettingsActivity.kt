package com.arbonik.project

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import kotlinx.android.synthetic.main.activity_main.*

class SettingsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    val settingsFragment = SettingsFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        this.window.statusBarColor = resources.getColor(android.R.color.black)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Настройки"
        val context: Context = applicationContext
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.registerOnSharedPreferenceChangeListener(this)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.settings, settingsFragment)
                    .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        var list_themes: ListPreference? = null
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            list_themes = findPreference("app_theme")
            list_themes?.summary = getDefaultSharedPreferences(context)?.getString("theme_summary", "")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @SuppressLint("CommitPrefEdits")
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (sharedPreferences?.getString("app_theme", "") == "Тёмная") {
            sharedPreferences.edit()?.putString("theme_summary", "Тёмная")?.apply()
            sharedPreferences.edit()?.putInt("theme", AppCompatDelegate.MODE_NIGHT_YES)?.apply()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else if (sharedPreferences?.getString("app_theme", "") == "Светлая") {
            sharedPreferences.edit()?.putString("theme_summary", "Светлая")?.apply()
            sharedPreferences.edit()?.putInt("theme", AppCompatDelegate.MODE_NIGHT_NO)?.apply()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        else if (sharedPreferences?.getString("app_theme", "") == "Тема устройства") {
            sharedPreferences.edit()?.putString("theme_summary", "Тема устройства")?.apply()
            sharedPreferences.edit()?.putInt("theme", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)?.apply()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

}