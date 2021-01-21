package com.example.pixabayviewer.view.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.pixabayviewer.R
import com.example.pixabayviewer.data.db.account.Account
import com.example.pixabayviewer.databinding.LoginFragmentBinding
import com.example.pixabayviewer.utils.Resource
import com.example.pixabayviewer.utils.Status
import com.example.pixabayviewer.view.ui.BaseFragment
import es.dmoral.toasty.Toasty
import javax.inject.Inject

class LoginFragment : BaseFragment(R.layout.login_fragment, R.string.login_fragment_label) {
    private val binding: LoginFragmentBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.result.observe(viewLifecycleOwner, Observer { onResult(it) })


        with(binding) {
            login.setOnClickListener {
                viewModel.login(email.text.toString(), password.text.toString())
            }

            register.setOnClickListener {
                findNavController().navigate(R.id.action_Login_to_Register)
            }
        }
    }

    private fun onResult(resource: Resource<Account>) {
        when (resource.status) {
            Status.SUCCESS -> success()
            Status.LOADING -> loading()
            Status.ERROR -> error(resource.message(requireContext()))
        }
    }

    private fun loading() {
        binding.progressBar.root.visibility = View.VISIBLE
    }

    private fun error(message: String?) {
        binding.progressBar.root.visibility = View.GONE
        message?.let { Toasty.error(requireContext(), it, Toast.LENGTH_LONG).show() }
    }

    private fun success() {
        binding.progressBar.root.visibility = View.GONE
        findNavController().navigate(R.id.action_Login_to_Home)
    }
}