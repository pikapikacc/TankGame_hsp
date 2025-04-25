package com.tutu.tankgame;

/***
 * Shot 子弹类，演示子弹效果
 * @author tutu
 * @version 1.0
 */
public class Bullet implements Runnable {
    private int x;
    private int y;
    private int direct;
    private int speed = 2;
    boolean isLive = true;

    public Bullet(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDirect() {
        return direct;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
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

//    public void setState(boolean isLive) {
//        this.isLive = isLive;
//    }
//
//    public boolean getState() {
//        return this.isLive;
//    }

    @Override
    /***
     * 让子弹飞
     */
    public void run() {
        while(true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //子弹顺着发射方向移动
            switch (this.direct) {
                case 0:
                    this.y -= this.speed;
                    break;
                case 1:
                    this.x += this.speed;
                    break;
                case 2:
                    this.y += this.speed;
                    break;
                case 3:
                    this.x -= this.speed;
                    break;
            }

//            System.out.println("子弹当前坐标 x = " + this.x + ", y = " + this.y);
            //子弹触碰边界自动销毁
            if(!(x >= 0 && x <= 1000 && y >= 0 && y <= 750 && this.isLive)) {
                this.isLive = false;
//                System.out.println("子弹被销毁……");
                break;
            }
        }
    }
}
