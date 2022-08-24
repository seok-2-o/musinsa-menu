package com.musinsa.menu.exepction

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "하위 메뉴가 존재하여 삭제할 수 없습니다.")
class CannotBeDeletedException : IllegalStateException()

