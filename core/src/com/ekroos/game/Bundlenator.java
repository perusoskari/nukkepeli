package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

/**
 * Created by Ville on 23.4.2017.
 *
 */

/**
 * Generates bundles for other classes that need them.
 * Also offers differrent kinds of fonts.
 */
public class Bundlenator {

    CharSequence localText;
    Locale defaultLocale;
    I18NBundle myBundle;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator generator2;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    FreeTypeFontGenerator.FreeTypeFontParameter infoParameter;
    private BitmapFont font;

    /**
     *Creates a bundlenator containing bundle.
     */
    public Bundlenator() {

        localText = "";
        defaultLocale = Locale.getDefault();
        myBundle = I18NBundle.createBundle(Gdx.files.internal("myBundle"), defaultLocale);

        // Generate font
        generator = new FreeTypeFontGenerator(Gdx.files.internal("myFont.ttf"));
        generator2 = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));

        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        infoParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        font = generator.generateFont(parameter);
    }

    /**
     * Returns a localized version of a needed text.
     * @param searchedText a String on the myBundle.properties
     * @return localized text
     */
    public CharSequence getLocal(String searchedText) {
        localText = myBundle.get(searchedText);
        return localText;
    }

    /**
     * Returns font
     * @return font
     */
    public BitmapFont getFont() {
        return font;
    }
    public BitmapFont getDescriptionFont() {
        parameter.color = Color.WHITE;
        parameter.size = 23;
        font = generator.generateFont(parameter);
        return font;
    }
    /**
     * Returns font
     * @return font
     */
    public BitmapFont getHighlyVisibleFont() {
        parameter.color = Color.BLACK;
        parameter.size = 35;
        font = generator.generateFont(parameter);
        return font;
    }
    /**
     * Returns font
     * @return font
     */
    public BitmapFont getNoNonsenseFont() {
        parameter.color = Color.WHITE;
        parameter.size = 32;
        font = generator2.generateFont(parameter);
        return font;
    }
    /**
     * Returns font
     * @return font
     */
    public BitmapFont getMediumBlack() {
        parameter.color = Color.BLACK;
        parameter.size = 20;
        font = generator.generateFont(parameter);
        return font;
    }
}
