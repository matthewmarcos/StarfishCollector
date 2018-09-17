package com.matthewmarcos.starfishcollector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.net.ResponseCache;
import java.util.ArrayList;

public class BaseActor extends Actor {

    protected Animation<TextureRegion> animation;
    protected float elapsedTime;
    private boolean animationPaused;
    private Vector2 velocityVec;
    private Vector2 accelerationVec;
    private float acceleration;
    private float maxSpeed;
    private float deceleration;
    private Polygon boundaryPolygon;
    private String myClass;

    private static Rectangle worldBounds;

    public BaseActor(float x, float y, Stage s, String myClass) {
        super();
        // Additional Initialization tasks
        initialize(x, y, s);
        this.myClass = myClass;
    }

    public BaseActor(float x, float y, Stage s) {
        super();
        // Additional Initialization tasks
        initialize(x, y, s);
        this.myClass = "";
    }

    private void initialize(float x, float y, Stage s) {
        setPosition(x, y);
        s.addActor(this);

        animation = null;
        elapsedTime = 0;
        animationPaused = false;
        velocityVec = new Vector2(0, 0);
        accelerationVec = new Vector2(0, 0);
        acceleration = 0;

        maxSpeed = 1000;
        deceleration = 0;
    }

    public static void setWorldBounds(float width, float height) {
        worldBounds = new Rectangle(0, 0, width, height);
    }

    public static void setWorldBounds(BaseActor ba) {
        setWorldBounds(ba.getWidth(), ba.getHeight());
    }

    public void boundToWorld() {
        // check left edge
        if (getX() < 0)
            setX(0);
        // check right edge
        if (getX() + getWidth() > worldBounds.width)
            setX(worldBounds.width - getWidth());
        // check bottom edge
        if (getY() < 0)
            setY(0);
        // check top edge
        if (getY() + getHeight() > worldBounds.height)
            setY(worldBounds.height - getHeight());
    }

    public void setBoundaryRectangle() {
        float w = getWidth();
        float h = getHeight();
        float[] vertices = {0,0,w,0,w,h,0,h};

        boundaryPolygon = new Polygon(vertices);
    }

    public void setAnimation(Animation<TextureRegion> anim) {
        animation = anim;
        // Keyframe = one frame in the animation
        TextureRegion tr = animation.getKeyFrame(0);
        float w = tr.getRegionWidth();
        float h = tr.getRegionHeight();
        setSize(w, h);
        // Origin: Where the image rotates
        setOrigin(w/2, h/2);

        if(boundaryPolygon == null) {
            setBoundaryRectangle();
        }
    }

    public void setBoundaryPolygon(int numSides) {
        float w = getWidth();
        float h = getHeight();
        float[] vertices = new float[2 * numSides];

        for(int i = 0 ; i < numSides ; i++) {
            // 6.28 = 2 radians. We'll calculate the coordinates of the points every `numSide` of the ellipse
            float angle = (6.28f / numSides) * i;
            // x-coordinate
            vertices[2*i] = w/2 * MathUtils.cos(angle) + w/2;
            // y-coordinate
            vertices[2*i+1] = h/2 * MathUtils.sin(angle) + h/2;
        }

        boundaryPolygon = new Polygon(vertices);
    }

    public boolean overlaps(BaseActor other) {
        Polygon p1 = this.getBoundaryPolygon();
        Polygon p2 = other.getBoundaryPolygon();
        Rectangle r1 = p1.getBoundingRectangle();
        Rectangle r2 = p2.getBoundingRectangle();

        /*
            Polygon collision requires a lot of computing power so we can just check if the
            boundary rectangles overlap first. If they don't, we do not need to compute any further
         */
        if(!r1.overlaps(r2)) {
            return false;
        }

        return Intersector.overlapConvexPolygons(p1, p2);

    }

    public void centerAtPosition(float x, float y) {
        setPosition(x - getWidth()/2 , y - getHeight()/2);
    }

    public void centerAtActor(BaseActor other) {
        centerAtPosition(other.getX() + other.getWidth()/2 , other.getY() + other.getHeight()/2);
    }

    public void setOpacity(float opacity) {
        this.getColor().a = opacity;
    }

    public Polygon getBoundaryPolygon() {
        boundaryPolygon.setPosition(getX(), getY());
        boundaryPolygon.setOrigin(getOriginX(), getOriginY());
        boundaryPolygon.setRotation(getRotation());
        boundaryPolygon.setScale(getScaleX(), getScaleY());

        return boundaryPolygon;
    }

    public void setAnimationPaused(boolean pause) {
    animationPaused = pause;
    }

