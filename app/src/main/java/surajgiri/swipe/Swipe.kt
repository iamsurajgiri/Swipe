package surajgiri.swipe

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import surajgiri.core.di.networkModule
import surajgiri.swipe.di.appModule

class Swipe : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Swipe)
            modules(listOf(appModule,networkModule))
        }
    }
}
