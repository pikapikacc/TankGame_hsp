package com.tutu.tankgame;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * AePlayWave 播放背景音乐
 * @author 韩顺平 tutu
 * @version 1.1
 */
public class AePlayWave extends Thread {
    //private String filename;
    private InputStream soundInputStream = null;

    //通过stream读取音频，以适应jar包直接运行
    public AePlayWave(InputStream soundInputStream) {
        this.soundInputStream = soundInputStream;
    }

    public void run() {

        //File soundFile = new File(filename);

        AudioInputStream audioInputStream = null;
        try {
            //需要将InputStream包装成BufferedInputStream
            audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(soundInputStream));
        } catch (Exception e1) {
            e1.printStackTrace();
            return;
        }

        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try {
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        auline.start();
        int nBytesRead = 0;
        //这是缓冲
        byte[] abData = new byte[512];

        try {
            while (nBytesRead != -1) {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0)
                    auline.write(abData, 0, nBytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            auline.drain();
            auline.close();
        }

    }
}

