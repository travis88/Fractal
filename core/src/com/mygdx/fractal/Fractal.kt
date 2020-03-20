package com.mygdx.fractal

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.mygdx.fractal.entity.Dot

class Fractal : ApplicationAdapter() {
    private lateinit var shape: ShapeRenderer
    private lateinit var dots: ArrayList<Dot>

    override fun create() {
        shape = ShapeRenderer()
        dots = ArrayList()
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        shape.begin(ShapeRenderer.ShapeType.Filled)
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            val x = Gdx.input.x.toFloat()
            val y = Gdx.graphics.height - Gdx.input.y.toFloat()

            Gdx.app.log(TAG, Gdx.input.x.toString())
            dots.add(Dot(x, y, 3f))
        }

        for (dot in dots) {
            dot.draw(shape)
        }

        shape.end()
    }

    companion object {
        const val TAG = "TAG"
    }
}