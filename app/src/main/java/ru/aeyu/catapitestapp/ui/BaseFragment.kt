package ru.aeyu.catapitestapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.aeyu.catapitestapp.ui.actions.UiActions

abstract class BaseFragment<BindingClass : ViewBinding, MyViewModel: BaseViewModel> : Fragment() {

    protected abstract val viewModel: MyViewModel
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectUiActions()
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

    private fun showDialog(text: String) {
        AlertDialog.Builder(requireContext()).apply {
            setMessage(text)
            setNeutralButton("Понятно", null)
        }.show()
    }

    private fun showSnackBar(messageText: String) {
        Snackbar.make(requireView(), messageText, Snackbar.LENGTH_SHORT).show()
    }

    private fun collectUiActions(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiActions.collect{action ->
                    when(action){
                        is UiActions.OnError -> showDialog(action.message)
                        is UiActions.OnInformation -> showSnackBar(action.message)
                        is UiActions.OnLoading -> showProgressIndicator(action.isLoading)
                    }
                }
            }
        }
    }

    abstract fun showProgressIndicator(isLoading: Boolean)
}