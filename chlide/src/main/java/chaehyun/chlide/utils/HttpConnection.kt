package chaehyun.chlide.utils

import java.net.HttpURLConnection
import java.net.URL

fun createHttpConnection(url: String): HttpURLConnection {
        return (URL(url).openConnection() as HttpURLConnection).apply {
            connectTimeout = 5000
            readTimeout = 5000
        }
    }
