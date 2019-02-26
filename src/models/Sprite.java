package models;

import javafx.scene.canvas.GraphicsContext;

public class Sprite {

    /**
     * initial layout
     */

    private int id;
    private int posX;
    private int posY;
    private GraphicsContext graphicsContext;

    public Sprite(GraphicsContext graphicsContext, int id, int posX, int posY){
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.graphicsContext = graphicsContext;
    }

    public int getId() {
        return id;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }


    public void draw(){

    }
}
