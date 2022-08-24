package com.musinsa.menu.ui

import com.musinsa.menu.domain.model.Banner

data class RootMenuResponse(
    var id: Long,
    var title: String,
    var location: String,
    var banner: Banner? = null
)
