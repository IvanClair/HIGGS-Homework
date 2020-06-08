package personal.ivan.higgshomework.di

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import personal.ivan.higgshomework.io.network.GitHubService
import personal.ivan.higgshomework.repository.GitHubRepository
import personal.ivan.higgshomework.view.SearchUsersFragment
import personal.ivan.higgshomework.view.UserDetailsFragment
import personal.ivan.higgshomework.view.UserListFragment
import personal.ivan.higgshomework.view.UserSummaryAdapter
import personal.ivan.higgshomework.view_model.MainViewModel
import javax.inject.Named
import javax.inject.Scope

// region Scope

@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class MainScope

// endregion

// region Sub Component

@Suppress("unused")
@Module
abstract class MainFragmentModule {

    @MainScope
    @ContributesAndroidInjector(
        modules = [
            MainViewModelModule::class,
            MainScopeModule::class]
    )
    abstract fun contributeUserListFragment(): UserListFragment

    @MainScope
    @ContributesAndroidInjector(
        modules = [
            MainViewModelModule::class,
            MainScopeModule::class]
    )
    abstract fun contributeSearchUsersFragment(): SearchUsersFragment

    @MainScope
    @ContributesAndroidInjector(
        modules = [
            MainViewModelModule::class,
            MainScopeModule::class]
    )
    abstract fun contributeUserDetailsFragment(): UserDetailsFragment
}

// endregion

// region View Model

@Suppress("unused")
@Module
abstract class MainViewModelModule {

    @MainScope
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}

// endregion

// region Main Scope Module

@Module
object MainScopeModule {

    /**
     * Provide user list adapter
     */
    @JvmStatic
    @MainScope
    @Provides
    @Named("userListAdapter")
    fun provideUserListAdapter(viewModel: MainViewModel): UserSummaryAdapter =
        UserSummaryAdapter(viewModel = viewModel)

    /**
     * Provide user list adapter
     */
    @JvmStatic
    @MainScope
    @Provides
    @Named("searchUsersAdapter")
    fun provideSearchUsersAdapter(viewModel: MainViewModel): UserSummaryAdapter =
        UserSummaryAdapter(viewModel = viewModel)

    @JvmStatic
    @MainScope
    @Provides
    fun provideGitHubRepository(
        service: GitHubService,
        config: PagedList.Config
    ): GitHubRepository =
        GitHubRepository(service = service, pagedListConfig = config)

    @JvmStatic
    @MainScope
    @Provides
    fun providePagedListConfig(): PagedList.Config =
        PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(GitHubRepository.PAGE_LIMIT)
            .setPrefetchDistance(GitHubRepository.PREFETCH_DISTANCE)
            .build()
}

// endregion