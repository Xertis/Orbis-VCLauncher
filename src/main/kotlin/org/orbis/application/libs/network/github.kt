package org.orbis.application.libs.network

import org.json.JSONArray
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URI
import java.time.ZonedDateTime

data class ReleaseFile(
    val name: String,
    val downloadUrl: String
)

data class Release(
    val name: String,
    val publishedAt: ZonedDateTime,
    val assets: List<ReleaseFile>
)


object Github {
    fun downloadFileWithProgress(
        fileData: ReleaseFile,
        destinationPath: String,
        onProgressUpdate: (Double) -> Unit,
        onEnd: (Boolean) -> Unit
    ) {
        Thread {
            val url = URI(fileData.downloadUrl).toURL()
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.connectTimeout = 15_000
            connection.readTimeout = 60_000

            val responseCode = connection.responseCode
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw RuntimeException("Ошибка скачивания: HTTP $responseCode")
            }

            val fileSize = connection.contentLengthLong
            if (fileSize <= 0) {
                println("Не удалось получить размер файла. Прогресс не будет отображаться.")
                onProgressUpdate(0.0)
                onEnd(false)
            }

            val destinationFile = File(destinationPath)
            destinationFile.parentFile?.mkdirs()

            var downloadedBytes: Long = 0
            val buffer = ByteArray(4096)
            var bytesRead: Int

            connection.inputStream.use { inputStream ->
                FileOutputStream(destinationFile).use { outputStream ->
                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                        downloadedBytes += bytesRead

                        if (fileSize > 0) {
                            val progress = (downloadedBytes.toDouble() / fileSize.toDouble()) * 100.0
                            onProgressUpdate(progress)
                        }
                    }
                }
            }
            onProgressUpdate(100.0)
            onEnd(true)
        }.start()
    }

    fun getReleases(): List<Release> {
        val allReleases = mutableListOf<Release>()
        var urlString: String? = "https://api.github.com/repos/MihailRis/voxelcore/releases"

        while (urlString != null) {
            val url = URI(urlString).toURL()
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.setRequestProperty("Accept", "application/vnd.github+json")
            connection.connectTimeout = 10_000
            connection.readTimeout = 10_000

            val responseCode = connection.responseCode
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw RuntimeException("GitHub API error: $responseCode")
            }

            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(response)

            allReleases.addAll(parseReleases(jsonArray))

            val linkHeader = connection.getHeaderField("Link")
            urlString = extractNextPageUrl(linkHeader)
        }

        return allReleases
    }

    private fun extractNextPageUrl(linkHeader: String?): String? {

        if (linkHeader.isNullOrEmpty()) return null

        val regex = Regex("<(.*?)>; rel=\"next\"")
        val match = regex.find(linkHeader)

        return match?.groupValues?.get(1)
    }
}

private fun parseReleases(jsonArray: JSONArray): List<Release> {
    val releases = mutableListOf<Release>()

    for (i in 0 until jsonArray.length()) {
        val releaseJson = jsonArray.getJSONObject(i)

        val name = releaseJson.getString("name")
        val publishedAtString = releaseJson.getString("published_at")
        val publishedAt = ZonedDateTime.parse(publishedAtString)

        val assetsJsonArray = releaseJson.getJSONArray("assets")
        val assetsList = mutableListOf<ReleaseFile>()

        for (j in 0 until assetsJsonArray.length()) {
            val assetJson = assetsJsonArray.getJSONObject(j)
            val fileName = assetJson.getString("name")
            val downloadUrl = assetJson.getString("browser_download_url")

            assetsList.add(ReleaseFile(fileName, downloadUrl))
        }

        releases.add(
            Release(
                name = name,
                publishedAt = publishedAt,
                assets = assetsList
            )
        )
    }

    return releases
}
