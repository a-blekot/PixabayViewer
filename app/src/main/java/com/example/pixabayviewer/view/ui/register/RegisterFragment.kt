package com.example.pixabayviewer.view.ui.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.pixabayviewer.R
import com.example.pixabayviewer.databinding.RegisterFragmentBinding
import com.example.pixabayviewer.utils.Resource
import com.example.pixabayviewer.utils.Status
import com.example.pixabayviewer.view.ui.BaseFragment
import es.dmoral.toasty.Toasty
import javax.inject.Inject

class RegisterFragment :
    BaseFragment(R.layout.register_fragment, R.string.register_fragment_label) {
    private val binding: RegisterFragmentBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            register.setOnClickListener {
                viewModel.register(
                    email.text.toString(),
                    password.text.toString(),
                    age.text.toString()
                )
            }

            viewModel.emailError.observe(viewLifecycleOwner, Observer {
                emailNotification.showIfOrHide { it }
            })

            viewModel.passwordError.observe(viewLifecycleOwner, Observer {
                passwordNotification.showIfOrHide { it }
            })

            viewModel.ageError.observe(viewLifecycleOwner, Observer {
                ageNotification.showIfOrHide { it }
            })

            email.afterTextChanged { viewModel.emailChanged(it) }
            password.afterTextChanged { viewModel.passwordChanged(it) }
            age.afterTextChanged { viewModel.ageChanged(it) }
        }

        viewModel.result.observe(viewLifecycleOwner, Observer { onResult(it) })
    }

    private fun onResult(resource: Resource<Any>) {
        when (resource.status) {
            Status.ERROR -> resource.message(requireContext())?.let { error(it) }
            else -> success()
        }
    }

    private fun error(message: String?) {
        message?.let { Toasty.error(requireContext(), it, Toast.LENGTH_LONG).show() }
    }

    private fun success() {
        Toasty.success(
            requireContext(),
            getString(R.string.successful_registration, binding.email.text.toString()),
            Toast.LENGTH_SHORT
        ).show()

        findNavController().popBackStack()
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    private inline fun View.showIfOrHide(predicate: () -> Boolean) {
        visibility = if (predicate()) View.VISIBLE else View.GONE
    }
}