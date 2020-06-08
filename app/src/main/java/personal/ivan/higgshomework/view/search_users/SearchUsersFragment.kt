package personal.ivan.higgshomework.view.search_users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import dagger.android.support.DaggerFragment
import personal.ivan.higgshomework.R
import personal.ivan.higgshomework.binding_model.UserSummaryVhBindingModel
import personal.ivan.higgshomework.databinding.FragmentSearchUsersBinding
import personal.ivan.higgshomework.di.AppViewModelFactory
import personal.ivan.higgshomework.io.model.IoStatus
import personal.ivan.higgshomework.ui_utils.UiUtil
import personal.ivan.higgshomework.ui_utils.showIoAlert
import personal.ivan.higgshomework.ui_utils.showOrHide
import personal.ivan.higgshomework.view_model.MainViewModel
import javax.inject.Inject

class SearchUsersFragment : DaggerFragment() {

    // Data Binding
    private lateinit var binding: FragmentSearchUsersBinding

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
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_search_users,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEditText()
        initRecyclerView()
        viewModel.apply {

            // IO status
            ioStatus.observe(
                viewLifecycleOwner,
                Observer {
                    binding.progressBar showOrHide (it.status == IoStatus.LOADING)
                    if (it.status == IoStatus.FAIL) {
                        activity?.showIoAlert()
                    }
                })

            // clear keyboard focus
            clearFocusTrigger.observe(
                viewLifecycleOwner,
                Observer {
                    binding.root.apply {
                        requestFocus()
                        UiUtil.hideKeyboard(view = this)
                    }
                }
            )

            // user paged list
            getUserPagedList.observe(
                viewLifecycleOwner,
                Observer { updateDataSource(it) }
            )
        }
    }

    // endregion

    // region Search Edit Text

    private fun initEditText() {
        binding.textInputEditText.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.startSearchUsers(query = textView.text.toString())
                binding.root.requestFocus()
                UiUtil.hideKeyboard(view = textView)
                return@setOnEditorActionListener true
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
//            adapter = userListAdapter

            // return transition
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
    }

    private fun updateDataSource(dataList: PagedList<UserSummaryVhBindingModel>?) {
//        (binding.recyclerViewUser.adapter as UserListAdapter).submitList(dataList)
    }

    // endregion
}