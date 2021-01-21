package com.example.pixabayviewer.data

import com.example.pixabayviewer.R
import com.example.pixabayviewer.data.db.image.Image
import com.example.pixabayviewer.data.db.image.ImageDao
import com.example.pixabayviewer.data.network.PixabayApi
import com.example.pixabayviewer.data.network.PixabayResponse
import com.example.pixabayviewer.data.network.ResponseMapper
import com.example.pixabayviewer.data.network.state.NetworkMonitor
import com.example.pixabayviewer.domain.AccountRepository
import com.example.pixabayviewer.domain.ImageRepository
import com.example.pixabayviewer.utils.Resource
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.random.Random

@Suppress("TooManyFunctions")
class ImageRepositoryImpl @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val accountRepository: AccountRepository,
    private val pixabayApi: PixabayApi,
    private val imageDao: ImageDao
) : ImageRepository {

    override suspend fun fetchAll(): Resource<List<Image>> {
        val images = imageDao.fetchAll(accountRepository.activeAccount.id)

        return if (images.isNotEmpty()) Resource.success(data = images) else load()
    }

    override suspend fun fetch(id: Int): Resource<Image> {
        val image = imageDao.fetch(id)

        return if (image != null) Resource.success(data = image) else load(id)
    }

    private suspend fun load(): Resource<List<Image>> {
        if (networkMonitor.offline) {
            return Resource.error(R.string.network_no_connection)
        }

        val response: PixabayResponse
        try {
            response = pixabayApi.getImages(API_KEY, randomPage.nextInt(MIN_PAGE, MAX_PAGE))
        } catch (e: HttpException) {
            return Resource.error(R.string.network_cannot_load_images)
        }

        val result = ResponseMapper.convert(response, accountRepository.activeAccount.id)

        imageDao.insertAll(result)
        return Resource.success(data = imageDao.fetchAll(accountRepository.activeAccount.id))
    }

    private suspend fun load(id: Int): Resource<Image> {
        if (networkMonitor.offline) {
            return Resource.error(R.string.network_no_connection)
        }

        val response = pixabayApi.getImage(API_KEY, id)
        val result = ResponseMapper.convert(response, accountRepository.activeAccount.id)

        imageDao.insert(result[0])
        return Resource.success(data = result[0])
    }

    companion object {
        private val randomPage = Random(System.currentTimeMillis())
        private const val MIN_PAGE = 1
        private const val MAX_PAGE = 26
        private const val API_KEY = "19946705-63650a329cd7fa1185c39db55"
    }
}
