package com.khuram.mvi.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.khuram.mvi.R
import com.khuram.mvi.databinding.FragmentDetailBinding
import com.khuram.mvi.ui.main.MainViewModel


class DetailFragment: Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            it.onBackPressedDispatcher.addCallback {
                it.supportFragmentManager.popBackStack()
                remove()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDetails()
    }

    private fun setDetails() {
        val breed = viewModel.selectedItem

        binding.breedName.text = breed.name

        if(!breed.bredFor.isNullOrEmpty()) {
            binding.breedBredFor.text = breed.bredFor
            binding.breedBredFor.visibility = View.VISIBLE
            binding.breedBredForTitle.visibility = View.VISIBLE
        }

        if(!breed.breedGroup.isNullOrEmpty()) {
            binding.breedGroup.text = breed.breedGroup
            binding.breedGroup.visibility = View.VISIBLE
            binding.breedGroupTitle.visibility = View.VISIBLE
        }

        if(!breed.lifeSpan.isNullOrEmpty()) {
            binding.breedLifeSpan.text = breed.lifeSpan
            binding.breedLifeSpan.visibility = View.VISIBLE
            binding.breedLifeSpanTitle.visibility = View.VISIBLE
        }

        if(!breed.temperament.isNullOrEmpty()) {
            binding.breedTemperament.text = breed.temperament
            binding.breedTemperament.visibility = View.VISIBLE
            binding.breedTemperamentTitle.visibility = View.VISIBLE
        }

        if(!breed.origin.isNullOrEmpty()) {
            binding.breedOrigin.text = breed.origin
            binding.breedOrigin.visibility = View.VISIBLE
            binding.breedOriginTitle.visibility = View.VISIBLE
        }

        Glide.with(this@DetailFragment)
            .load(breed.image.url)
            .placeholder(R.drawable.dog)
            .into(binding.imageView)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}