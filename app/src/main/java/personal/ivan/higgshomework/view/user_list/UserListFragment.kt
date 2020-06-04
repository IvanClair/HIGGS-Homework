package personal.ivan.higgshomework.view.user_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.android.support.DaggerFragment
import personal.ivan.higgshomework.R
import personal.ivan.higgshomework.databinding.FragmentUserListBinding
import personal.ivan.higgshomework.di.AppViewModelFactory
import personal.ivan.higgshomework.io.model.IoRqStatusModel
import personal.ivan.higgshomework.ui_utils.showIoAlert
import personal.ivan.higgshomework.view_model.MainViewModel
import javax.inject.Inject

class UserListFragment : DaggerFragment() {

    // Data Binding
    private lateinit var binding: FragmentUserListBinding

    // View Model
    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private val viewModel by navGraphViewModels<MainViewModel>(R.id.navigation_graph_main) { viewModelFactory }

    // Adapter
    @Inject
    lateinit var userListAdapter: UserListAdapter

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
        initRecyclerView()
        viewModel.apply {

            // user view holder model list
            userVhModelList.observe(
                viewLifecycleOwner,
                Observer {
                    updateUserListUiWidgetsVisibility(enable = it.status == IoRqStatusModel.LOADING)
                    when (it.status) {
                        IoRqStatusModel.FAIL -> requireActivity().showIoAlert()
                        IoRqStatusModel.SUCCESS -> updateDataSource()
                    }
                })
        }
    }

    // endregion

    // region RecyclerView

    private fun initRecyclerView() {
        binding.recyclerViewUser.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = userListAdapter

            // return transition
//            postponeEnterTransition()
//            viewTreeObserver.addOnPreDrawListener {
//                startPostponedEnterTransition()
//                true
//            }
        }
    }

    private fun updateDataSource() {

    }

    // endregion
}