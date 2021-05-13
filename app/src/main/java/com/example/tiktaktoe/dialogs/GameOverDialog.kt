package com.example.tiktaktoe.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.tiktaktoe.R

class GameOverDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder: AlertDialog.Builder = AlertDialog.Builder(it)

            builder.apply {
                setTitle("Game over")
                setNeutralButton("Menu") { dialog, which ->
                    findNavController().navigate(R.id.action_gameOverDialog_to_menuFragment)
                }
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}