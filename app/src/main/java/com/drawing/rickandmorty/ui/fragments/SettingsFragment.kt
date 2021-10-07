package com.drawing.rickandmorty.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.databinding.FragmentSettingsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class SettingsFragment: Fragment(R.layout.fragment_settings) {

    private var binding : FragmentSettingsBinding? = null
    private var selectedItemIndex = 0
    val sharedPref: SharedPreferences? = activity?.getSharedPreferences(
        "sharedPref",
        Context.MODE_PRIVATE
    )
    val editor: SharedPreferences.Editor? = sharedPref?.edit()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSettingsBinding.bind(view)

        binding!!.clThemeSettings.setOnClickListener {
            showConfirmationDialog(it)
        }
    }

    private fun showConfirmationDialog(view: View){
        val singleItems = arrayOf("Off", "On", "Follow system settings")

        MaterialAlertDialogBuilder(requireContext())
                .setTitle("Dark Theme")
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(singleItems, selectedItemIndex) { dialog, which ->
                    // Respond to item chosen
                    selectedItemIndex = which
                    val checkedItem = singleItems[which]
                    binding!!.userThemeChoice.text = checkedItem

                    val theme = when(which){
                        0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }


                    //AppCompatDelegate.setDefaultNightMode(theme)
                    showSnackbar(view, "$checkedItem selected")
                }
                .setNeutralButton("CANCEL") { dialog, which ->
                    // Respond to neutral button press
                }
                .show()
    }

    private fun showSnackbar(view: View, msg: String){
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()
    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}