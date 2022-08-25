package com.musinsa.menu.application

import com.musinsa.menu.domain.model.Banner
import com.musinsa.menu.domain.model.Menu
import com.musinsa.menu.domain.model.MenuValueObject
import com.musinsa.menu.domain.repository.MenuRepository
import com.musinsa.menu.ui.MenuResponse
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class MenuQueryService(private val repository: MenuRepository) {

    fun findAllRoot(): List<Menu> {
        return repository.findAllByParentIsNull()
    }

    @Cacheable(cacheNames = ["menus"], key = "#id")
    fun findByMenuWithChildren(id: Long): MenuResponse {

        val children = repository.findAllProjectionByParentIsNotNull()
            .groupBy { it.parentId }
        val menu = repository.findProjectionById(id).let {
            MenuResponse(
                it.id,
                it.title,
                it.location,
                findChildren(children, it.id),
                it.banner?.let { b -> Banner(b.thumbnail, b.target) }
            )
        }
        return menu
    }

    private fun findChildren(children: Map<Long?, List<MenuValueObject>>, id: Long): List<MenuResponse> {
        return children[id]?.map {
            MenuResponse(
                it.id,
                it.title,
                it.location,
                findChildren(children, it.id),
                it.banner?.let { b -> Banner(b.thumbnail, b.target) })
        } ?: listOf()
    }

}
