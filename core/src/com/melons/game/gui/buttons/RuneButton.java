package com.melons.game.gui.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.melons.game.Constants;
import com.melons.game.MelonCycle;
import com.melons.game.controllers.LibController;
import com.melons.game.interfaces.SizeChangable;
import com.melons.game.skills.Skill;

public class RuneButton extends Actor implements SizeChangable {

    private Texture Image;
    private String desc;  // Описание скилла
    private MelonCycle game;

    private Skill s;

    private String runeName;

    private float x;
    private float y;
    private float default_x;
    private float default_y;

    private float default_width;
    private float default_height;

    public RuneButton(float x, float y, MelonCycle g, Skill s){

        this.x = x;
        this.y = y;
        this.default_x = x;
        this.default_y = y;
        this.Image = new Texture("Runes/"+s.getTextureName()+".png");
        default_width = Image.getWidth();
        default_height = Image.getHeight();

        this.s = s;

        this.desc = s.getDescription();
        this.runeName = s.getTextureName();

        setBounds(x, y, default_width, default_height);
        setTouchable(Touchable.enabled);
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pick();
                return true;
            }
        });
        game = g;
        g.addResizable(this);
    }

    public void setImage(String path){
        Image = new Texture("Runes/"+path+".png");
    }

    public void setDesc(String d){
        desc = d;
    }

    public Texture getImage(){
        return Image;
    }

    public String getDesc(){
        return desc;
    }

    public String getRuneName(){return runeName;}

    @Override
    public void resize(int new_width, int new_height) {
        float bx = default_x / Constants.START_SCREEN_WIDTH * new_width;
        float by = default_y / Constants.START_SCREEN_HEIGHT * new_height;
        float width = default_width / Constants.START_SCREEN_WIDTH * new_width;
        float height = default_height / Constants.START_SCREEN_HEIGHT * new_height;
        System.out.println("Melon " + bx + " " + by);
        setX(default_x);
        setY(default_y);
        setBounds(bx, by, width, height);

    }

    public void setCoords(float x, float y){
        setX(x);
        setY(y);
        this.x = x;
        this.y = y;
        default_x = x;
        default_y = y;
    }

    public void pick(){
        LibController Minerva = game.getMinerva();
        Minerva.setRune(this);
    }

    public RuneButton copy(){
        return new RuneButton(0, 0, game, s);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(Image, default_x, default_y);
    }
}