package personal.ivan.higgshomework.di

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import personal.ivan.higgshomework.io.db.AppDatabase
import personal.ivan.higgshomework.io.db.GitHubUserDetailsDao
import personal.ivan.higgshomework.io.network.GitHubService
import personal.ivan.higgshomework.repository.GitHubRepository
import personal.ivan.higgshomework.view.*
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

    @MainScope
    @ContributesAndroidInjector(
        modules = [
            MainViewModelModule::class,
            MainScopeModule::class]
    )
    abstract fun contributeRomanToChineseFragment(): RomanToChineseFragment
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

    /**
     * GitHub repository
     */
    @JvmStatic
    @MainScope
    @Provides
    fun provideGitHubRepository(
        service: GitHubService,
        config: PagedList.Config,
        dao: GitHubUserDetailsDao
    ): GitHubRepository = GitHubRepository(
        service = service,
        pagedListConfig = config,
        userDetailsDao = dao
    )

    /**
     * Paged list configuration
     */
    @JvmStatic
    @MainScope
    @Provides
    fun providePagedListConfig(): PagedList.Config =
        PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(GitHubRepository.PAGE_LIMIT)
            .setPrefetchDistance(GitHubRepository.PREFETCH_DISTANCE)
            .build()

    /**
     * GitHub user details DAO
     */
    @JvmStatic
    @MainScope
    @Provides
    fun provideUserDetailsDao(db: AppDatabase): GitHubUserDetailsDao = db.gitHubUserDetailsDao()
}

// endregion