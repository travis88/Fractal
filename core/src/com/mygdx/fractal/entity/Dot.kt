package com.mygdx.fractal.entity

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class Dot(val x: Float, val y: Float, private val radius: Float = 3f) {
    fun draw(shape: ShapeRenderer) {
        shape.color = Color.BLACK
        shape.circle(this.x, this.y, this.radius)
    }
}