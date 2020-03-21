package com.mygdx.fractal

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.mygdx.fractal.entity.Dot

class Fractal : ApplicationAdapter() {
    private lateinit var shape: ShapeRenderer
    private lateinit var dots: ArrayList<Dot>

    private lateinit var stage: Stage
    private lateinit var button: ImageButton

    override fun create() {
        shape = ShapeRenderer()
        dots = ArrayList()

        stage = Stage()
        val runTexture = Texture(Gdx.files.internal("button.png"))
        val runTexturePressed = Texture(Gdx.files.internal("button_pressed.png"))
        button = ImageButton(TextureRegionDrawable(TextureRegion(runTexture)),
                             TextureRegionDrawable(TextureRegion(runTexturePressed)))
        button.setPosition(60f, 300f)
        button.isVisible = false
        Gdx.input.inputProcessor = stage
        stage.addActor(button)

        button.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                val rnds = (1..6).random()
                Gdx.app.log(TAG, rnds.toString())
            }
        })
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        Gdx.gl.glClearColor(255f, 255f, 255f, 0.0f)
        shape.begin(ShapeRenderer.ShapeType.Filled)
        if (dots.size >= 3) {
            button.isVisible = true
        }
        if (dots.size < 3 && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            val x = Gdx.input.x.toFloat()
            val y = Gdx.graphics.height - Gdx.input.y.toFloat()

            Gdx.app.log(TAG, Gdx.input.x.toString())
            dots.add(Dot(x, y, 3f))
        }

        for (dot in dots) {
            dot.draw(shape)
        }

        shape.end()
        stage.draw()
    }

    companion object {
        const val TAG = "TAG"
    }
}