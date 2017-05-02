package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 30.4.2017.
 */

public class SoundManager {
    private Array<Sound> allSounds;
    private Sound drumSound;
    private Sound fallingWhistle;
    private Sound campfireBurn;
    private Sound shroomEat;
    private Sound zombieNullified;
    private Sound boxLock;

    public SoundManager() {
        allSounds = new Array<Sound>();
        drumSound = Gdx.audio.newSound(Gdx.files.internal("sounds/drumSound.wav"));
        fallingWhistle = Gdx.audio.newSound(Gdx.files.internal("sounds/Falling_whistle.wav"));
        campfireBurn = Gdx.audio.newSound(Gdx.files.internal("sounds/feuer.wav"));
        shroomEat = Gdx.audio.newSound(Gdx.files.internal("sounds/namnam.wav"));
        zombieNullified = Gdx.audio.newSound(Gdx.files.internal("sounds/zombieHappy.wav"));
        boxLock = Gdx.audio.newSound(Gdx.files.internal("sounds/kopautusBoxDoll.wav"));

        allSounds.add(drumSound);
        allSounds.add(fallingWhistle);
        allSounds.add(campfireBurn);
        allSounds.add(shroomEat);
        allSounds.add(zombieNullified);
        allSounds.add(boxLock);
    }

    public void playSound(String name, float volume, boolean mute) {

        if (!mute) {
            if (name.equals("zombieNullifiedSound")) {
                zombieNullified.play(volume);
            }

            if (name.equals("shroomEat")) {
                shroomEat.play(volume);
            }

            if (name.equals("campfireBurn")) {
                campfireBurn.play(volume);
            }

            if (name.equals("fallingWhistle")) {
                fallingWhistle.play(volume);
            }

            if (name.equals("drumSound")) {
                drumSound.play(volume);
            }

            if (name.equals("boxLockSound")) {
                boxLock.play(volume);
            }
        }
    }

    public void pause(String name) {
        if (name.equals("zombieNullifiedSound")) {
            zombieNullified.pause();
        }

        if (name.equals("shroomEat")) {
            shroomEat.pause();
        }

        if (name.equals("campfireBurn")) {
            campfireBurn.pause();
        }

        if (name.equals("fallingWhistle")) {
            fallingWhistle.pause();
        }

        if (name.equals("drumSound")) {
            drumSound.pause();
        }

        if (name.equals("boxLockSound")) {
            boxLock.pause();
        }
    }

    public void resume(String name) {
        if (name.equals("zombieNullifiedSound")) {
            zombieNullified.resume();
        }

        if (name.equals("shroomEat")) {
            shroomEat.resume();
        }

        if (name.equals("campfireBurn")) {
            campfireBurn.resume();
        }

        if (name.equals("fallingWhistle")) {
            fallingWhistle.resume();
        }

        if (name.equals("drumSound")) {
            drumSound.resume();
        }

        if (name.equals("boxLockSound")) {
            boxLock.resume();
        }
    }

    public void pauseAll() {
        for (int i = allSounds.size; i > 0; i--) {
            allSounds.get(i).pause();
        }
    }

    public void resumeAll() {
        for (int i = allSounds.size; i > 0; i--) {
            allSounds.get(i).resume();
        }
    }

    public void stopAll() {
        for (int i = allSounds.size; i > 0; i--) {
            allSounds.get(i).stop();
        }
    }

    public void dispose() {
        for (int i = allSounds.size - 1; i > 0; i--) {
            allSounds.get(i).dispose();
        }
    }
}
