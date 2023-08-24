package com.jones.myapplication

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.jones.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the click listeners directly on the buttons using data binding
        binding.btnComputer.setOnClickListener {
            // Handle button click for "Play With Computer"
            val intent = Intent(this, ComputerActivity::class.java)
            startActivity(intent)
        }

        binding.btnFriends.setOnClickListener {
            // Handle button click for "Play With Friends"
            val intent = Intent(this, FriendsFormActivity::class.java)
            startActivity(intent)
        }

        binding.btnQuit.setOnClickListener {
            // Show a confirmation dialog before quitting
            showQuitConfirmationDialog()
        }
    }

    private fun showQuitConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Quit")
        alertDialogBuilder.setMessage("Are you sure you want to quit?")
        alertDialogBuilder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
            // Handle "Yes" button click - close the MainActivity and exit the app
            finish()
        }
        alertDialogBuilder.setNegativeButton("No") { dialog: DialogInterface, _: Int ->
            // Handle "No" button click - dismiss the dialog and do nothing
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
