package com.tutu.tankgame;

public class Tank {
    private int x;
    private int y;
    private int direct;
    private int speed;
    private int type;
    boolean isLive = true;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
        this.speed = 1;
    }

    public void moveUp() {
        y -= this.speed;
    }

    public void moveRight() {
        x += this.speed;
    }

    public void moveDown() {
        y += this.speed;
    }

    public void moveLeft() {
        x -= this.speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
