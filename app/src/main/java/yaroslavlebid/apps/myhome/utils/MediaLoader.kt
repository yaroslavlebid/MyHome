package yaroslavlebid.apps.myhome.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.AlbumLoader
import yaroslavlebid.apps.myhome.R

class MediaLoader : AlbumLoader {
    override fun load(imageView: ImageView, albumFile: AlbumFile) {
        load(imageView, albumFile.getPath());
    }

    override fun load(imageView: ImageView, url: String) {
        Glide.with(imageView.getContext())
            .load(url)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .into(imageView);
    }
}