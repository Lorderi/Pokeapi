package ru.lorderi.pokeapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.lorderi.pokeapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
