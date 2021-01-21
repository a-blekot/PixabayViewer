package com.example.pixabayviewer.view.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.pixabayviewer.R
import com.example.pixabayviewer.data.db.image.Image
import com.example.pixabayviewer.databinding.ImageDetailFragmentBinding
import com.example.pixabayviewer.utils.Resource
import com.example.pixabayviewer.utils.Status
import com.example.pixabayviewer.view.ui.BaseFragment
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageDetailFragment :
    BaseFragment(R.layout.image_detail_fragment, R.string.image_detail_fragment_label) {
    private val binding: ImageDetailFragmentBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var imageId = 0

    private lateinit var viewModel: ImageDetailViewModel

    private lateinit var loadJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(
            ImageDetailViewModel::class.java
        )
        imageId = ImageDetailFragmentArgs.fromBundle(requireArguments()).imageId
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.result.observe(viewLifecycleOwner, Observer { onResult(it) })
        viewModel.loadImage(imageId)
    }

    override fun onStop() {
        super.onStop()
        if (this::loadJob.isInitialized) {
            loadJob.cancel()
        }
    }

    private fun onResult(resource: Resource<Image>) {
        when (resource.status) {
            Status.ERROR -> resource.message(requireContext())?.let { error(it) }
            else -> success(resource.data!!)
        }
    }

    private fun error(message: String?) {
        message?.let { Toasty.error(requireContext(), it, Toast.LENGTH_LONG).show() }
    }

    private fun success(image: Image) {
        with(binding) {
            showProgress()

            loadJob = viewLifecycleOwner.lifecycleScope.launch {
                loadImage(image.largeImageURL)
            }

            size.text = ImageSizeFormatter.formatMB(image.imageSize)
            type.text = image.type
            tags.text = image.tags

            userName.text = image.userName
            views.text = image.views.toString()
            likes.text = image.likes.toString()
            comments.text = image.comments.toString()
            favorites.text = image.favorites.toString()
            downloads.text = image.downloads.toString()
        }
    }

    private fun loadImage(url: String) {
        Glide.with(this)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    hideProgress()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    hideProgress()
                    return false
                }
            })
            .error(R.drawable.image_not_found)
            .into(binding.largeImage)
    }

    private fun showProgress() {
        binding.progressBar.root.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.progressBar.root.visibility = View.GONE
    }
}