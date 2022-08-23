package com.musinsa.menu.ui

import com.musinsa.menu.application.MenuCommandService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("apis/menus")
class MenuCommandController(private val service: MenuCommandService) {

    @PostMapping
    fun create(@RequestBody command: CreateMenuCommand): ResponseEntity<Void> {
        var id = service.create(command.title, command.link, command.parent, command.banner)
        return ResponseEntity.created(URI.create("apis/menus/$id"))
            .build()
    }

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody command: UpdateMenuCommand): ResponseEntity<Void> {
        service.update(id, command.title, command.link, command.parent, command.banner)
        return ResponseEntity.noContent()
            .build()
    }
}
