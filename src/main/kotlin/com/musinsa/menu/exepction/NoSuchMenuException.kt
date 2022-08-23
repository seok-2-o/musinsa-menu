package com.musinsa.menu.exepction

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "메뉴를 찾을 수 업습니다.")
class NoSuchMenuException : NoSuchElementException()

