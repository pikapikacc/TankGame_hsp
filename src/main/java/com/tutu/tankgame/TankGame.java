package com.tutu.tankgame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class TankGame extends JFrame {
    private TankGamePanel panel = null;

    public static void main(String[] args) {
        new TankGame();
    }

    public TankGame() {
        //弹窗 YES开始新游戏 NO继续上局游戏
        int code = JOptionPane.showConfirmDialog(null, "新游戏？", "确认",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(code == JOptionPane.YES_OPTION) {
            this.panel = new TankGamePanel();
        } else if(code == JOptionPane.NO_OPTION) {
            this.panel = new TankGamePanel(Recoder.getTankGameDataPath());
        } else {
            return;
        }

        new Thread(this.panel).start();
        this.add(this.panel);
        //监听敲击键盘
        this.addKeyListener(this.panel);
        //设置画框大小
        this.setSize(1200,750);
        //关闭窗口退出
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口可见性
        this.setVisible(true);
        //监听关闭窗口按钮
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //持久化数据
                Recoder.saveGameData();
                System.exit(0);
            }
        });
    }
}
