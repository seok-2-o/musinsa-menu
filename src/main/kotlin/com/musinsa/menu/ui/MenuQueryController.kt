package com.musinsa.menu.ui

import com.musinsa.menu.application.MenuQueryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("apis/menus")
class MenuQueryController(private val service: MenuQueryService) {

    @GetMapping("/root-menus")
    fun getAllRootMenus(): ResponseEntity<RootMenuResponses> {
        return service.findAllRoot()
            .map { RootMenuResponse(it.id!!, it.title, it.location, it.banner) }
            .let { RootMenuResponses(it) }
            .let { ResponseEntity.ok(it) }

    }

}
