package personal.ivan.higgshomework.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import dagger.android.support.DaggerFragment
import personal.ivan.higgshomework.R
import personal.ivan.higgshomework.binding_model.UserSummaryVhBindingModel
import personal.ivan.higgshomework.databinding.FragmentUserListBinding
import personal.ivan.higgshomework.di.AppViewModelFactory
import personal.ivan.higgshomework.io.model.IoStatus
import personal.ivan.higgshomework.ui_utils.showIoAlert
import personal.ivan.higgshomework.view_model.MainViewModel
import javax.inject.Inject
import javax.inject.Named

class UserListFragment : DaggerFragment() {

    // Data Binding
    private lateinit var binding: FragmentUserListBinding

    // View Model
    @Inject
    lateinit var viewModelFactory: AppViewModelFactory
    private val viewModel by navGraphViewModels<MainViewModel>(R.id.navigation_graph_main) { viewModelFactory }

    // Adapter
    @Inject
    @Named("userListAdapter")
    lateinit var userListAdapter: UserSummaryAdapter

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
        initMenu()
        initRecyclerView()
        viewModel.apply {

            // set up view model
            binding.viewModel = this

            // IO status
            userListIoStatus.observe(
                viewLifecycleOwner,
                Observer {
                    updateUserListUiWidgetsVisibility(loading = it.status == IoStatus.LOADING)
                    if (it.status == IoStatus.FAIL) {
                        activity?.showIoAlert()
                    }
                })

            // user paged list
            userListPagedList.observe(
                viewLifecycleOwner,
                Observer { updateDataSource(dataList = it) }
            )
        }
    }

    // endregion

    // region Menu

    private fun initMenu() {
        binding.toolBar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_translate) {
                findNavController().navigate(UserListFragmentDirections.navigateToRomanToChinese())
                return@setOnMenuItemClickListener true
            }
            false
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
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
    }

    private fun updateDataSource(dataList: PagedList<UserSummaryVhBindingModel>?) {
        (binding.recyclerViewUser.adapter as UserSummaryAdapter).submitList(dataList)
    }

    // endregion
}