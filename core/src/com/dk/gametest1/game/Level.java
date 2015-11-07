package com.dk.gametest1.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dk.gametest1.Constants;

/**
 * This class loads level and holds all game objects, updates them and renders
 * Created by dekay on 30.10.2015.
 */

public class Level extends Stage{
    protected Ball[] balls;
    protected TimeScore score;

    public Level(String path) {
        init();
    }

    /**
     * Initializing all game objects
     */
    private void init() {
        balls = new Ball[Constants.BALLS_QUANTITY];
        score = new TimeScore();
        this.addActor(score);
        for (int i = 0; i < Constants.BALLS_QUANTITY; i++) {
            balls[i] = new Ball();
            balls[i].setOrigin(balls[i].getRadius() / 2, balls[i].getRadius() / 2);
            balls[i].setPosition(MathUtils.random(0, Constants.VIEWPORT_WIDTH), MathUtils.random(0, Constants.VIEWPORT_HEIGHT));
            this.addActor(balls[i]);
        }
    }
}