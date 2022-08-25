package com.musinsa.menu.domain.model

interface MenuValueObject {
    val id: Long
    val title: String
    val location: String
    val parentId: Long?
    val banner: BannerValueObject?

    interface BannerValueObject {
        val thumbnail: String
        val target: String
    }
}
