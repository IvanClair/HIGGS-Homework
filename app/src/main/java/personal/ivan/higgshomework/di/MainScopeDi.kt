package personal.ivan.higgshomework.di

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import personal.ivan.higgshomework.binding_model.UserListPageBindingModel
import personal.ivan.higgshomework.view.UserDetailsFragment
import personal.ivan.higgshomework.view.user_list.UserListAdapter
import personal.ivan.higgshomework.view.user_list.UserListFragment
import personal.ivan.higgshomework.view_model.MainViewModel
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
    fun provideUserListAdapter(viewModel: MainViewModel): UserListAdapter =
        UserListAdapter(viewModel = viewModel)
}

// endregion