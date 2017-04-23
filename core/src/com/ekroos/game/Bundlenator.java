package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

/**
 * Created by Ville on 23.4.2017.
 */

public class Bundlenator {

    CharSequence localText;
    Locale defaultLocale;
    I18NBundle myBundle;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    FreeTypeFontGenerator.FreeTypeFontParameter infoParameter;
    private BitmapFont font;

    public Bundlenator() {

        localText = "";
        defaultLocale = Locale.getDefault();
        myBundle = I18NBundle.createBundle(Gdx.files.internal("myBundle"), defaultLocale);
        // Generate font
        generator = new FreeTypeFontGenerator(Gdx.files.internal("myFont.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        infoParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        font = generator.generateFont(parameter);
    }
    //This method is to get local text anywhere needed, WARNING: should be used outside render
    public CharSequence getLocal(String searchedText) {
        localText = myBundle.get(searchedText);
        return localText;
    }
    //Returns... font
    public BitmapFont getFont() {
        return font;
    }
    public BitmapFont getDescriptionFont() {
        parameter.color = Color.WHITE;
        parameter.size = 30;
        font = generator.generateFont(parameter);
        return font;
    }
    public BitmapFont getHighlyVisibleFont() {
        parameter.color = Color.BLACK;
        parameter.size = 35;
        font = generator.generateFont(parameter);
        return font;
    }
}
