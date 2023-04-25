package gg.essential.elementa.svg.data

import gg.essential.elementa.impl.dom4j.Element
import org.lwjgl.opengl.GL11
import java.nio.FloatBuffer

class SvgPolygon(val points: List<Float>) : SVGElement() {

    override fun getVertexCount(): Int {
        return points.size / 2
    }

    override fun createBuffer(buffer: FloatBuffer): Int {
        for (point in points) {
            buffer.put(point)
        }
        return GL11.GL_LINE_LOOP
    }

    companion object {
        private val POLYGON_SPLIT = "[ ,]+".toRegex()

        internal fun from(element: Element): SvgPolygon {
            val points = element.attributeValue("points").split(POLYGON_SPLIT).map { it.toFloat() }

            return SvgPolygon(points)
        }
    }
}