package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculatorapp.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var latnum =false
    var stateerror = false
    var lastDot = false
    private lateinit var expression:Expression
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onEqualClick(view: View) {
        onEqual()

    }
    fun onDigitClick(view: View) {
        if (stateerror){
            binding.input.text=(view as Button).text
            stateerror=false
        }else{
            binding.input.append((view as Button).text)
        }
        latnum=true
        onEqual()
    }
    fun onAllclearClick(view: View) {
        binding.input.text=""
        binding.output.text=""
        latnum =false
        stateerror = false
        lastDot = false
        binding.output.visibility= View.GONE

    }
    fun onOperationClick(view: View) {
        if (!stateerror && latnum){
            binding.input.append((view as Button).text)
            lastDot = false
            latnum = false
            onEqual()
        }
    }
    fun onBackClick(view: View) {
        binding.input.text=binding.input.text.dropLast(1)
        try {
            val lastchar =binding.input.text.toString().last()
            if (lastchar.isDigit()){
                onEqual()
            }
        }catch (e:Exception){
            binding.output.text=""
            binding.output.visibility=View.GONE
            Log.e("error last char ", e.toString())
        }
    }
    fun onClearClick(view: View) {
        binding.input.text=""
        latnum=false
    }
    fun onEqual(){
        if (latnum && !stateerror ){
            val txt = binding.input.text.toString()
            expression=ExpressionBuilder(txt).build()
            try {
                val result = expression.evaluate()
                binding.output.visibility=View.VISIBLE
                binding.output.text=result.toString()
            }catch (ex:ArithmeticException){
                Log.e("error",ex.toString())
                binding.output.text = "error"
                latnum =true
                stateerror = true
            }
        }
    }
}