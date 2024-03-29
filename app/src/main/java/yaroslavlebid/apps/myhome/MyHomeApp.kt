package yaroslavlebid.apps.myhome

import android.app.Application
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import yaroslavlebid.apps.myhome.utils.MediaLoader

class MyHomeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyHomeApp)
            modules(
                viewModelModule,
                repositoryModule,
                utilsModule,
                firebaseModule
            )
        }

        Album.initialize(
            AlbumConfig.newBuilder(this)
                .setAlbumLoader(MediaLoader())
                .build());

        Timber.plant(Timber.DebugTree())
    }
}