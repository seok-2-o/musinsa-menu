package com.musinsa.menu.ui

import com.fasterxml.jackson.annotation.JsonInclude
import com.musinsa.menu.domain.model.Banner

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MenuResponse(
    val id: Long,
    val title: String,
    val location: String,
    val children: List<MenuResponse> = listOf(),
    val banner: Banner?
)
