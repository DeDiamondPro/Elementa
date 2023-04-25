package com.example.examplemod

import gg.essential.elementa.ElementaVersion
import gg.essential.elementa.WindowScreen
import gg.essential.elementa.components.*
import gg.essential.elementa.components.input.UIMultilineTextInput
import gg.essential.elementa.components.inspector.Inspector
import gg.essential.elementa.constraints.*
import gg.essential.elementa.constraints.animation.Animations
import gg.essential.elementa.dsl.*
import gg.essential.elementa.effects.OutlineEffect
import gg.essential.elementa.effects.ScissorEffect
import gg.essential.elementa.font.DefaultFonts
import gg.essential.elementa.font.ElementaFonts
import gg.essential.elementa.utils.withAlpha
import java.awt.Color
import java.net.URL

class KtTestGui : WindowScreen(ElementaVersion.V2) {
    private val myTextBox = UIBlock(Color(0, 0, 0, 255))

    init {
        /*for (i in 50..500) {
            if (i % 15 != 0) continue
            UIBlock(Color.RED).constrain {
                x = CramSiblingConstraint(10 / 3f)
                y = CramSiblingConstraint(10f / 3f)
                width = 5.pixels()
                height = 5.pixels()
            } childOf container effect OutlineEffect(Color.BLUE, 1f);
        }*/

        SVGComponent.ofString("<svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><polygon points=\"13 2 3 14 12 14 11 22 21 10 12 10 13 2\"/></svg>")
            .constrain {
                x = CenterConstraint()
                y = 50.pixels()

                width = 100.pixels()
                height = 100.pixels()
            } childOf window

        SVGComponent.ofURL(URL("https://raw.githubusercontent.com/EssentialGG/Elementa/master/src/main/resources/svg/test.svg"))
            .constrain {
                x = CenterConstraint()
                y = SiblingConstraint()

                width = 100.pixels()
                height = 100.pixels()
            } childOf window
    }
}