package com.musinsa.menu.global.cache

import com.musinsa.menu.application.MenuQueryService
import com.musinsa.menu.domain.repository.MenuRepository
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MenuCacheWarmupManager(
    private val menuRepository: MenuRepository,
    private val menuQueryService: MenuQueryService
) {

    @PostConstruct
    fun warmup() {
        menuRepository.findAll()
            .parallelStream()
            .forEach { menuQueryService.findByMenuWithChildren(it.id!!) }
    }


}
