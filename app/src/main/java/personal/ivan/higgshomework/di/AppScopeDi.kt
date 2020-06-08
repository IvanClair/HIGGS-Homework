package personal.ivan.higgshomework.di

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import dagger.*
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import personal.ivan.higgshomework.BuildConfig
import personal.ivan.higgshomework.MainApplication
import personal.ivan.higgshomework.R
import personal.ivan.higgshomework.io.db.AppDatabase
import personal.ivan.higgshomework.io.network.GitHubService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import kotlin.reflect.KClass

// region Application Scope Component

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ViewModelModule::class,
        RetrofitModule::class,
        DbModule::class,
        MainFragmentModule::class]
)
interface AppComponent : AndroidInjector<MainApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: MainApplication): AppComponent
    }
}

// endregion

// region View Model

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

// endregion

// region Retrofit

@Module
object RetrofitModule {

    /**
     * Create Retrofit for API call
     */
    @JvmStatic
    @Singleton
    @Provides
    fun provideGitHubService(
        application: MainApplication,
        okHttpClient: OkHttpClient
    ): GitHubService =
        Retrofit.Builder()
            .baseUrl(application.getString(R.string.base_url_git_hub))
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(GitHubService::class.java)

    /**
     * Setting HTTP configs
     */
    @JvmStatic
    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                // log on debug mode
                if (BuildConfig.DEBUG) {
                    addInterceptor(interceptor)
                }
            }
            .build()

    /**
     * Choose to log HTTP detailed information
     */
    @JvmStatic
    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
}

// endregion

// region Database

@Module
object DbModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideAppDb(application: MainApplication) =
        Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                application.packageName
            )
            .fallbackToDestructiveMigration()
            .build()
}

// endregion

// region Glide

@com.bumptech.glide.annotation.GlideModule
class MyGlideModule : AppGlideModule() {

    /**
     * Set up request and transition for Glide
     */
    override fun applyOptions(
        context: Context,
        builder: GlideBuilder
    ) {
        builder
            .setDefaultRequestOptions(
                RequestOptions()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .fallback(R.drawable.ic_launcher_foreground)
                    .centerCrop()
            )
            .setDefaultTransitionOptions(
                Drawable::class.java,
                DrawableTransitionOptions.withCrossFade()
            )
    }
}

// endregion