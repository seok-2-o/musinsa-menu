package com.musinsa.menu.domain.model

import javax.persistence.*


@Entity
class Menu(
    @Column(nullable = false)
    var title: String,
    @Column(nullable = false)
    var location: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent: Menu? = null,
) {

    init {
        parent?.let { it.children.add(this) }
    }

    @OneToMany(mappedBy = "parent")
    var children: MutableList<Menu> = mutableListOf()

    @Embedded
    var banner: Banner? = null

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun addBanner(banner: Banner) {
        if (this.parent != null) {
            throw IllegalArgumentException("최상위 메뉴만 배너 등록이 가능합니다.")
        }
        this.banner = banner
    }

    fun update(title: String, location: String, parent: Menu? = null) {
        this.title = title
        this.location = location
        this.parent = parent
    }

    fun hasChildren(): Boolean {
        return this.children.isNotEmpty()
    }

}
