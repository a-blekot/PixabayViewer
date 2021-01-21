package com.example.pixabayviewer.view.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.pixabayviewer.R
import com.example.pixabayviewer.data.db.image.Image
import com.example.pixabayviewer.databinding.HomeFragmentBinding
import com.example.pixabayviewer.utils.Resource
import com.example.pixabayviewer.utils.Status
import com.example.pixabayviewer.view.ui.BaseFragment
import es.dmoral.toasty.Toasty
import javax.inject.Inject

class HomeFragment : BaseFragment(R.layout.home_fragment, R.string.home_fragment_label),
    HomeAdapter.Listener {
    private val binding: HomeFragmentBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: HomeViewModel

    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HomeAdapter(this)
        binding.recyclerView.apply {
            adapter = this@HomeFragment.adapter
            addItemDecoration(DividerItemDecoration(binding.recyclerView.context, VERTICAL))
        }

        viewModel.images.observe(viewLifecycleOwner, Observer { onResult(it) })
        viewModel.isConnected.observe(viewLifecycleOwner, Observer { onConnectionChanged(it) })
    }

    private fun onResult(resource: Resource<List<Image>>) {
        when (resource.status) {
            Status.SUCCESS -> success(resource.data!!)
            Status.LOADING -> loading()
            Status.ERROR -> error(resource.message(requireContext()))
        }
        showEmptyLayout = resource.data.isNullOrEmpty()
    }

    private fun loading() {
        binding.progressBar.root.visibility = View.VISIBLE
    }

    private fun error(message: String?) {
        binding.progressBar.root.visibility = View.GONE
        message?.let { Toasty.error(requireContext(), it, Toast.LENGTH_LONG).show() }
    }

    private var showEmptyLayout: Boolean = false
        set(value) {
            field = value
            if (value) {
                binding.emptyContainer.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.emptyContainer.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        }

    private fun success(images: List<Image>?) {
        binding.progressBar.root.visibility = View.GONE
        images?.let { adapter.dataset = it }
    }

    private fun onConnectionChanged(isConnected: Boolean) {
        if (showEmptyLayout && isConnected) {
            viewModel.tryLoad()
            viewModel.images.observe(viewLifecycleOwner, Observer { onResult(it) })
        }
    }

    override fun onSelected(id: Int) {
        val action = HomeFragmentDirections.actionHomeToImageDetail()
        action.imageId = id
        findNavController().navigate(action)
    }
}