package br.ufpr.hpapi

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import br.ufpr.hpapi.api.HPApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TeachersListActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var tvError: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var hpApi: HPApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_teachers_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listView = findViewById(R.id.lvTeachers)
        tvError = findViewById(R.id.tvListTeachersError)
        progressBar= findViewById(R.id.progressBar)

        val retrofit = Retrofit.Builder().baseUrl("https://hp-api.onrender.com/").addConverterFactory(GsonConverterFactory.create()).build()

        hpApi = retrofit.create(HPApi::class.java)

        listTeachers()
    }

    private fun listTeachers() {
        showLoading()
        hideContent()
        hideError()

        lifecycleScope.launch {
            try {
                val teachers = withContext(Dispatchers.IO) {
                    hpApi.listStaff()
                }

                val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(this@TeachersListActivity, android.R.layout.simple_list_item_1, teachers.map {it.name})

                listView.adapter = arrayAdapter

                showContent()
            }catch(e: Exception) {
                Log.e("TeacherListActivity", "Erro ao buscar professores", e)
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

    private fun showError() {
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