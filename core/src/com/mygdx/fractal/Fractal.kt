package com.mygdx.fractal

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.mygdx.fractal.entity.Dot
import kotlin.math.max
import kotlin.math.min

class Fractal : ApplicationAdapter() {
    private lateinit var shape: ShapeRenderer
    private lateinit var dots: ArrayList<Dot>

    private lateinit var stage: Stage
    private lateinit var addButton: ImageButton
    private lateinit var autoButton: ImageButton
    private lateinit var spriteBatch: SpriteBatch
    private lateinit var font: BitmapFont
    private var rnd = 0
    private var isAuto = false

    override fun create() {
        shape = ShapeRenderer()
        dots = ArrayList()

        stage = Stage()
        Gdx.input.inputProcessor = stage

        val runTexture = Texture(Gdx.files.internal("button.png"))
        val runTexturePressed = Texture(Gdx.files.internal("button_pressed.png"))
        addButton = ImageButton(TextureRegionDrawable(TextureRegion(runTexture)),
                TextureRegionDrawable(TextureRegion(runTexturePressed)))
        addButton.setPosition(60f, 350f)
        addButton.isVisible = false
        addButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                addDot()
            }
        })
        stage.addActor(addButton)

        val autoTexture = Texture(Gdx.files.internal("auto_button.png"))
        val autoTexturePressed = Texture(Gdx.files.internal("auto_button_pressed.png"))
        autoButton = ImageButton(TextureRegionDrawable(TextureRegion(autoTexture)),
                TextureRegionDrawable(TextureRegion(autoTexturePressed)))
        autoButton.setPosition(60f, 400f)
        autoButton.isVisible = false
        autoButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                isAuto = !isAuto
            }
        })
        stage.addActor(autoButton)

        spriteBatch = SpriteBatch()
        font = BitmapFont()
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        Gdx.gl.glClearColor(255f, 255f, 255f, 0.0f)
        shape.begin(ShapeRenderer.ShapeType.Filled)

        if (isAuto && dots.size < MAX_DOTS) {
            addDot()
            Thread.sleep(50)
        }

        spriteBatch.begin()
        font.draw(spriteBatch, "count: ${dots.size}", 60f, 310f)
        getInfo()
        spriteBatch.end()

        addButton.isVisible = dots.size >= 4
        autoButton.isVisible = dots.size >= 4
        if (dots.size < 4 && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            val x = Gdx.input.x.toFloat()
            val y = Gdx.graphics.height - Gdx.input.y.toFloat()
            dots.add(Dot(x, y))
        }

        for (dot in dots) {
            dot.draw(shape)
        }

        shape.end()
        stage.draw()
    }

    private fun getInfo() {
        font.color = Color.BLACK

        font.draw(spriteBatch, "random number: $rnd", 60f, 280f)
        for (i in 0..3) {
            canShowDotInfo(i)
        }
    }

    private fun canShowDotInfo(index: Int) {
        if (dots.size > index) {
            val y = 260f - 20 * index
            font.draw(spriteBatch, "dot${index + 1}: (${dots[index].x}, ${dots[index].y})", 60f, y)
        }
    }

    private fun addDot() {
        rnd = (1..6).random()
        Gdx.app.log(TAG, rnd.toString())

        // точка, к которой необходимо следовать
        val target = when (rnd) {
            in 1..2 -> dots[0]
            in 3..4 -> dots[1]
            in 5..6 -> dots[2]
            else -> dots[0]
        }

        val lastDot = dots.last()
        Gdx.app.log(TAG, "lastdot: (${lastDot.x}, ${lastDot.y})")
        Gdx.app.log(TAG, "target: (${target.x}, ${target.y})")

        val maxX = max(target.x, lastDot.x)
        val minX = min(target.x, lastDot.x)
        val maxY = max(target.y, lastDot.y)
        val minY = min(target.y, lastDot.y)

        val newDot = Dot(minX + (maxX - minX) / 2, minY + (maxY - minY) / 2)
        dots.add(newDot)
    }

    companion object {
        const val TAG = "TAG"
        const val MAX_DOTS = 5000
    }
}