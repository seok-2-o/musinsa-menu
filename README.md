# 메뉴 API

## 🚀 Getting Started

#### application 구동
```
./gradlew bootRun
```

## 요구 사항
- 메뉴를 등록/수정/삭제 할 수 있어야 한다.
- 상위 메뉴를 이용해, 해당 메뉴의 모든 하위 메뉴를 조회 가능해야 한다.
- 최상위 메뉴에는 배너를 등록하여 추가 노출 할 수 있습니다.

## API 명세

#### 메뉴 등록
메뉴를 등록할 수 있다. 메뉴는 이름과 링크를 가지며 최상위 메뉴의 경우 배너,
최상위 배너가 아닐 경우 상위 메뉴의 아이디를 가진다.
```
POST apis/menus    
Content-type : application/json;charset=utf-8  

{
    "parent" : {{상위메뉴 아이디}},
    "title" : {{메뉴 타이틀}}
    "location" : {{메뉴 링크}} ,
    "thubmnail" : {{배너 이미지}}  ,
    "target" : {{배너 타겟 링크}}
}

응답 예) 
성공 HTTP/1.1 201 Created
-H Location apis/menus/{id}
     
```
#### 메뉴 수정
메뉴를 수정할 수 있다. 메뉴의 이름과 링크, 배너, 상위메뉴를 수정할 수있다.

```
PUT apis/menus/{id}    
Content-type : application/json;charset=utf-8  

{
    "parent" : {{상위메뉴 아이디}},
    "title" : {{메뉴 타이틀}}
    "location" : {{메뉴 링크}} ,
    "thubmnail" : {{배너 이미지}}  ,
    "target" : {{배너 타겟 링크}}
}

응답 예) 
성공 HTTP/1.1 204 No Content   
```
#### 메뉴 삭제
메뉴를 삭제할 수 있다. 하위 메뉴가 존재하는 경우 삭제할 수 없다.
```
DELETE apis/menus/{id}    

응답 예) 
성공 HTTP/1.1 204 No Content
```

#### 모든 상위 메뉴 조회
최상위 메뉴를 조회한다. 하위 메뉴를 포함하지 않는다.
```
GET apis/root-menus


응답 예)
HTTP/1.1 200 OK

[
{
    "id": 10001,
    "title" : "아우터"
    "lint" : "/outer"
    "bnner" : {
        "thumbnail" : "/imgs/autumn-outer.webp"
        "link" : "/autumn-outer"
    }
}, {
    "id": 10002,
    "title" : "인기"
    "lint" : "/best"
    "bnner" : {
        "thumbnail" : "/imgs/autumn-outer.webp"
        "link" : "/autumn-outer"
    }
}
]
```

#### 메뉴 조회
아이디에 해당하는 메뉴와 그 하위 메뉴들을 조회할 수 있다.
```
GET apis/menus/{id}

응답 예)
HTTP/1.1 200 OK
{
    "id": 10001,
    "title" : "아우터"
    "lint" : "/outer"
    "children" : [
        {
            "id": 10003,
            "title": "후드집엎",
            "link": "outer/hood"
            "order" : 1 
        }
    ]
}

```
