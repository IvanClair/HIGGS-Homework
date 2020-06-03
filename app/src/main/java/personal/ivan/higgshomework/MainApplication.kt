package personal.ivan.higgshomework

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import personal.ivan.higgshomework.di.DaggerAppComponent

class MainApplication : DaggerApplication() {

    // region Dagger

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.factory().create(this)

    // endregion
}