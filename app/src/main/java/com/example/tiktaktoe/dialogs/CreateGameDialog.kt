package com.example.tiktaktoe.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.tiktaktoe.R
import com.example.tiktaktoe.databinding.DialogCreateGameBinding

class CreateGameDialog : DialogFragment() {

    private lateinit var listener: GameDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder: AlertDialog.Builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val binding = DialogCreateGameBinding.inflate(inflater)

            builder.apply {
                setTitle("Create Game")
                setPositiveButton("Create") { dialog, which ->
                    if(binding.username.text.toString() != "") {
                        listener.onDialogCreateGame(binding.username.text.toString())
                        findNavController().navigate(R.id.action_menuFragment_to_gameFragment)
                    }
                }
                setNegativeButton("Cancel") { dialog, which ->
                    dialog.cancel()
                }
                setView(binding.root)
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as GameDialogListener
        } catch (e:ClassCastException){
            throw ClassCastException(("$context must implement GameDialogListener"))
        }

    }
}