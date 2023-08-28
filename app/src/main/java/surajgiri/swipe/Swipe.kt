package surajgiri.swipe

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import surajgiri.core.di.productModule
import surajgiri.swipe.di.appModule
import surajgiri.swipe.utils.FirebaseDefaultAnalytics

class Swipe : Application() {
    override fun onCreate() {
        super.onCreate()



        NotificationPreferenceHelper.init(this)
        FirebaseDefaultAnalytics.setFirebaseDefaults()
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())

        startKoin {
            androidContext(this@Swipe)
            modules(listOf(appModule,productModule))
        }
    }
}

