package com.pictogram.domain.usecases

import com.pictogram.domain.repository.ImagesRepository
import javax.inject.Inject

class ImagesUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository
) {
    operator fun invoke(userId: Int? = null)=imagesRepository.fetchImages()
}