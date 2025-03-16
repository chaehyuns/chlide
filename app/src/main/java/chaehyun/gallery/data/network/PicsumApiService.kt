package chaehyun.gallery.data.network

import chaehyun.gallery.data.dto.ImagesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PicsumApiService {
    @GET("v2/list")
    suspend fun getImages(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 30
    ): List<ImagesResponse>

    @GET("id/{id}/info")
    suspend fun getImageDetail(
        @Path("id") id: Long
    ): ImagesResponse
}
