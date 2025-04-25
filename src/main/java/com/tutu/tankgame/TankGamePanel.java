package com.tutu.tankgame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Vector;

public class TankGamePanel extends JPanel implements KeyListener,Runnable {
    PlayerTank playerTank = null;
    Vector<SystemTank> systemTanks = new Vector<>();
    Vector<Bomb> bombs = new Vector<>();

    int systemTankSize = 6;

    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    {
        try {
            image1 = ImageIO.read(TankGamePanel.class.getResource("/bomb01.png"));
            image2 = ImageIO.read(TankGamePanel.class.getResource("/bomb02.png"));
            image3 = ImageIO.read(TankGamePanel.class.getResource("/bomb03.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream soundInputStream = TankGamePanel.class.getResourceAsStream("/bgMusic.wav");
        new Thread(new AePlayWave(soundInputStream)).start();
    }

    //新游戏
    public TankGamePanel() {
        //初始化玩家坦克
        this.playerTank = new PlayerTank(500, 500);
        playerTank.setSpeed(10);
        playerTank.setSystemTanks(systemTanks);
        Recoder.setPlayerTank(playerTank);
        //初始化系统坦克
        for(int i = 0; i < this.systemTankSize; i++) {
            //创建一个系统坦克
            SystemTank systemTank = new SystemTank(100*(i+1), 100);
            systemTank.setDirect(2);
            systemTank.setSystemTanks(systemTanks);
            systemTank.setPlayerTank(playerTank);
            //系统坦克自由移动
            new Thread(systemTank).start();
            //加入到坦克集合
            systemTanks.add(systemTank);
        }

        Recoder.setSystemTanks(systemTanks);
        //Toolkit.getDefaultToolkit().getImage() 加载的图片是异步
        //image1 = Toolkit.getDefaultToolkit().getImage(TankGamePanel.class.getResource("/bomb01.png"));
        //image2 = Toolkit.getDefaultToolkit().getImage(TankGamePanel.class.getResource("/bomb02.png"));
        //image3 = Toolkit.getDefaultToolkit().getImage(TankGamePanel.class.getResource("/bomb03.png"));
    }

    //继续上局游戏

    /***
     *
     * @param tankGameDataPath 上局数据
     */
    public TankGamePanel(String tankGameDataPath){
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(tankGameDataPath), StandardCharsets.UTF_8))) {
            Recoder.setDestroyedTankNum(Integer.parseInt(br.readLine()));

            boolean playerIsLive = Boolean.parseBoolean(br.readLine());
            if(playerIsLive) {
                String[] playerTankXYD = br.readLine().split(" ");
                this.playerTank = new PlayerTank(Integer.parseInt(playerTankXYD[0]), Integer.parseInt(playerTankXYD[1]));
                playerTank.setDirect(Integer.parseInt(playerTankXYD[2]));
                playerTank.setSpeed(10);
                Recoder.setPlayerTank(playerTank);
            }

            String newLine = null;
            while((newLine = br.readLine()) != null) {
                String[] systemTankXYD = newLine.split(" ");
                SystemTank systemTank = new SystemTank(Integer.parseInt(systemTankXYD[0]), Integer.parseInt(systemTankXYD[1]));
                systemTank.setDirect(Integer.parseInt(systemTankXYD[2]));
                systemTank.setPlayerTank(playerTank);
                systemTanks.add(systemTank);
                new Thread(systemTank).start();
            }
            Recoder.setSystemTanks(systemTanks);

        } catch (IOException e) {
            new TankGamePanel();
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //绘制背景
        g.fillRect(0,0,1000,750);

        //绘制玩家tank
        if(playerTank != null && playerTank.isLive) {
            drawTank(g, playerTank.getX(), playerTank.getY(), playerTank.getType(), playerTank.getDirect());

            //绘制玩家子弹
            Vector<Bullet> playerTankBullets = playerTank.getBullets();
            for(int i = 0; i < playerTankBullets.size(); i++) {
                Bullet bullet = playerTankBullets.get(i);
                if(bullet != null && bullet.isLive) {
                    g.setColor(Color.white);
                    g.fill3DRect(bullet.getX(),bullet.getY(), 2, 2, false);
                } else {
                    playerTankBullets.remove(bullet);
                }
            }
        } else {
            playerTank = null;
        }

        //绘制系统tank
        for (int i = 0; i < systemTanks.size(); i++) {
            SystemTank systemTank = systemTanks.get(i);
            if(systemTank.isLive) {
                drawTank(g, systemTank.getX(), systemTank.getY(), systemTank.getType(), systemTank.getDirect());
                Iterator<Bullet> it = systemTank.getBullets().iterator();
                while(it.hasNext()) {
                    Bullet bullet = it.next();
                    if(bullet.isLive) {
                        g.fill3DRect(bullet.getX(), bullet.getY(),2,2,false);
                    } else {
                        it.remove();
                    }
                }
            } else {
                systemTanks.remove(systemTank);
            }
        }

        //绘制爆炸效果
        for(int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if(bomb.getLife() > 6) {
                boolean isSuccess1 = g.drawImage(image1, bomb.getX(), bomb.getY(), 60,60,this);
            } else if(bomb.getLife() > 3) {
                boolean isSuccess2 = g.drawImage(image2, bomb.getX(), bomb.getY(), 60,60,this);
            } else {
                boolean isSuccess3 = g.drawImage(image3, bomb.getX(), bomb.getY(), 60,60,this);
            }
            bomb.lifeDown();
            if(bomb.getLife() == 0) {
                bombs.remove(bomb);
            }
        }

        if(playerTank != null && playerTank.isLive) {
            showGameInfo(g, systemTankSize, systemTanks.size(), playerTank.isLive);
        } else {
            showGameInfo(g, systemTankSize, systemTanks.size(), false);
        }


    }

    /**
     *
     * @param g 画笔
     * @param x 坦克左上角x坐标
     * @param y 坦克右上角y坐标
     * @param type 坦克类型
     * @param direct 坦克方向（上下左右）
     */
    public void drawTank(Graphics g, int x, int y, int type, int direct) {
        //根据坦克的颜色画出不同的坦克
        switch (type) {
            case 0://系统的坦克
                g.setColor(Color.cyan);
                break;
            case 1://玩家的坦克
                g.setColor(Color.yellow);
                break;
        }

        //根据坦克的方向画出不同的坦克
        switch (direct) {
            case 0://up
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画出圆形炮台
                g.drawLine(x + 20, y + 30, x + 20, y);//画出炮
                break;
            case 1://right
                g.fill3DRect(x, y, 60, 10, false);//画出坦克上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克盖子
                g.fillOval(x + 20, y + 10, 20, 20);//画出圆形炮台
                g.drawLine(x + 30, y + 20, x + 60, y + 20);//画出炮筒
                break;
            case 2://down
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画出圆形炮台
                g.drawLine(x + 20, y + 30, x + 20, y + 60);//画出炮筒
                break;
            case 3://left
                g.fill3DRect(x, y, 60, 10, false);//画出坦克上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克盖子
                g.fillOval(x + 20, y + 10, 20, 20);//画出圆形炮台
                g.drawLine(x + 30, y + 20, x, y + 20);//画出炮筒
                break;
        }
    }

    public void showGameInfo(Graphics g, int systemTankSize, int remainingNums, boolean playerTankIsLive) {
        g.setColor(Color.cyan);
        drawTank(g, 1060, 125, 0, 0);

        g.setColor(Color.black);
        g.setFont(new Font("隶书", Font.BOLD, 28));
        g.drawString("击毁坦克", 1050, 100);
        int num = systemTankSize-remainingNums;
        g.drawString(new String(num + ""), 1140, 160);

        g.setColor(Color.white);
        g.setFont(new Font("隶书", Font.BOLD, 60));
        if(num == systemTankSize && playerTankIsLive) {
            g.drawString("恭喜，你取得了胜利！", 260, 375);
        } else if(!playerTankIsLive) {
            g.drawString("很遗憾，再接再厉！", 260, 375);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    /***
     * keyPressed 玩家坦克控制 '↑ → ↓ ←'控制方向，'A'射击
     */
    public void keyPressed(KeyEvent e) {
        if(playerTank != null && playerTank.isLive) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    playerTank.setDirect(0);
                    if(playerTank.getY() > 0 && !playerTank.isTouchSystemTank()) {
                        playerTank.moveUp();
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    playerTank.setDirect(1);
                    if(playerTank.getX() + 60 < 1000 && !playerTank.isTouchSystemTank()) {
                        playerTank.moveRight();
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    playerTank.setDirect(2);
                    if(playerTank.getY() + 60 < 750 && !playerTank.isTouchSystemTank()) {
                        playerTank.moveDown();
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    playerTank.setDirect(3);
                    if(playerTank.getX() > 0 && !playerTank.isTouchSystemTank()) {
                        playerTank.moveLeft();
                    }
                    break;
                case KeyEvent.VK_A:
                    playerTank.fire();
                    break;
            }
        }
        this.repaint();
    }

    /***
     * hitTank 判断子弹是否击中坦克
     * @param bullet 系统坦克子弹  玩家子弹
     * @param tank   玩家坦克     系统坦克
     */
    public void hitTank(Bullet bullet, Tank tank) {
            switch (tank.getDirect()) {
                case 0:
                case 2:
                    if(bullet.getX() > tank.getX() && bullet.getX() < tank.getX()+40 &&
                            bullet.getY() > tank.getY() && bullet.getY() < tank.getY()+60) {
                        bullet.isLive = false;
                        tank.isLive = false;
                        if(tank instanceof SystemTank) {
                            Recoder.addDestroyedTankNum();
                        }
                        Bomb bomb = new Bomb(tank.getX(), tank.getY());
                        bombs.add(bomb);
                    }
                    break;
                case 1:
                case 3:
                    if(bullet.getX() > tank.getX() && bullet.getX() < tank.getX()+60 &&
                            bullet.getY() > tank.getY() && bullet.getY() < tank.getY()+40) {
                        bullet.isLive = false;
                        tank.isLive = false;
                        if(tank instanceof SystemTank) {
                            Recoder.addDestroyedTankNum();
                        }
                        Bomb bomb = new Bomb(tank.getX(), tank.getY());
                        bombs.add(bomb);
                    }
                    break;
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    //刷新屏幕
    public void run() {
        while(true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //判断是否击中了敌人的坦克
            if(playerTank != null && playerTank.isLive) {
                Vector<Bullet> playerTankBullets = playerTank.getBullets();
                for (Bullet bullet : playerTankBullets) {
                    if (bullet != null && bullet.isLive) {
                        for (SystemTank systemTank : systemTanks) {
                            hitTank(bullet, systemTank);
                        }
                    }
                }
            }

            //判断敌人子弹是否击中了自己
            for(SystemTank systemTank : systemTanks) {
                if(systemTank != null && systemTank.isLive) {
                    Vector<Bullet> systemTankBullets = systemTank.getBullets();
                    for(Bullet bullet : systemTankBullets) {
                        if(bullet != null && bullet.isLive && playerTank != null && playerTank.isLive) {
                            hitTank(bullet, playerTank);
                        }
                    }
                }
            }

            this.repaint();
        }
    }
}
