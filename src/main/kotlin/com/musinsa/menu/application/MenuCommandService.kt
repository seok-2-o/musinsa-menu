package com.musinsa.menu.application

import com.musinsa.menu.domain.model.Banner
import com.musinsa.menu.domain.model.Menu
import com.musinsa.menu.domain.repository.MenuRepository
import org.springframework.stereotype.Service

@Service
class MenuCommandService(private val repository: MenuRepository) {

    fun create(title: String, link: String, parentId: Long?, banner: Banner?): Long {
        val parent = parentId?.let {
            repository.findById(parentId)
        }
        val menu = Menu(title, link, parent)
        banner?.let {
            menu.addBanner(it)
        }
        return repository.save(menu).id!!
    }
}
