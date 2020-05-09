package com.melons.game.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.melons.game.Constants;
import com.melons.game.MelonCycle;
import com.melons.game.interfaces.SizeChangable;

public class GuiButton extends Actor implements SizeChangable {

    protected Texture Image;

    protected float default_x;
    protected float default_y;

    protected float default_width;
    protected float default_height;

    protected float x;
    protected float y;

    protected MelonCycle game;
    Stage toChange;

    public GuiButton(float x, float y, final MelonCycle g, String path){
        toChange = null;
        this.x = x;
        this.y = y;
        this.game = g;
        Image = new Texture(path);

        default_x = x;
        default_y = y;
        default_width = Image.getWidth();
        default_height = Image.getWidth();

        setX(x);
        setY(y);
        setBounds(x, y, Image.getWidth(), Image.getHeight());

        setTouchable(Touchable.enabled);
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                execute();
                return true;
                }
        });
        g.addResizable(this);
    }

    public float getX(){
            return x;
        }

    public float getY() {
        return y;
    }

    public Texture getImage(){
        return Image;
    }

    public void setStage(Stage s){
        toChange = s;
    }

    public void execute(){
        game.changeStage(toChange);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(Image, this.x, this.y);
    }

    @Override
    public void resize(int nw, int nh) {
        float bx = default_x / Constants.START_SCREEN_WIDTH * nw;
        float by = default_y / Constants.START_SCREEN_HEIGHT * nh;
        float width = default_width / Constants.START_SCREEN_WIDTH * nw;
        float height = default_height / Constants.START_SCREEN_HEIGHT * nh;
        setBounds(bx, by, width, height);
    }
}


