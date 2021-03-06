package com.dk.gametest1.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.dk.gametest1.Constants;

/**
 * Renderer which draws game elements
 * Created by dekay on 30.10.2015.
 */
public class GameRenderer implements Disposable {
    private GameUpdater gameUpdater; //updater to calculate new positions, dimensions and other of each object
    private SpriteBatch spriteBatch; //batch to draw on it
    private OrthographicCamera cameraGUI;//camera to let user see font
    private BitmapFont guiFont;//font

    public GameRenderer(GameUpdater gameUpdater) {
        this.gameUpdater = gameUpdater;
        init();
    }

    /**
     * Initializing all objects
     */
    private void init() {
        //Loading font from assets dir
        guiFont = new BitmapFont(Gdx.files.internal("f72.fnt"), true);
        guiFont.setColor(Constants.GRAY);
        guiFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        spriteBatch = new SpriteBatch();
        //Creating two cameras for objects and for font
        gameUpdater.level.getCamera().viewportHeight = Constants.VIEWPORT_HEIGHT;
        gameUpdater.level.getCamera().viewportWidth = Constants.VIEWPORT_WIDTH;
        gameUpdater.level.getCamera().position.set(0, 0, 0);
        gameUpdater.level.getCamera().update();

        cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
        cameraGUI.position.set(0, 0, 0);
        cameraGUI.setToOrtho(true);
        cameraGUI.update();
    }

    /**
     * Deleting objects to release memory
     */
    @Override
    public void dispose() {
        spriteBatch.dispose();
        guiFont.dispose();
    }

    /**
     * renders game and gui
     */
    public void render() {
        renderGame();
        renderGUI();
    }

    /**
     * Rendering level
     */
    private void renderGame() {
        gameUpdater.level.draw();
    }


    private void renderGUI() {
        renderGUITimer();
    }

    /**
     * Rendering timer using batch
     */
    private void renderGUITimer() {
        //Setting the scale by checking type of the app
        float scale;
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            scale = Constants.DESKTOP_SCALE_X;
        } else {
            scale = Constants.MOBILE_SCALE_X;
        }
        guiFont.getData().setScale(scale);
        spriteBatch.setProjectionMatrix(cameraGUI.combined);
        spriteBatch.begin();
        //Setting position for the numbers of timer by using size of font and scale
        long time = gameUpdater.level.score.time;
        if (time < 10) {
            guiFont.draw(spriteBatch, String.valueOf(time), cameraGUI.position.x - scale * (Constants.FONT_SIZE / 4 - 2), cameraGUI.position.y - scale * (Constants.FONT_SIZE / 4 + 4));
        } else if (time >= 10 && time < 100) {
            guiFont.draw(spriteBatch, String.valueOf(time), cameraGUI.position.x - scale * (Constants.FONT_SIZE / 2 - 2), cameraGUI.position.y - scale * (Constants.FONT_SIZE / 4 + 4));
        } else {
            guiFont.draw(spriteBatch, String.valueOf(time), cameraGUI.position.x - scale * (Constants.FONT_SIZE / 2 + Constants.FONT_SIZE / 4 - 2), cameraGUI.position.y - scale * (Constants.FONT_SIZE / 4 + 4));
        }
        spriteBatch.end();
    }

}
