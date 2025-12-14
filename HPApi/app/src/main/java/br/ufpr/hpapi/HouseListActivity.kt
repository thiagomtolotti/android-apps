package br.ufpr.hpapi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import br.ufpr.hpapi.api.HPApi
import br.ufpr.hpapi.api.HogwartsHouse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HouseListActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var tvError: TextView
    private lateinit var listView: ListView
    private lateinit var hpApi: HPApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_house_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvError = findViewById(R.id.tvListHouseMembersError)
        progressBar = findViewById(R.id.pbListHouseMembers)
        listView = findViewById(R.id.lvHouseMembers)

        val retrofit = Retrofit.Builder().baseUrl("https://hp-api.onrender.com/").addConverterFactory(GsonConverterFactory.create()).build()

        hpApi = retrofit.create(HPApi::class.java)
    }

    fun onListHouseMembersClick(view: View) {
        val radioGroup = findViewById<RadioGroup>(R.id.houseRadioGroup)

         val houseByRadioId = mapOf(
            R.id.rbGryffindor to HogwartsHouse.GRYFFINDOR,
            R.id.rbSlytherin  to HogwartsHouse.SLYTHERIN,
            R.id.rbRavenclaw  to HogwartsHouse.RAVENCLAW,
            R.id.rbHufflepuff to HogwartsHouse.HUFFLEPUFF
        )

        val selectedHouse = houseByRadioId[radioGroup.checkedRadioButtonId]

        if(selectedHouse == null) {
            showError("É necessário escolher uma casa!")
            return
        }

        fetchHouseMembers(selectedHouse)
    }

    private fun fetchHouseMembers(house: HogwartsHouse) {
        showLoading()
        hideContent()
        hideError()

        lifecycleScope.launch {
            try{
                val members = withContext(Dispatchers.IO) {
                    hpApi.listHouseMembers(house.displayName)
                }

                val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(this@HouseListActivity, android.R.layout.simple_list_item_1, members.map {it.name})

                listView.adapter = arrayAdapter
                listView.onItemClickListener =
                    AdapterView.OnItemClickListener {_,_, position, _ ->
                        val memberId = members[position].id

                        val intent = Intent(this@HouseListActivity, CharacterDetailsActivity::class.java)
                        intent.putExtra("id", memberId)
                        startActivity(intent)
                    }


                showContent()
            }catch (e:Exception){
                Log.e("HouseListActivity", "Erro ao carregar membros da casa", e)
                showError()
            }finally {
                hideLoading()
            }
        }
    }

    private fun showLoading() {
        progressBar.visibility = ProgressBar.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = ProgressBar.GONE
    }

    private fun showError(message: String = "Houve um erro ao buscar os membros!") {
        tvError.text = message
        tvError.visibility = TextView.VISIBLE
    }

    private fun hideError() {
        tvError.visibility = TextView.GONE
    }

    private fun showContent() {
        listView.visibility = ListView.VISIBLE
    }

    private fun hideContent() {
        listView.visibility = ListView.GONE
    }
}