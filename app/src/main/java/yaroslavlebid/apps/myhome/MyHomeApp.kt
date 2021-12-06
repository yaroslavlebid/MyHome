package yaroslavlebid.apps.myhome

import android.app.Application
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class MyHomeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                viewModelModule,
                repositoryModule,
                utilsModule,
                firebaseModule
            )
        }
    }
}