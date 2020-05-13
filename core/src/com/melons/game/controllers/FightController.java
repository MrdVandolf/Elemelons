package com.melons.game.controllers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.melons.game.Constants;
import com.melons.game.MelonCycle;
import com.melons.game.MelonMage;
import com.melons.game.gui.GuiButton;
import com.melons.game.gui.Panel;
import com.melons.game.skills.Skill;

import org.omg.CORBA.UserException;

import java.util.ArrayList;

public class FightController {

    private boolean mustChange = false;
    private boolean pickable = true;

    ArrayList<MelonMage> rivals;
    MelonMage player;
    MelonMage current_melon;
    int current_index = 0;
    Skill picked_skill = null;

    private MelonCycle game;

    Stage field;

    public FightController(ArrayList<MelonMage> melons, MelonMage player, MelonCycle gam, Stage g){
        rivals = melons;
        this.player = player;
        player.setSeedDraw(true);
        current_melon = rivals.get(0);
        field = g;
        game = gam;
        for (MelonMage m: rivals){
            m.setFightController(this);
        }
    }

    public void changeTurn(){
        current_index = current_index >= rivals.size() - 1 ? 0 : current_index + 1;
        current_melon = rivals.get(current_index);
        current_melon.refreshSeeds();
        current_melon.runOverBuffs();
        mustChange = false;
        if (current_melon != player){changeTurn();}
    }

    public void pick(Skill skill){
        if (picked_skill == skill){
            unpick();
        }
        else if (pickable) {
            for (MelonMage i: rivals){
                if (i != player){
                    i.setMark();
                }
                else{
                    i.setShade();
                    i.showSeedsToUse(skill.getSeeds());
                }
            }
            picked_skill = skill;
            System.out.println("picked");
        }
    }

    public void unpick(){
        for (MelonMage i: rivals){
            i.unsetMark();
            i.unsetShade();
        }
        current_melon.showSeedsToUse(0);
        picked_skill = null;
    }

    public void pickMelon(MelonMage m){
        if (picked_skill != null && m != player && pickable) {
            picked_skill.setTarget(current_melon, m);
            current_melon.decreaseSeeds(picked_skill.getSeeds());
            unpick();

        }
    }

    public void addActor(Actor a){
        field.addActor(a);
    }

    public void setPickable(boolean v){
        pickable = v;
        if (pickable && mustChange){
            changeTurn();
        }
    }

    public boolean getPickable(){
        return pickable;
    }

    public void setMustChange(boolean v){
        mustChange = v;
    }

    public void defeated(MelonMage m){
        for (MelonMage i: rivals){
            if (i != m){
                i.refreshAll();
            }
        }

        pickable = false;
        System.out.println("Defeated!");

        Texture win = new Texture("GUI/Panels/message_panel.png");
        Panel message_window = new Panel(Constants.START_SCREEN_WIDTH / 2 - win.getWidth() / 2, Constants.START_SCREEN_HEIGHT / 2 - win.getHeight() / 2, "GUI/Panels/message_panel.png");

        field.addActor(message_window);

        GuiButton ok = new GuiButton(Constants.START_SCREEN_WIDTH / 2 - win.getWidth() / 3, Constants.START_SCREEN_HEIGHT / 2 - win.getHeight() / 3, game, "Ок", field);

        field.addActor(ok);
        ok.setStage(game.getMain());
    }

}
