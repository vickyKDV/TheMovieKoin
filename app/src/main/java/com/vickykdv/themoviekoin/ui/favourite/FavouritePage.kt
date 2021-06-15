package com.vickykdv.themoviekoin.ui.favourite

import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.vickykdv.themovie.ui.detail.DetailMoviePage
import com.vickykdv.themoviekoin.base.BaseActivity
import com.vickykdv.themoviekoin.database.LocalDataMapping
import com.vickykdv.themoviekoin.database.MovieEntity
import com.vickykdv.themoviekoin.databinding.ActivityDashboardBinding
import com.vickykdv.themoviekoin.ui.adapter.FavouriteAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouritePage: BaseActivity() {

    private val viewModel: FavouriteViewModel by viewModel()

    private val adapter  by lazy {
        FavouriteAdapter { item -> showDetail(item)}
    }

    private val binding  by lazy {
        ActivityDashboardBinding.inflate(layoutInflater)
    }

    override fun setLayout()= binding.root

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        with(window) {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )

        }
        setupUI()
    }


    private fun setupUI(){

        with(binding){
            hide(shimmerMovie)
            hide(fabFav)
            rvData.also {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(this@FavouritePage)
                it.setHasFixedSize(true)
            }
        }

        setupViewModel()

    }

    private fun showDetail(item: MovieEntity) {
        val dataMovie = LocalDataMapping.mapEntityToResponse(item)
        openActivityResult(DetailMoviePage::class.java, extras = {
            this.putParcelable("data",dataMovie)
        })
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupViewModel() {
        viewModel.data.observe(this,(adapter::submitList))
        viewModel.getFavoriteMovie()
    }


}