package br.ufpr.hpapi

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterDetailsActivity : AppCompatActivity() {
    private lateinit var etId: EditText
    private lateinit var tvName: TextView
    private lateinit var tvHouse: TextView
    private lateinit var tvError: TextView
    private lateinit var tvLoading: TextView
    private lateinit var hpApi: HPApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_character_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etId = findViewById(R.id.editTextId)

        tvName = findViewById(R.id.tvName)
        tvHouse = findViewById(R.id.tvError)
        tvError = findViewById(R.id.tvError)
        tvLoading= findViewById(R.id.tvLoading)

        val retrofit = Retrofit.Builder().baseUrl("https://hp-api.onrender.com/").addConverterFactory(GsonConverterFactory.create()).build()

        hpApi = retrofit.create(HPApi::class.java)
    }

    fun getCharacterDetails(view: View) {
        hideError()
        hideDetails()

        val id = etId.text.toString()

        if(id.isNotEmpty()) {
            getCharacterFromApi(id)
        }else{
            showError("Forneça um id válido")
        }
    }

    private fun getCharacterFromApi(id: String) {
        lifecycleScope.launch {
            try {
                showLoading()
                hideError()

                val character = withContext(Dispatchers.IO) {
                    hpApi.getCharacterDetails(id)
                }

                tvName.text =   """Nome: ${character[0].name}""".trimIndent()
                tvHouse.text =  """Casa: ${character[0].house}""".trimIndent()

                showDetails()
            }catch(e:Exception) {
                Log.e("CharacterDetailsActivity", "Erro ao buscar o personagem", e)
                hideDetails()
                showError()
            }finally {
                hideLoading()
            }
        }
    }

    private fun showLoading() {
        tvLoading.visibility = TextView.VISIBLE
    }

    private fun hideLoading() {
        tvLoading.visibility = TextView.GONE
    }

    private fun showError(message: String = "Houve um erro ao buscar o personagem!") {
        tvError.text = message
        tvError.visibility = TextView.VISIBLE
    }

    private fun hideError() {
        tvError.visibility = TextView.GONE
    }

    private fun showDetails() {
        tvName.visibility = TextView.VISIBLE
        tvHouse.visibility = TextView.VISIBLE
    }

    private fun hideDetails() {
        tvName.visibility = TextView.GONE
        tvHouse.visibility = TextView.GONE
    }
}