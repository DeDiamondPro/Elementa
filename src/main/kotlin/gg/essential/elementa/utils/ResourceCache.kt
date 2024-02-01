package gg.essential.elementa.utils

import gg.essential.elementa.components.UIImage
import gg.essential.elementa.components.image.CacheableImage
import java.awt.image.BufferedImage
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap
import javax.imageio.ImageIO

class ResourceCache(val size: Int = 50) {
    private val cacheMap = ConcurrentHashMap<String, CacheableImage>()

    fun getUIImage(path: String): CacheableImage {
        if (cacheMap.size > size)
            cacheMap.clear()
        val cachedImage = cacheMap.computeIfAbsent(path) { pth ->
            UIImage(CompletableFuture.supplyAsync {
                ImageIO.read(this::class.java.getResourceAsStream(pth))
            })
        }
        return UIImage(CompletableFuture.completedFuture<BufferedImage>(null)).also {
            cachedImage.supply(it)
        }
    }

    fun invalidateAll() {
        cacheMap.clear()
    }

    fun invalidate(path: String): Boolean {
        return cacheMap.remove(path) != null
    }
}