package personal.ivan.higgshomework.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.navigation.navGraphViewModels
import dagger.android.support.DaggerFragment
import personal.ivan.higgshomework.R
import personal.ivan.higgshomework.databinding.FragmentRomanToChineseBinding
import personal.ivan.higgshomework.di.AppViewModelFactory
import personal.ivan.higgshomework.ui_utils.UiUtil
import personal.ivan.higgshomework.view_model.MainViewModel
import javax.inject.Inject

class RomanToChineseFragment : DaggerFragment() {

    // Data Binding
    private lateinit var binding: FragmentRomanToChineseBinding

    // View Model
    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private val viewModel by navGraphViewModels<MainViewModel>(R.id.navigation_graph_main) { viewModelFactory }

    // region Life Cycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_roman_to_chinese,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEditText()
    }

    // endregion

    // region Edit Text

    private fun initEditText() {
        binding.textInputEditTextRoman.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                // todo
                updateDisplay(text = "")
                binding.root.requestFocus()
                UiUtil.hideKeyboard(view = textView)
                return@setOnEditorActionListener true
            }
            false
        }
    }

    // endregion

    // region Result Text View

    private fun updateDisplay(text: String) {
        binding.textViewChinese.text = text
    }

    // endregion
}