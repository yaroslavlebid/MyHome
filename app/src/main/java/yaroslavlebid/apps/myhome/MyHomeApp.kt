package yaroslavlebid.apps.myhome

import android.app.Application
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import yaroslavlebid.apps.myhome.utils.MediaLoader

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

        Album.initialize(
            AlbumConfig.newBuilder(this)
                .setAlbumLoader(MediaLoader())
                .build());
    }
}