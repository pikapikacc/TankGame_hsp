package com.tutu.tankgame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

/***
 * class Recorder 记录游戏数据
 * @author tutu
 * @version v1.0
 */
public class Recoder {
    private static int destroyedTankNum;
    //系统坦克数据
    private static Vector<SystemTank> systemTanks = null;
    //用户坦克数据
    private static PlayerTank playerTank = null;
    //上局游戏数据文件路径
    private static String tankGameDataPath = null;

    static {
        try {
            //获取jar包父目录
            String jarDir = new File(TankGamePanel.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI())
                    .getParent();
            //拼接上局游戏数据文件路径
            tankGameDataPath = jarDir + File.separator + "tankGameData.txt";
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void saveGameData() {
        if(tankGameDataPath == null) {
            System.out.println("Recorder.tankGameDataPath is null...");
            return;
        }

        //如果数据文件不存在，创建
        File file = new File(tankGameDataPath);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Create tankGameData.txt  suceess...");
        }

        //将游戏数据写入文件
        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get(tankGameDataPath)), StandardCharsets.UTF_8))) {
            bw.write(Recoder.destroyedTankNum + "\r\n");
            if(playerTank != null && playerTank.isLive) {
                bw.write("true");
                bw.newLine();
                String record = playerTank.getX() + " " + playerTank.getY() + " " + playerTank.getDirect();
                bw.write(record);
                bw.newLine();
            } else {
                bw.write("false");
                bw.newLine();
            }

            if(systemTanks != null) {
                for(int i = 0; i < systemTanks.size(); i++) {
                    SystemTank systemTank = systemTanks.get(i);
                    if(systemTank.isLive) {
                        String record = systemTank.getX() + " " + systemTank.getY() + " " + systemTank.getDirect();
                        bw.write(record + "\r\n");
                    }
                }
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void setSystemTanks(Vector<SystemTank> systemTanks) {
        Recoder.systemTanks = systemTanks;
    }

    public static void setPlayerTank(PlayerTank playerTank) {
        Recoder.playerTank = playerTank;
    }

    public static void addDestroyedTankNum() {
        Recoder.destroyedTankNum++;
    }

    public static void setDestroyedTankNum(int destroyedTankNum) {
        Recoder.destroyedTankNum = destroyedTankNum;
    }

    public static String getTankGameDataPath() {
        return tankGameDataPath;
    }
}
