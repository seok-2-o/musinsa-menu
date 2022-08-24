package com.musinsa.menu.application

import com.musinsa.menu.domain.model.Menu
import com.musinsa.menu.domain.repository.MenuRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class MenuQueryService(private val repository: MenuRepository) {

    fun findAllRoot(): List<Menu> {
        return repository.findAllByParentIsNull()
    }

}
