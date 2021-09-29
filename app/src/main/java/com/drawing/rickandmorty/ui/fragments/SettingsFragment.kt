package com.drawing.rickandmorty.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.drawing.rickandmorty.R
import com.drawing.rickandmorty.databinding.FragmentSettingsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class SettingsFragment: Fragment(R.layout.fragment_settings) {

    private var binding : FragmentSettingsBinding? = null
    var selectedItemIndex = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSettingsBinding.bind(view)

        binding!!.clThemeSettings.setOnClickListener {
            showConfirmationDialog(it)
        }

    }

    fun showConfirmationDialog(view: View){
        val singleItems = arrayOf("Off", "On", "Follow system settings", "In power saving mode")
        var checkedItem = singleItems[selectedItemIndex]

            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Dark Theme")
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(singleItems, selectedItemIndex) { dialog, which ->
                    // Respond to item chosen
                    selectedItemIndex = which
                    checkedItem = singleItems[which]
                }
                .setNeutralButton("CANCEL") { dialog, which ->
                    // Respond to neutral button press
                }
                .setPositiveButton("OK") { dialog, which ->
                    // Respond to positive button press
                    showSnackbar(view, "${checkedItem} selected")
                }
                .show()
    }

    fun showSnackbar(view: View, msg: String){
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()
    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}