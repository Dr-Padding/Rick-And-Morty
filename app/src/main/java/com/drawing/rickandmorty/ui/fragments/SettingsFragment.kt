package com.drawing.rickandmorty.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils.indexOf
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.databinding.FragmentSettingsBinding
import com.drawing.rickandmorty.util.Constants
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class SettingsFragment: Fragment(R.layout.fragment_settings) {

    private var binding : FragmentSettingsBinding? = null
    //var fragmentReload = false

    private val editor: SharedPreferences.Editor by lazy {
        requireActivity().getSharedPreferences(
            "sharedPref",
            Context.MODE_PRIVATE
        ).edit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSettingsBinding.bind(view)

        val singleItems = arrayOf("Off", "On", "Follow system settings")

        val defaultNightMode = requireContext().getSharedPreferences("sharedPref", AppCompatActivity.MODE_PRIVATE)
            .getInt("themeMode", Constants.FOLLOW_SYSTEM_THEME)

        var selectedItemIndex = when(defaultNightMode){
            Constants.LIGHT_THEME -> 0
            Constants.DARK_THEME -> 1
            else -> 2
        }

        var checkedItem = singleItems[selectedItemIndex]

            binding!!.userThemeChoice.text = checkedItem
            showSnackbar(view, "$checkedItem selected")

        binding!!.clThemeSettings.setOnClickListener {
            //showConfirmationDialog(it)

            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Dark Theme")
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(singleItems, selectedItemIndex) { dialog, which ->
                    // Respond to item chosen
                    selectedItemIndex = which


                    val themeMode = when(which){
                        0 -> Constants.LIGHT_THEME
                        1 -> Constants.DARK_THEME
                        else -> Constants.FOLLOW_SYSTEM_THEME
                    }

                    editor.apply {
                        putInt("themeMode", themeMode)
                    }.apply()

                    AppCompatDelegate.setDefaultNightMode(themeMode)
                    binding!!.userThemeChoice.text = checkedItem
                    showSnackbar(view, "$checkedItem selected")
                }
                .setNeutralButton("CANCEL") { dialog, which ->
                    // Respond to neutral button press
                }
                .show()
        }
    }

    /*private fun showConfirmationDialog(view: View){

    }*/

    private fun showSnackbar(view: View, msg: String){
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()
    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}