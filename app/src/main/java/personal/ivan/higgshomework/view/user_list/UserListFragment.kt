package personal.ivan.higgshomework.view.user_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import dagger.android.support.DaggerFragment
import personal.ivan.higgshomework.R
import personal.ivan.higgshomework.databinding.FragmentUserListBinding
import personal.ivan.higgshomework.di.AppViewModelFactory
import personal.ivan.higgshomework.io.model.GitHubUserSummary
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
        binding =
            DataBindingUtil.inflate(
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

            // set up view model
            binding.viewModel = this

            // IO status of get user API
            getUserIoStatus.observe(
                viewLifecycleOwner,
                Observer {
                    updateUserListUiWidgetsVisibility(loading = it.status == IoRqStatusModel.LOADING)
                    if (it.status == IoRqStatusModel.FAIL) {
                        activity?.showIoAlert()
                    }
                })

            // user paged list
            getUserPagedList.observe(
                viewLifecycleOwner,
                Observer { updateDataSource(it) }
            )
        }
    }

    // endregion

    // region RecyclerView

    private fun initRecyclerView() {
        binding.recyclerViewUser.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3)
            adapter = userListAdapter

            // return transition
//            postponeEnterTransition()
//            viewTreeObserver.addOnPreDrawListener {
//                startPostponedEnterTransition()
//                true
//            }
        }
    }

    private fun updateDataSource(dataList: PagedList<GitHubUserSummary>?) {
        (binding.recyclerViewUser.adapter as UserListAdapter).submitList(dataList)
    }

    // endregion
}