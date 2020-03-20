package com.mygdx.fractal.entity

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class Dot(private val x: Float, private val y: Float, private val radius: Float) {
    fun draw(shape: ShapeRenderer) {
        shape.color = Color.WHITE
        shape.circle(this.x, this.y, this.radius)
    }
}