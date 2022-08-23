package com.musinsa.menu

import com.musinsa.menu.domain.model.Banner
import com.musinsa.menu.helper.AcceptanceTest
import com.musinsa.menu.ui.CreateMenuCommand
import com.musinsa.menu.ui.UpdateMenuCommand
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import org.apache.http.HttpStatus
import org.junit.jupiter.api.Test

internal class MenuApiTest : AcceptanceTest() {


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

    @Test
    fun `메뉴의 속성을 변경할 수 있다`() {
        var location = `메뉴 등록`("아우터", "/outer", null, null, null) Extract {
            header("Location")
        }
        var id = location.replace("apis/menus/", "").toLong()
        `메뉴 수정`(id, "상의", "/top") Then {
            statusCode(HttpStatus.SC_NO_CONTENT)
        }
    }

    @Test
    fun `존재하지 않는 메뉴의 속성을 변경할 수 없다`() {
        `메뉴 수정`(1L, "상의", "/top") Then {
            statusCode(HttpStatus.SC_NOT_FOUND)
        }
    }

    @Test
    fun `하위 메뉴는 배너를 가질 수 없다_수정`() {
        var 부모 = `메뉴 등록 후 아이디 응답`("아우터", "/outer", null, null, null)
        var 자식 = `메뉴 등록 후 아이디 응답`("후드짚업", "outer/hood", 부모);
        `메뉴 수정`(자식!!, "후드짚업", "outer/hood", 부모, "/imgs/autumn-outer.webp", "/autumn-outer") Then {
            statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
        }
    }

    @Test
    fun `메뉴를 삭제할 수 있다`() {
        var id = `메뉴 등록 후 아이디 응답`("아우터", "/outer", null, null, null)

        `메뉴 삭제`(id!!) Then {
            statusCode(HttpStatus.SC_NO_CONTENT)
        }
    }

    @Test
    fun `하위메뉴가 존재하는 경우 삭제할 수 없다`() {
        var 부모_ID = `메뉴 등록 후 아이디 응답`("아우터", "/outer", null, null, null)
        `메뉴 등록`("후드짚업", "outer/hood", 부모_ID)
        `메뉴 등록`("후드짚업2", "outer/hood2", 부모_ID)
        `메뉴 삭제`(부모_ID!!) Then {
            statusCode(HttpStatus.SC_BAD_REQUEST)
        }
    }


    fun `메뉴 등록 후 아이디 응답`(
        title: String,
        location: String,
        parent: Long? = null,
        thumbnail: String? = null,
        target: String? = null
    ): Long? {
        return `메뉴 등록`(title, location, parent, thumbnail, target) Extract {
            header("Location").replace("apis/menus/", "").toLongOrNull()
        }
    }


    fun `메뉴 등록`(
        title: String,
        location: String,
        parent: Long? = null,
        thumbnail: String? = null,
        target: String? = null
    ): Response {
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

    fun `메뉴 수정`(
        id: Long,
        title: String,
        location: String,
        parent: Long? = null,
        thumbnail: String? = null,
        target: String? = null
    ): Response {
        var banner: Banner? = null
        if (thumbnail != null && target != null) {
            banner = Banner(thumbnail, target)
        }
        val command = UpdateMenuCommand(title, location, parent, banner)
        return Given {
            contentType(ContentType.JSON)
            body(command)
        } When {
            put("/apis/menus/$id")
        }
    }

    fun `메뉴 삭제`(id: Long): Response {
        return When {
            delete("/apis/menus/$id")
        }
    }
}
