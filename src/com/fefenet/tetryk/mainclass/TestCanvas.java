package com.fefenet.tetryk.mainclass;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mati
 */
public class TestCanvas extends JPanel implements KeyListener{
    private final BlockGroup activeGroup;
    private Graphics graphics;
    private final TestCanvas testCanvas;
    private final FieldGroup fields;
    private boolean keyLock = false;
    public TestCanvas()
    {
        activeGroup = new BlockGroup();
        activeGroup.addElement(10, 10);
        activeGroup.addElement(20, 10);
        activeGroup.addElement(20, 20);
        activeGroup.addElement(30, 10);
        testCanvas = this;
        fields = new FieldGroup();
        fields.initializeWall();
        Timer timer = new Timer();
        UpdateTask timerTask = new UpdateTask().setReference(activeGroup);
        timer.schedule(timerTask, 200, 500);    
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 37 && keyLock == false)
            activeGroup.moveGroup(-1);
        else if(e.getKeyCode() == 39 && keyLock == false)
            activeGroup.moveGroup(1);
        repaint();
        keyLock = true; //blokada, tymczasowo zdjęta
        //37 = lewo
        //39 = prawo
        //System.out.println(e.getKeyChar() + " " + e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyLock = false;
    }
        
    public class UpdateTask extends TimerTask{
        private BlockGroup groupReference;
        
        public UpdateTask setReference(BlockGroup ref)
        {
            groupReference = ref;
            return this;
        }
        
        @Override
        public void run() {
            groupReference.updateBlocks();
            groupReference.drawGroup(graphics);
        } 
       
    }
    
    public class Block{
        int x;
        int y;
        int width = 10;
        int height = 10;
        
        public void draw(Graphics g)
        {
            g.setColor(Color.black);
            if(g != null)
                g.fillRect(x, y, width, height);
        }
        
        public void moveDown(int step)
        {
            this.y = this.y + step;
        }
        
        public void moveLeft(int step)
        {
            this.x = this.x - step;
        }
        
        public void moveRight(int step)
        {
            this.x = this.x + step;
        }
    }
    
    
    public class FieldGroup{
        private final List<Field> fieldList;
        
        public void drawWalls()
        {
            for(int i = 0; i < fieldList.size(); i++)
            {
                graphics.setColor(Color.red);
                graphics.drawRect(fieldList.get(i).x*10, fieldList.get(i).y*10, 10, 10);
            }
            repaint();
        }
        
        public void initializeWall()
        {
            for(int i = 0; i < 20; i++)
            {
                fieldList.add(new Field(0,i).setIsWall(true));
                fieldList.add(new Field(11,i).setIsWall(true));
            }
        }
        
        public FieldGroup()
        {
            fieldList = new ArrayList<>();
        }
        
        public class Field{
            protected boolean isFilled = false;
            protected boolean isWall = false;
            protected int x;
            protected int y;
            
            public Field(int x, int y)
            {
                this.x = x;
                this.y = y;
            }
            
            public Field setIsWall(boolean wall)
            {
                isWall = wall;
                return this;
            }
        }
        
        public Field lookForField(int x, int y)
        {
            Field elem;
            for(int i = 0; i < fieldList.size(); i++)
            {
                elem = fieldList.get(i);
                if(elem.x == x && elem.y == y)
                {
                    return elem;
                }
            }
            return null;
        }
    }
    
    public class BlockGroup{
        List<Block> group;
        int defaultWidth = 10;
        int defaultHeight = 10;
        int defaultStep = 10;
        
        public BlockGroup()
        {
            group = new ArrayList<>();
        }
        
        public void addElement(int x, int y)
        {
            Block block = new Block();
            block.x = x;
            block.y = y;
            block.width = defaultWidth;
            block.height = defaultHeight;
            group.add(block);
        }
        
        public void drawGroup(Graphics g)
        {
            for(int i = 0; i < group.size(); i++)
            {
                group.get(i).draw(g);
            }
        }
        
        public void updateBlocks()
        {
            for(int i = 0; i < group.size(); i++)
            {
                group.get(i).moveDown(defaultStep);
            }

            testCanvas.repaint();
        }
        
        public void moveGroup(int direction) // -1 = left, 1 = right
        {
            for(int i = 0; i < group.size(); i++)
            {
                if(direction == -1)
                {
                    group.get(i).moveLeft(defaultStep);
                }
                else if(direction == 1)
                {
                    group.get(i).moveRight(defaultStep);
                }
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        graphics = g;
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.black);
        fields.drawWalls();
        activeGroup.drawGroup(g);
        
      }

}
