package one.mixin.dagger.utils

import android.content.Context
import java.io.File

fun dbDir(context: Context, identityNumber: String): File {
    val baseDir = File(context.filesDir.parent, "databases")
    val dir = File(baseDir, identityNumber)
    if (!dir.exists()) {
        dir.mkdirs()
    }
    return dir
}