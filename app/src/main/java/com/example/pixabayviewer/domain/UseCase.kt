package com.example.pixabayviewer.domain

interface UseCase<out T, in Params> {
    suspend fun build(params: Params): T
}