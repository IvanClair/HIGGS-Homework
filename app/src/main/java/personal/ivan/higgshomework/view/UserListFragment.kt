package personal.ivan.higgshomework.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.navGraphViewModels
import dagger.android.support.DaggerFragment
import personal.ivan.higgshomework.R
import personal.ivan.higgshomework.databinding.FragmentUserListBinding
import personal.ivan.higgshomework.di.AppViewModelFactory
import personal.ivan.higgshomework.view_model.MainViewModel
import javax.inject.Inject

class UserListFragment : DaggerFragment() {

    // Data Binding
    private lateinit var binding: FragmentUserListBinding

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
            R.layout.fragment_user_list,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    // endregion
}