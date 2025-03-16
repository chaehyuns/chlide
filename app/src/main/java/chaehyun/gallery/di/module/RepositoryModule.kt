package chaehyun.gallery.di.module

import chaehyun.gallery.data.repository.DefaultImageRepository
import chaehyun.gallery.domain.repository.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindImageRepository(
        imageRepositoryImpl: DefaultImageRepository
    ): ImageRepository
}
