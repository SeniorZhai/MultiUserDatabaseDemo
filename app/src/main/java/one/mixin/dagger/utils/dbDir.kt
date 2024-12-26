package one.mixin.dagger.utils

import android.content.Context
import java.io.File

fun dbDir(context: Context, identityNumber: String? = null): File {
    val baseDir = File(context.filesDir.parent, "databases")
    val dir = File(baseDir, identityNumber ?: Session.getAccount()?.identityNumber?.toString() ?: "temp")
    if (!dir.exists()) {
        dir.mkdirs()
    }
    return dir
}