package com.musinsa.menu.domain.repository

import com.musinsa.menu.domain.model.Menu
import com.musinsa.menu.domain.model.MenuValueObject

interface MenuRepository {

    fun findAll(): List<Menu>

    fun save(menu: Menu): Menu

    fun findById(id: Long): Menu?

    fun delete(menu: Menu)

    fun findAllByParentIsNull(): List<Menu>

    fun findProjectionById(id: Long): MenuValueObject

    fun findAllProjectionByParentIsNotNull(): List<MenuValueObject>

//    fun findAllProjection() : List<MenuValueObject>


}
