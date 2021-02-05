package com.arbonik.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SettingsFragment : Fragment() {
    var mView: View? = null
    var swith_theme: SwitchCompat? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_settings, container, false)
        swith_theme = mView?.findViewById(R.id.switch_theme)
        swith_theme!!.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if (p1) {
                    context!!.setTheme(android.R.style.Theme_Black)
                    Toast.makeText(context, "asd", Toast.LENGTH_SHORT).show()
                }
            }
        })
        return mView
    }
}