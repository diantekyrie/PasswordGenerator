package com.example.passwordgenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvPassword: TextView = findViewById(R.id.tvPassword)
        val btnGenerate: Button = findViewById(R.id.btnGenerate)
        val btnCopy: Button = findViewById(R.id.btnCopy)

        // Generate password
        btnGenerate.setOnClickListener {
            val password = generateStrongPassword()
            tvPassword.text = password
        }

        // Copy password to clipboard
        btnCopy.setOnClickListener {
            val password = tvPassword.text.toString()
            if (password.isNotEmpty()) {
                copyToClipboard(password)
                Toast.makeText(this, "Password copied to clipboard!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No password to copy!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generateStrongPassword(): String {
        val upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val lowerCase = "abcdefghijklmnopqrstuvwxyz"
        val numbers = "0123456789"
        val specialChars = "!@#$%^&*()-_+=<>?/"

        val allChars = upperCase + lowerCase + numbers + specialChars
        val passwordLength = 12

        return (1..passwordLength).map {
            allChars[Random.nextInt(allChars.length)]
        }.shuffled().joinToString("")
    }

    private fun copyToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Password", text)
        clipboard.setPrimaryClip(clip)
    }
}
