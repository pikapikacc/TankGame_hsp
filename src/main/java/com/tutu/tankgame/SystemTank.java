package com.tutu.tankgame;

import java.util.Vector;

public class SystemTank extends Tank implements Runnable {
    private Vector<Bullet> bullets = new Vector<>();
    private Vector<SystemTank> systemTanks = new Vector<>();
    private PlayerTank playerTank = null;

    public SystemTank(int x, int y) {
        super(x, y);
    }

    /***
     * 系统坦克发射一颗子弹
     */
    public void newShot() {
        Bullet bullet = null;
        switch (this.getDirect()) {
            case 0:
                bullet = new Bullet(this.getX() + 20, this.getY(), 0);
                break;
            case 1:
                bullet = new Bullet(this.getX() + 60, this.getY() + 20, 1);
                break;
            case 2:
                bullet = new Bullet(this.getX() + 20, this.getY() + 60, 2);
                break;
            case 3:
                bullet = new Bullet(this.getX(), this.getY() + 20, 3);
                break;
        }

        new Thread(bullet).start();
        this.bullets.add(bullet);
    }

    /***
     * moveRandom 让坦克自由移动
     */
    public void moveRandom() {
        switch (this.getDirect()) {
            case 0:
                for (int i = 0; i < 100; i++) {
                    if (super.getY() > 0 && !isTouchSystemTank() && !isTouchPlayerTank()) {
                        super.moveUp();
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 1:
                for (int i = 0; i < 100; i++) {
                    if (super.getX() + 60 < 1000 && !isTouchSystemTank() && !isTouchPlayerTank()) {
                        super.moveRight();
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 2:
                for (int i = 0; i < 100; i++) {
                    if (super.getY() + 40 < 750 && !isTouchSystemTank() && !isTouchPlayerTank()) {
                        super.moveDown();
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 3:
                for (int i = 0; i < 100; i++) {
                    if (super.getX() > 0 && !isTouchSystemTank() && !isTouchPlayerTank()) {
                        super.moveLeft();
                        ;
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }

        this.setDirect((int) (Math.random() * 4));
    }

    @Override
    public void run() {
        while (true) {
            if (isLive && bullets.size() < 3) {
                newShot();
            }

            moveRandom();
            if (!this.isLive) {
                break;
            }
        }
    }

    /***
     * isTouchSystemTank 判断系统坦克之间边界是否重叠
     * TODO 坦克边界细节有待优化，会出现小部分重叠或越界
     * @return
     */
    boolean isTouchSystemTank() {
        switch (getDirect()) {
            case 0:
                for (int i = 0; i < systemTanks.size(); i++) {
                    SystemTank systemTank = systemTanks.get(i);
                    if (systemTank.getDirect() == 0 || systemTank.getDirect() == 2) {
                        if (this.getX() > systemTank.getX()
                                && this.getX() < systemTank.getX() + 40
                                && this.getY() > systemTank.getY()
                                && this.getY() < systemTank.getY() + 60) {
                            return true;
                        }

                        if (this.getX() + 40 > systemTank.getX()
                                && this.getX() + 40 < systemTank.getX() + 40
                                && this.getY() > systemTank.getY()
                                && this.getY() < systemTank.getY() + 60) {
                            return true;
                        }
                    }
                    if (systemTank.getDirect() == 1 || systemTank.getDirect() == 3) {
                        if (this.getX() > systemTank.getX()
                                && this.getX() < systemTank.getX() + 60
                                && this.getY() > systemTank.getY()
                                && this.getY() < systemTank.getY() + 40) {
                            return true;
                        }
                        if (this.getX() + 40 > systemTank.getX()
                                && this.getX() + 40 < systemTank.getX() + 60
                                && this.getY() > systemTank.getY()
                                && this.getY() < systemTank.getY() + 40) {
                            return true;
                        }
                    }
                }
                break;
            case 1:
                for (int i = 0; i < systemTanks.size(); i++) {
                    SystemTank systemTank = systemTanks.get(i);
                    if (systemTank.getDirect() == 0 || systemTank.getDirect() == 2) {
                        if (this.getX() + 60 > systemTank.getX()
                                && this.getX() + 60 < systemTank.getX() + 40
                                && this.getY() > systemTank.getY()
                                && this.getY() < systemTank.getY() + 60) {
                            return true;
                        }

                        if (this.getX() + 60 > systemTank.getX()
                                && this.getX() + 60 < systemTank.getX() + 40
                                && this.getY() + 40 > systemTank.getY()
                                && this.getY() + 40 < systemTank.getY() + 60) {
                            return true;
                        }
                    }
                    if (systemTank.getDirect() == 1 || systemTank.getDirect() == 3) {
                        if (this.getX() + 60 > systemTank.getX()
                                && this.getX() + 60 < systemTank.getX() + 60
                                && this.getY() > systemTank.getY()
                                && this.getY() < systemTank.getY() + 40) {
                            return true;
                        }
                        if (this.getX() + 60 > systemTank.getX()
                                && this.getX() + 60 < systemTank.getX() + 60
                                && this.getY() + 40 > systemTank.getY()
                                && this.getY() + 40 < systemTank.getY() + 40) {
                            return true;
                        }
                    }
                }
                break;
            case 2:
                for (int i = 0; i < systemTanks.size(); i++) {
                    SystemTank systemTank = systemTanks.get(i);
                    if (systemTank.getDirect() == 0 || systemTank.getDirect() == 2) {
                        if (this.getX() > systemTank.getX()
                                && this.getX() < systemTank.getX() + 40
                                && this.getY() + 60 > systemTank.getY()
                                && this.getY() + 60 < systemTank.getY() + 60) {
                            return true;
                        }

                        if (this.getX() + 40 > systemTank.getX()
                                && this.getX() + 40 < systemTank.getX() + 40
                                && this.getY() + 60 > systemTank.getY()
                                && this.getY() + 60 < systemTank.getY() + 60) {
                            return true;
                        }
                    }
                    if (systemTank.getDirect() == 1 || systemTank.getDirect() == 3) {
                        if (this.getX() > systemTank.getX()
                                && this.getX() < systemTank.getX() + 60
                                && this.getY() + 60 > systemTank.getY()
                                && this.getY() + 60 < systemTank.getY() + 40) {
                            return true;
                        }
                        if (this.getX() + 40 > systemTank.getX()
                                && this.getX() + 40 < systemTank.getX() + 60
                                && this.getY() + 60 > systemTank.getY()
                                && this.getY() + 60 < systemTank.getY() + 40) {
                            return true;
                        }
                    }
                }
                break;
            case 3:
                for (int i = 0; i < systemTanks.size(); i++) {
                    SystemTank systemTank = systemTanks.get(i);
                    if (systemTank.getDirect() == 0 || systemTank.getDirect() == 2) {
                        if (this.getX() > systemTank.getX()
                                && this.getX() < systemTank.getX() + 40
                                && this.getY() > systemTank.getY()
                                && this.getY() < systemTank.getY() + 60) {
                            return true;
                        }

                        if (this.getX() > systemTank.getX()
                                && this.getX() < systemTank.getX() + 40
                                && this.getY() + 40 > systemTank.getY()
                                && this.getY() + 40 < systemTank.getY() + 60) {
                            return true;
                        }
                    }
                    if (systemTank.getDirect() == 1 || systemTank.getDirect() == 3) {
                        if (this.getX() > systemTank.getX()
                                && this.getX() < systemTank.getX() + 60
                                && this.getY() > systemTank.getY()
                                && this.getY() < systemTank.getY() + 40) {
                            return true;
                        }
                        if (this.getX() > systemTank.getX()
                                && this.getX() < systemTank.getX() + 60
                                && this.getY() + 40 > systemTank.getY()
                                && this.getY() + 40 < systemTank.getY() + 40) {
                            return true;
                        }
                    }
                    break;
                }
        }
        return false;
    }

    /***
     * isTouchPlayerTank 判断系统坦克和玩家坦克之间边界是否重叠
     * TODO 坦克边界细节有待优化，会出现小部分重叠或越界
     * @return
     */
    boolean isTouchPlayerTank() {
        if(playerTank != null && playerTank.isLive) {
            switch (getDirect()) {
                case 0:
                    if (playerTank.getDirect() == 0 || playerTank.getDirect() == 2) {
                        if (this.getX() > playerTank.getX()
                                && this.getX() < playerTank.getX() + 40
                                && this.getY() > playerTank.getY()
                                && this.getY() < playerTank.getY() + 60) {
                            return true;
                        }

                        if (this.getX() + 40 > playerTank.getX()
                                && this.getX() + 40 < playerTank.getX() + 40
                                && this.getY() > playerTank.getY()
                                && this.getY() < playerTank.getY() + 60) {
                            return true;
                        }
                    }
                    if (playerTank.getDirect() == 1 || playerTank.getDirect() == 3) {
                        if (this.getX() > playerTank.getX()
                                && this.getX() < playerTank.getX() + 60
                                && this.getY() > playerTank.getY()
                                && this.getY() < playerTank.getY() + 40) {
                            return true;
                        }
                        if (this.getX() + 40 > playerTank.getX()
                                && this.getX() + 40 < playerTank.getX() + 60
                                && this.getY() > playerTank.getY()
                                && this.getY() < playerTank.getY() + 40) {
                            return true;
                        }
                    }
                    break;
                case 1:
                    if (playerTank.getDirect() == 0 || playerTank.getDirect() == 2) {
                        if (this.getX() + 60 > playerTank.getX()
                                && this.getX() + 60 < playerTank.getX() + 40
                                && this.getY() > playerTank.getY()
                                && this.getY() < playerTank.getY() + 60) {
                            return true;
                        }

                        if (this.getX() + 60 > playerTank.getX()
                                && this.getX() + 60 < playerTank.getX() + 40
                                && this.getY() + 40 > playerTank.getY()
                                && this.getY() + 40 < playerTank.getY() + 60) {
                            return true;
                        }
                    }
                    if (playerTank.getDirect() == 1 || playerTank.getDirect() == 3) {
                        if (this.getX() + 60 > playerTank.getX()
                                && this.getX() + 60 < playerTank.getX() + 60
                                && this.getY() > playerTank.getY()
                                && this.getY() < playerTank.getY() + 40) {
                            return true;
                        }
                        if (this.getX() + 60 > playerTank.getX()
                                && this.getX() + 60 < playerTank.getX() + 60
                                && this.getY() + 40 > playerTank.getY()
                                && this.getY() + 40 < playerTank.getY() + 40) {
                            return true;
                        }
                    }
                    break;
                case 2:
                    if (playerTank.getDirect() == 0 || playerTank.getDirect() == 2) {
                        if (this.getX() > playerTank.getX()
                                && this.getX() < playerTank.getX() + 40
                                && this.getY() + 60 > playerTank.getY()
                                && this.getY() + 60 < playerTank.getY() + 60) {
                            return true;
                        }

                        if (this.getX() + 40 > playerTank.getX()
                                && this.getX() + 40 < playerTank.getX() + 40
                                && this.getY() + 60 > playerTank.getY()
                                && this.getY() + 60 < playerTank.getY() + 60) {
                            return true;
                        }
                    }
                    if (playerTank.getDirect() == 1 || playerTank.getDirect() == 3) {
                        if (this.getX() > playerTank.getX()
                                && this.getX() < playerTank.getX() + 60
                                && this.getY() + 60 > playerTank.getY()
                                && this.getY() + 60 < playerTank.getY() + 40) {
                            return true;
                        }
                        if (this.getX() + 40 > playerTank.getX()
                                && this.getX() + 40 < playerTank.getX() + 60
                                && this.getY() + 60 > playerTank.getY()
                                && this.getY() + 60 < playerTank.getY() + 40) {
                            return true;
                        }
                    }
                    break;
                case 3:
                    if (playerTank.getDirect() == 0 || playerTank.getDirect() == 2) {
                        if (this.getX() > playerTank.getX()
                                && this.getX() < playerTank.getX() + 40
                                && this.getY() > playerTank.getY()
                                && this.getY() < playerTank.getY() + 60) {
                            return true;
                        }

                        if (this.getX() > playerTank.getX()
                                && this.getX() < playerTank.getX() + 40
                                && this.getY() + 40 > playerTank.getY()
                                && this.getY() + 40 < playerTank.getY() + 60) {
                            return true;
                        }
                    }
                    if (playerTank.getDirect() == 1 || playerTank.getDirect() == 3) {
                        if (this.getX() > playerTank.getX()
                                && this.getX() < playerTank.getX() + 60
                                && this.getY() > playerTank.getY()
                                && this.getY() < playerTank.getY() + 40) {
                            return true;
                        }
                        if (this.getX() > playerTank.getX()
                                && this.getX() < playerTank.getX() + 60
                                && this.getY() + 40 > playerTank.getY()
                                && this.getY() + 40 < playerTank.getY() + 40) {
                            return true;
                        }
                        break;

                    }
            }
        }
        return false;
    }

    public Vector<Bullet> getBullets() {
        return this.bullets;
    }

    public void setSystemTanks(Vector<SystemTank> systemTanks) {
        this.systemTanks = systemTanks;
    }

    public void setPlayerTank(PlayerTank playerTank) {
        this.playerTank = playerTank;
    }
}
