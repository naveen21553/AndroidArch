package com.mba.sample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mba.sample.R
import com.mba.sample.model.Country
import com.mba.sample.viewmodel.ListViewModel

class MainActivity : AppCompatActivity() {

    private val countryAdapter = RecyclerListViewAdapter(arrayListOf())
    private lateinit var viewModel: ListViewModel
    private lateinit var countriesList: RecyclerView
    private lateinit var errorTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        viewModel.refresh()

        countriesList = findViewById(R.id.countriesList)
        countriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countryAdapter
        }

        errorTextView = findViewById(R.id.errorTextView)
        progressBar = findViewById(R.id.progressBar)

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
            swipeRefreshLayout.isRefreshing = false
            Toast.makeText(this, "Up to date", Toast.LENGTH_SHORT)
        }

        observeViewModel()

    }


    private fun observeViewModel() {

        viewModel.countries.observe(this, Observer { countries: List<Country>? ->
            countries?.let { countryAdapter.update(it) }
        })

        viewModel.countryLoadError.observe(this, Observer { isError: Boolean? ->
            isError?.let { errorTextView.visibility = if (it) View.VISIBLE else View.GONE }
        })

        viewModel.loading.observe(this, Observer { isLoading: Boolean? ->
            isLoading?.let {
                progressBar.visibility = if (it) View.VISIBLE else View.GONE
                if (it)
                    errorTextView.visibility = View.GONE
            }
        })

    }
}
