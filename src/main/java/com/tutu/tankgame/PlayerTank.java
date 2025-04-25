package com.tutu.tankgame;

import java.util.Vector;

/***
 * PlayerTank 玩家坦克类
 * @author tutu
 * @version 1.0
 */
public class PlayerTank extends Tank {
    private Vector<Bullet> bullets = new Vector<>();
    private Vector<SystemTank> systemTanks = new Vector<>();

    public PlayerTank(int x, int y) {
        super(x, y);
        this.setType(1);
        this.setDirect(0);
    }

    public void fire() {
        //玩家至多有五颗子弹存活
        if(bullets.size() >= 5) {
            return;
        }
        Bullet bullet = null;
        //根据坦克目前方向，发射子弹
        switch (this.getDirect()) {
            case 0:
                bullet = new Bullet(this.getX()+20, this.getY(), 0);
                break;
            case 1:
                bullet = new Bullet(this.getX()+60, this.getY()+20, 1);
                break;
            case 2:
                bullet = new Bullet(this.getX()+20, this.getY()+60, 2);
                break;
            case 3:
                bullet = new Bullet(this.getX(), this.getY()+20, 3);
                break;
        }
        this.bullets.add(bullet);
        new Thread(bullet).start();
    }

    /***
     * isTouchSystemTank 判断坦克边界是否重叠
     * TODO 坦克边界细节有待优化，会出现小部分重叠或越界
     * @return
     */
    boolean isTouchSystemTank() {
        switch (getDirect()) {
            case 0:
                for (SystemTank systemTank : systemTanks) {
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
                for (SystemTank systemTank : systemTanks) {
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
                for (SystemTank systemTank : systemTanks) {
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
                for (SystemTank systemTank : systemTanks) {
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

    public Vector<Bullet> getBullets() {
        return bullets;
    }
    public void setSystemTanks(Vector<SystemTank> systemTanks) {
        this.systemTanks = systemTanks;
    }
}
