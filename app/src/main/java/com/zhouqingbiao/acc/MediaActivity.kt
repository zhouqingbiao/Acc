package com.zhouqingbiao.acc

import android.app.Activity
import android.content.Intent
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Bundle

class MediaActivity : Activity() {
    var mediaProjectionManager: MediaProjectionManager? = null
    var mediaProjection: MediaProjection? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaProjectionManager =
            getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    mediaProjection = mediaProjectionManager?.getMediaProjection(resultCode, data)
                }
                finish()
            }
        }
    }
}