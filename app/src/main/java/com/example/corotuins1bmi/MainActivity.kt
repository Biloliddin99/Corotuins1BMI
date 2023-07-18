package com.example.corotuins1bmi

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.corotuins1bmi.databinding.ActivityMainBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            binding.btnSave.isEnabled = false
            GlobalScope.launch(Dispatchers.Main) {
                val result = checkHealthy(
                    calculation(
                        binding.edt1.text.toString().toInt(),
                        binding.edt2.text.toString().toInt()
                    )
                )
                binding.tv1.text = result
                binding.btnSave.isEnabled = true
            }
        }

    }


    suspend fun calculation(height: Int, weight: Int): Double {
        return GlobalScope.async(Dispatchers.Default) {
            val bmi = weight.toDouble() * 10000 / (height * height.toDouble())
            bmi
        }.await()
    }

     suspend fun checkHealthy(bmi: Double): String {
        return GlobalScope.async(Dispatchers.IO) {
            if (bmi < 18.5) {
                GlobalScope.launch(Dispatchers.Main) {
                    binding.root.setBackgroundColor(Color.BLUE)
                }
                "Yengil vazn"
            } else if (bmi < 25.0) {
                GlobalScope.launch(Dispatchers.Main) {
                    binding.root.setBackgroundColor(Color.GREEN)
                }
                "Normal vazn"
            } else if (bmi < 30.0) {
                GlobalScope.launch(Dispatchers.Main) {
                    binding.root.setBackgroundColor(Color.YELLOW)
                }
                "Semiz"
            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    binding.root.setBackgroundColor(Color.RED)
                }
                "Nosog'lom"
            }
        }.await()
    }
}