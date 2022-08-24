package com.musinsa.menu.domain.repository

import com.musinsa.menu.domain.model.Menu

interface MenuRepository {

    fun save(menu: Menu): Menu

    fun findById(id: Long): Menu?

    fun findAllByParentIsNull(): List<Menu>
}
