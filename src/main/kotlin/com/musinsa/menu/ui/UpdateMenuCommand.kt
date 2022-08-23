package com.musinsa.menu.ui

import com.musinsa.menu.domain.model.Banner

data class UpdateMenuCommand(val title: String, val link: String, val parent: Long?, val banner: Banner?)
