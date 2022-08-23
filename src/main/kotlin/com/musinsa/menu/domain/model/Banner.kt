package com.musinsa.menu.domain.model

import javax.persistence.Embeddable

@Embeddable
data class Banner(var thumbnail: String, var target: String)
