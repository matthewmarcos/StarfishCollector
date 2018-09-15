package com.matthewmarcos.starfishcollector;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class StarfishCollectorAlpha extends Game {
    private SpriteBatch batch;

    private Texture turtleTexture;
    private float turtleX;
    private float turtleY;
    private Rectangle turtleRectangle;

    private Texture starfishTexture;
    private float starfishX;
    private float starfishY;
    private Rectangle starfishRectangle;

    private Texture oceanTexture;
    private Texture winMessageTexture;
    private boolean win;

    private static int SPEED = 2;

    public void create() {
        this.batch = new SpriteBatch();

        this.turtleTexture = new Texture(Gdx.files.internal("turtle-1.png"));
        this.turtleX = 20;
        this.turtleY = 20;
        this.turtleRectangle = new Rectangle(this.turtleX, this.turtleY,
                                             this.turtleTexture.getWidth(), this.turtleTexture.getHeight());

        this.starfishTexture = new Texture(Gdx.files.internal("starfish.png"));
        this.starfishX = 380;
        this.starfishY = 380;
        this.starfishRectangle = new Rectangle(this.starfishX, this.starfishY,
                                               this.starfishTexture.getWidth(), this.starfishTexture.getHeight());

        this.oceanTexture = new Texture(Gdx.files.internal("water.jpg"));
        this.winMessageTexture = new Texture(Gdx.files.internal("you-win.png"));

        this.win = false;
    }

    public void render() {
        // Checking user input
        if (Gdx.input.isKeyPressed(Keys.LEFT)) this.turtleX-=SPEED;
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) this.turtleX+=SPEED;
        if (Gdx.input.isKeyPressed(Keys.UP)) this.turtleY+=SPEED;
        if (Gdx.input.isKeyPressed(Keys.DOWN)) this.turtleY-=SPEED;

        // Update turtle rectangle location
        this.turtleRectangle.setPosition(this.turtleX, this.turtleY);

        // Check the win condition
        if(this.turtleRectangle.overlaps(this.starfishRectangle)) win = true;

        // Actual drawing
        Gdx.gl.glClearColor(0,0,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.batch.begin();
        this.batch.draw(this.oceanTexture, 0, 0);
        if(!this.win) {
            this.batch.draw(this.starfishTexture, this.starfishX, this.starfishY);
            this.batch.draw(this.turtleTexture, this.turtleX, this.turtleY);
        }
        else {
            this.batch.draw(this.turtleTexture, this.turtleX, this.turtleY);
            this.batch.draw(this.winMessageTexture, 180, 180);
        }
        this.batch.end();
    }
}
