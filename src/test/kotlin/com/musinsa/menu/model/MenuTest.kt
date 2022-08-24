package com.musinsa.menu.model

import com.musinsa.menu.domain.model.Banner
import com.musinsa.menu.domain.model.Menu
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

internal class MenuTest {

    @Test
    fun `최상위메뉴만 배너를 추가할 수 있다`() {
        val 최상위_메뉴 = Menu("아우터", "/outer")
        val 하위_메뉴 = Menu("후드짚업", "/outer/hood", 최상위_메뉴)

        assertThatThrownBy { 하위_메뉴.addBanner(Banner("banner.webp", "/imgs/autumn-outer.webp")) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("최상위 메뉴만 배너 등록이 가능합니다.")
    }

    @Test
    fun `메뉴를 수정할 수 있다`() {
        val 메뉴 = Menu("아우터", "/outer")
        메뉴.update("상의", "/top", null)

        assertThat(메뉴.title).isEqualTo("상의")
        assertThat(메뉴.location).isEqualTo("/top")
    }

    @Test
    fun `하위 메뉴가 있는지 확인 할 수 있다`() {
        val 최상위_메뉴 = Menu("아우터", "/outer")
        Menu("후드짚업", "/outer/hood", 최상위_메뉴)

        assertThat(최상위_메뉴.hasChildren()).isTrue
    }

}
