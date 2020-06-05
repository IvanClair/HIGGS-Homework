package personal.ivan.higgshomework.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import androidx.transition.TransitionInflater
import dagger.android.support.DaggerFragment
import personal.ivan.higgshomework.R
import personal.ivan.higgshomework.databinding.FragmentUserDetailsBinding
import personal.ivan.higgshomework.di.AppViewModelFactory
import personal.ivan.higgshomework.io.model.IoStatus
import personal.ivan.higgshomework.ui_utils.showOrHide
import personal.ivan.higgshomework.view_model.MainViewModel
import javax.inject.Inject

class UserDetailsFragment : DaggerFragment() {

    // Data Binding
    private lateinit var binding: FragmentUserDetailsBinding

    // View Model
    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private val viewModel by navGraphViewModels<MainViewModel>(R.id.navigation_graph_main) { viewModelFactory }

    // Argument
    private val mArguments by navArgs<UserDetailsFragmentArgs>()

    // region Life Cycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // override shared element transition
        sharedElementEnterTransition =
            TransitionInflater
                .from(context)
                .inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user_details,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // set up shared element for transition
        binding.imageViewAvatar.transitionName = mArguments.username
        // observe live data
        viewModel.apply {
            // set view model
            binding.viewModel = this

            // IO status
            ioStatus.observe(
                viewLifecycleOwner,
                Observer { binding.progressBar showOrHide (it.status == IoStatus.LOADING) })

            // user details binding model
//            userDetailsPageBindingModel.observe(
//                viewLifecycleOwner,
//                Observer { binding.viewModel = this}
//            )
        }
    }

    // endregion
}