    public void act(float dt) {
        super.act(dt);
        if(!animationPaused) {
            elapsedTime += dt;
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        // Apply the color tint effect
        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a);

        if(animation != null && isVisible()) {
            batch.draw(animation.getKeyFrame(elapsedTime),
                    getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }

    public Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames,
                                                           float frameDuration, boolean loop) {
        /*
            Animation is from files
         */
        int fileCount = fileNames.length;
        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for(int n = 0 ; n < fileCount ; n++) {
            String fileName = fileNames[n];
            Texture texture = new Texture(Gdx.files.internal(fileName));
            texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
            textureArray.add(new TextureRegion(texture));
        }

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);

        if(loop) {
            anim.setPlayMode(PlayMode.LOOP);
        }
        else {
            anim.setPlayMode(PlayMode.NORMAL);
        }

        if(animation == null) {
            setAnimation(anim);
        }

        return anim;
    }

    public Animation<TextureRegion> loadAnimationFromSheet(String fileName, int rows, int cols,
                                                           float frameDuration, boolean loop) {
        /*
            Animation is from a spritesheet
         */
        Texture texture = new Texture(Gdx.files.internal(fileName), true);
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() / rows;

        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);

        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for(int r = 0 ; r < rows ; r++)
            for(int c = 0 ; c < cols ; c++) {
                textureArray.add(temp[r][c]);
            }

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);
        if(loop) {
            anim.setPlayMode(PlayMode.LOOP);
        }
        else {
            anim.setPlayMode(PlayMode.NORMAL);
        }

        if (animation == null) {
            setAnimation(anim);
        }

        return anim;
    }

    public Animation<TextureRegion> loadTexture(String fileName) {
        /*
            One-frame animation for things that do not move
         */
        String[] fileNames = new String[1];
        fileNames[0] = fileName;

        return loadAnimationFromFiles(fileNames, 1, true);
    }

    public boolean isAnimationFinished() {
        return animation.isAnimationFinished(elapsedTime);
    }

    public void setSpeed(float speed) {
        if(velocityVec.len() == 0) {
            velocityVec.set(speed, 0);
        }
        else {
            velocityVec.setLength(speed);
        }
    }

    public float getSpeed() {
        return velocityVec.len();
    }

    public void setMotionAngle(float angle) {
        velocityVec.setAngle(angle);
    }

    public float getMotionAngle() {
        return velocityVec.angle();
    }

    public boolean isMoving() {
        return getSpeed() > 0;
    }

    public void setAcceleration(float acc) {
        acceleration = acc;
    }

    public void accelerateAtAngle(float angle) {
        accelerationVec.add(new Vector2(acceleration, 0).setAngle(angle));
    }

    public void accelerateForward() {
        accelerateAtAngle(getRotation());
    }

    public void setMaxSpeed(float ms) {
        maxSpeed = ms;
    }

    public void setDeceleration(float dec) {
        deceleration = dec;
    }

    public float getAcceleration() {
        return accelerationVec.len();
    }

    public void applyPhysics(float dt) {
        // Apply acceleration
        velocityVec.add(accelerationVec.x * dt, accelerationVec.y * dt);

        float speed = getSpeed();

        // decrease speed when not accelerating
        if(getAcceleration() == 0) {
            speed -= deceleration * dt;
        }

        // Limit te speed
        speed = MathUtils.clamp(speed, 0, maxSpeed);

        // update velocity
        setSpeed(speed);

        // apply velocity
        moveBy(velocityVec.x * dt, velocityVec.y * dt);

        // reset the acceleration
        accelerationVec.set(0, 0);
    }

    public Vector2 preventOverlap(BaseActor other) {
        Polygon p1 = this.getBoundaryPolygon();
        Polygon p2 = other.getBoundaryPolygon();

        Rectangle r1 = p1.getBoundingRectangle();
        Rectangle r2 = p2.getBoundingRectangle();

        /*
            Polygon collision requires a lot of computing power so we can just check if the
            boundary rectangles overlap first. If they don't, we do not need to compute any further
         */
        if(!r1.overlaps(r2)) {
            return null;
        }

        Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();
        boolean polygonOverlap = Intersector.overlapConvexPolygons(p1, p2, mtv);

        if(!polygonOverlap) {
            return null;
        }

        this.moveBy(mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth);
        return mtv.normal;
    }

    public String getMyClass() {
        return this.myClass;
    }

    public static ArrayList<BaseActor> getList(Stage s, String className) {
        ArrayList<BaseActor> list = new ArrayList<BaseActor>();

        for(Actor a: s.getActors()) {
            // Get only the actors that are an instance of className
            BaseActor actor = (BaseActor)a;
            if(actor.getMyClass() == className) {
                list.add(actor);
            }
        }

        return list;
    }

    public static int count(Stage s, String className) {
        return getList(s, className).size();
    }

    public void alignCamera() {
        Camera cam = this.getStage().getCamera();
        Viewport v = this.getStage().getViewport();

        // center Camera on actor
        cam.position.set(this.getX() + this.getOriginX(), this.getY() + this.getOriginY(), 0);

        // bound camera to layout
        cam.position.x = MathUtils.clamp(cam.position.x,
                cam.viewportWidth/2, worldBounds.width - cam.viewportWidth/2);
        cam.position.y = MathUtils.clamp(cam.position.y,
                cam.viewportHeight/2, worldBounds.height - cam.viewportHeight/2);
        cam.update();
    }
}
