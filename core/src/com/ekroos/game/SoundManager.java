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
    private Music menuMusic;

    public SoundManager() {
        allSounds = new Array<Sound>();
        allSoundNames = new String[10];
        drumSound = Gdx.audio.newSound(Gdx.files.internal("sounds/drumSound.wav"));
        fallingWhistle = Gdx.audio.newSound(Gdx.files.internal("sounds/Falling_whistle.wav"));
        campfireBurn = Gdx.audio.newSound(Gdx.files.internal("sounds/feuer.wav"));
        shroomEat = Gdx.audio.newSound(Gdx.files.internal("sounds/namnam2.wav"));
        zombieNullified = Gdx.audio.newSound(Gdx.files.internal("sounds/korina.wav"));
        boxLock = Gdx.audio.newSound(Gdx.files.internal("sounds/kopautusBoxDoll.wav"));
        freezing = Gdx.audio.newSound(Gdx.files.internal("sounds/freezing2.wav"));
        spikeNullified = Gdx.audio.newSound(Gdx.files.internal("sounds/cloudOnPlace.wav"));
        ghostDead = Gdx.audio.newSound(Gdx.files.internal("sounds/ghostDed.wav"));
        rockCrumble = Gdx.audio.newSound(Gdx.files.internal("sounds/rockSmash.wav"));
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainMenu.mp3"));

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
    }

    public void playSound(String name, float volume, boolean mute) {

        if (!mute) {
            for (int i =0; i < allSoundNames.length;i++) {
                if (allSoundNames[i].equals(name)) {
                    allSounds.get(i).play(volume);
                }
            }
        }
    }

    public void playSound(String name, float volume, float pitch, boolean mute) {

        if (!mute) {
            for (int i =0; i < allSoundNames.length;i++) {
                if (allSoundNames[i].equals(name)) {
                    allSounds.get(i).play(volume, pitch, 0);
                }
            }
        }
    }

    public long playSoundReturnIdSetLooping(String name, float volume, float pitch, boolean mute) {

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
        for (int i = allSounds.size - 1; i > 0; i--) {
            allSounds.get(i).pause();
        }
    }

    public void resumeAll() {
        for (int i = allSounds.size - 1; i > 0; i--) {
            allSounds.get(i).resume();
        }
    }

    public void stopAll() {
        for (int i = allSounds.size - 1; i > 0; i--) {
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
    }

    public void stopMenuMusic() {
        menuMusic.stop();
    }
}
