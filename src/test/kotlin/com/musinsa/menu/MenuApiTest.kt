package com.musinsa.menu

import com.musinsa.menu.domain.model.Banner
import com.musinsa.menu.helper.AcceptanceTest
import com.musinsa.menu.ui.CreateMenuCommand
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import org.apache.http.HttpStatus
import org.junit.jupiter.api.Test

class MenuApiTest : AcceptanceTest() {


    @Test
    fun `최상위 메뉴 등록`() {
        `메뉴 등록`("아우터", "/outer", null, null, null) Then {
            statusCode(HttpStatus.SC_CREATED)
        } Extract {
            header("Location")
        }
    }

    @Test
    fun `하위 메뉴 등록`() {
        var location = `메뉴 등록`("아우터", "/outer", null, null, null) Extract {
            header("Location")
        }
        var parent = location.replace("apis/menus/", "").toLongOrNull()

        `메뉴 등록`("후드짚업", "outer/hood", parent, null, null) Then {
            statusCode(HttpStatus.SC_CREATED)
        }
    }

    @Test
    fun `하위 메뉴는 배너를 가질 수 없다`() {
        var location = `메뉴 등록`("아우터", "/outer", null, null, null) Extract {
            header("Location")
        }
        var parent = location.replace("apis/menus/", "").toLongOrNull()
        `메뉴 등록`("후드짚업", "outer/hood", parent, "/imgs/autumn-outer.webp", "/autumn-outer") Then {
            statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
        }
    }

    fun `메뉴 등록`(title: String, location: String, parent: Long?, thumbnail: String?, target: String?): Response {
        var banner: Banner? = null
        if (thumbnail != null && target != null) {
            banner = Banner(thumbnail, target)
        }
        val command = CreateMenuCommand(title, location, parent, banner)
        return Given {
            contentType(ContentType.JSON)
            body(command)
        } When {
            post("/apis/menus")
        }
    }
}
