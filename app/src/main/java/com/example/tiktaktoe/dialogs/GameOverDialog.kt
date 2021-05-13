package com.example.tiktaktoe.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.tiktaktoe.R

class GameOverDialog(s: String) : DialogFragment() {

    private var winner :String = s

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder: AlertDialog.Builder = AlertDialog.Builder(it)

            builder.apply {
                setTitle("Winner $winner")
                setNeutralButton("Close") { dialog, which ->
                    dialog.cancel()
                }
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}