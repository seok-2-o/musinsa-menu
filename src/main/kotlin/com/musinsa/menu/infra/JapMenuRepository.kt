package com.musinsa.menu.infra

import com.musinsa.menu.domain.model.Menu
import com.musinsa.menu.domain.repository.MenuRepository
import org.springframework.data.jpa.repository.JpaRepository

interface JapMenuRepository : MenuRepository, JpaRepository<Menu, Long>
