package com.musinsa.menu.global.cache

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableCaching
@Configuration
class CacheConfig {

    @Bean
    fun defaultCacheManager(): CacheManager = ConcurrentMapCacheManager("menus")
}
