package com.sjavaproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class MapGenerator {
    public int map1[][];
    public int brickwidth;
    public int brickheight;

    public MapGenerator(int row, int col) {
        map1 = new int[row][col];
        for (int i = 0; i < map1.length; i++) {
            for (int j = 0; j < map1[0].length; j++) {
                // Here the 1 represents that brick is not hit by the ball
                // Once the brick is hit by the ball (intersected by the ball),
                // the value will be changed to 0
                map1[i][j] = 1;
            }
        }
        brickwidth = 540 / col;
        brickheight = 150 / row;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g; // Cast to Graphics2D

        for (int i = 0; i < map1.length; i++) {
            for (int j = 0; j < map1[0].length; j++) {
                if (map1[i][j] > 0) {
                    g2d.setColor(Color.white);
                    g2d.fillRect(j * brickwidth + 80, i * brickheight + 50, brickwidth, brickheight);
                    g2d.setStroke(new BasicStroke(3)); // Set the stroke width
                    g2d.setColor(Color.black);
                    g2d.drawRect(j * brickwidth + 80, i * brickheight + 50, brickwidth, brickheight);
                }
            }
        }
    }
    public void setBrickeValue(int value, int row, int col)
    {
    	map1[row][col]=value;
    }
}
