package ru.aeyu.catapitestapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<BindingClass : ViewBinding> : Fragment() {

    private var _binding: BindingClass? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = getBindingInstance(inflater, container, false)

        return binding.root
    }

    abstract fun getBindingInstance(
        inflater: LayoutInflater,
        container: ViewGroup?,
        b: Boolean
    ): BindingClass?

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun showDialog(text: String) {
        AlertDialog.Builder(requireContext()).apply {
            setMessage(text)
            setNeutralButton("Понятно", null)
        }.show()
    }

    protected fun showSnackBar(messageText: String) {
        Snackbar.make(binding.root, messageText, Snackbar.LENGTH_SHORT).show()
    }
}