package com.musinsa.menu.application

import com.musinsa.menu.domain.model.Banner
import com.musinsa.menu.domain.model.Menu
import com.musinsa.menu.domain.repository.MenuRepository
import com.musinsa.menu.exepction.CannotBeDeletedException
import com.musinsa.menu.exepction.NoSuchMenuException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
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

    fun update(id: Long, title: String, link: String, parentId: Long?, banner: Banner?) {
        var saved = repository.findById(id) ?: throw NoSuchMenuException()
        val parent = parentId?.let {
            repository.findById(parentId)
        }
        saved.update(title, link, parent)
        banner?.let {
            saved.addBanner(it)
        }
    }

    fun delete(id: Long) {
        var saved = repository.findById(id) ?: throw NoSuchMenuException()
        if (saved.hasChildren()) {
            throw CannotBeDeletedException()
        }
        repository.delete(saved)
    }
}
