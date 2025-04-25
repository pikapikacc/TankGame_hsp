package com.tutu.tankgame;

/***
 * Bomb 演示爆炸效果
 * @author tutu
 * @version 1.0
 */
public class Bomb {
    private int x;
    private int y;
    private int life = 9;
    boolean isLive = true;

    Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public void lifeDown() {
        if(life > 0) {
            life--;
        } else {
            isLive = false;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLife() {
        return life;
    }
}
