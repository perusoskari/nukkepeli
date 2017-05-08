package com.ekroos.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Puoskari on 30.4.2017.
 */

public class SoundManager {
    private Array<Sound> allSounds;
    private String[] allSoundNames;
    private boolean mute;
    private Sound drumSound;
    private Sound fallingWhistle;
    private Sound campfireBurn;
    private Sound shroomEat;
    private Sound zombieNullified;
    private Sound boxLock;
    private Sound freezing;
    private Sound spikeNullified;
    private Sound ghostDead;
    private Sound rockCrumble;
    private Sound sovietWhistle;
    private Sound tapSound;
    private Sound shieldUp;
    private Sound gameOver;
    private Music menuMusic;
    private boolean menuMusicPlays;

    public SoundManager() {
        mute = false;
        allSounds = new Array<Sound>();
        allSoundNames = new String[14];
        drumSound = Gdx.audio.newSound(Gdx.files.internal("sounds/footstepsFast.wav"));
        fallingWhistle = Gdx.audio.newSound(Gdx.files.internal("sounds/Falling_whistle.wav"));
        campfireBurn = Gdx.audio.newSound(Gdx.files.internal("sounds/sammutus.wav"));
        shroomEat = Gdx.audio.newSound(Gdx.files.internal("sounds/namnam2.wav"));
        zombieNullified = Gdx.audio.newSound(Gdx.files.internal("sounds/zombieFinalHappy.wav"));
        boxLock = Gdx.audio.newSound(Gdx.files.internal("sounds/kopautusBoxDoll.wav"));
        freezing = Gdx.audio.newSound(Gdx.files.internal("sounds/freezing2.wav"));
        spikeNullified = Gdx.audio.newSound(Gdx.files.internal("sounds/cloudOnPlace.wav"));
        ghostDead = Gdx.audio.newSound(Gdx.files.internal("sounds/ghostDed.wav"));
        rockCrumble = Gdx.audio.newSound(Gdx.files.internal("sounds/rockSmash.wav"));
        sovietWhistle = Gdx.audio.newSound(Gdx.files.internal("sounds/sovietWhistle.wav"));
        tapSound = Gdx.audio.newSound(Gdx.files.internal("sounds/buttonPush.wav"));
        shieldUp = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainMenu.mp3"));
        gameOver = Gdx.audio.newSound(Gdx.files.internal("sounds/gameOver.wav"));

        allSounds.add(drumSound);
        allSoundNames[0] = "drumSound";
        allSounds.add(fallingWhistle);
        allSoundNames[1] = "fallingWhistle";
        allSounds.add(campfireBurn);
        allSoundNames[2] = "campfireBurn";
        allSounds.add(shroomEat);
        allSoundNames[3] = "shroomEat";
        allSounds.add(zombieNullified);
        allSoundNames[4] = "zombieNullifiedSound";
        allSounds.add(boxLock);
        allSoundNames[5] = "boxLockSound";
        allSounds.add(freezing);
        allSoundNames[6] = "freezing";
        allSounds.add(spikeNullified);
        allSoundNames[7] = "spikeNullified";
        allSounds.add(ghostDead);
        allSoundNames[8] = "ghostDead";
        allSounds.add(rockCrumble);
        allSoundNames[9] = "rockCrumble";
        allSounds.add(tapSound);
        allSoundNames[10] = "buttonPush";
        allSounds.add(shieldUp);
        allSoundNames[11] = "shield";
        allSounds.add(sovietWhistle);
        allSoundNames[12] = "sovietWhistle";
        allSounds.add(gameOver);
        allSoundNames[13] = "gameOver";
    }

    public void setMute(boolean t) {
        mute = t;
    }

    public void playSound(String name, float volume) {

        if (!mute) {
            for (int i =0; i < allSoundNames.length;i++) {
                if (allSoundNames[i].equals(name)) {
                    allSounds.get(i).play(volume);
                }
            }
        }
    }

    public long playSoundReturnId(String name, float volume) {

        if (!mute) {
            for (int i =0; i < allSoundNames.length;i++) {
                if (allSoundNames[i].equals(name)) {
                    return allSounds.get(i).play(volume);
                }
            }
        }

        return 0;
    }

    public void playSound(String name, float volume, float pitch) {

        if (!mute) {
            for (int i =0; i < allSoundNames.length;i++) {
                if (allSoundNames[i].equals(name)) {
                    allSounds.get(i).play(volume, pitch, 0);
                }
            }
        }
    }

    public long playSoundReturnIdSetLooping(String name, float volume, float pitch) {

        if (!mute) {
            for (int i =0; i < allSoundNames.length;i++) {
                if (allSoundNames[i].equals(name)) {
                    long id = allSounds.get(i).play(volume, pitch, 0);
                    allSounds.get(i).setLooping(id, true);
                    return id;
                }
            }
        }

        return 0;
    }

    public void loopSound(String name, float volume, float pitch) {

            for (int i =0; i < allSoundNames.length;i++) {
                if (allSoundNames[i].equals(name)) {
                    allSounds.get(i).loop(volume, pitch, 0);
                }
            }

    }

    public void pause(String name) {
        for (int i =0; i < allSoundNames.length;i++) {
            if (allSoundNames[i].equals(name)) {
                allSounds.get(i).pause();
            }
        }
    }

    public void stop(String name) {
        for (int i =0; i < allSoundNames.length;i++) {
            if (allSoundNames[i].equals(name)) {
                allSounds.get(i).stop();
            }
        }
    }



    public void stop(String name, long id) {
        for (int i =0; i < allSoundNames.length;i++) {
            if (allSoundNames[i].equals(name)) {
                System.out.println(id);
                allSounds.get(i).stop(id);
            }
        }
    }

    public void resume(String name) {
        for (int i =0; i < allSoundNames.length;i++) {
            if (allSoundNames[i].equals(name)) {
                allSounds.get(i).resume();
            }
        }
    }

    public void pauseAll() {
        for (int i = allSounds.size - 1; i >= 0; i--) {
            allSounds.get(i).pause();
            if (i == 0) {
                System.out.println("at 0 we are");
            }
        }
    }

    public void resumeAll() {
        for (int i = allSounds.size - 1; i >= 0; i--) {
            allSounds.get(i).resume();
        }
    }

    public void stopAll() {
        for (int i = allSounds.size - 1; i >= 0; i--) {
            allSounds.get(i).stop();
        }
    }

    public void dispose() {
        for (int i = 0; i < allSounds.size - 1; i++) {
            allSounds.get(i).dispose();
        }
        menuMusic.dispose();
    }

    public void playMenuMusic(float volume) {
        menuMusic.play();
        menuMusic.setVolume(volume);
        menuMusic.setLooping(true);
        menuMusicPlays = true;
    }

    public boolean menuMusicIsPlaying() {
        return menuMusicPlays;
    }

    public void setMenuMusicIsPlaying(boolean t) {
        menuMusicPlays = t;
    }

    public void stopMenuMusic() {
        menuMusic.stop();
    }
}
