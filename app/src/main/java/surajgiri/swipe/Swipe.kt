package surajgiri.swipe

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import surajgiri.core.di.productModule
import surajgiri.swipe.di.appModule
import surajgiri.swipe.utils.FirebaseDefaultAnalytics

class Swipe : Application() {
    override fun onCreate() {
        super.onCreate()


        FirebaseDefaultAnalytics.setFirebaseDefaults()

        startKoin {
            androidContext(this@Swipe)
            modules(listOf(appModule,productModule))
        }
    }
}
