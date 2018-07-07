package com.fefenet.tetryk.mainclass;


import com.fefenet.tetryk.mainclass.TestCanvas.FieldGroup.Field;
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
        activeGroup.addElement(1, 1);
        activeGroup.addElement(2, 1);
        activeGroup.addElement(2, 2);
        activeGroup.addElement(3, 1);
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
        keyLock = false; //blokada, tymczasowo zdjęta
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
                g.fillRect(x*10, y*10, width, height);
        }
        
        public boolean checkIfMovePossible(int where)
        {
            Field pole = fields.lookForField(this.x+where, this.y);
            if(pole != null)
            {
                if(pole.isWall == false)
                    return true;
            }
            return false;
        }
        
        public void moveDown()
        {
            this.y++;
        }
        
        public void moveLeft()
        {
            this.x--;
        }
        
        public void moveRight()
        {
            this.x++;
        }
    }
    
    
    public class FieldGroup{
        private final List<Field> fieldList;
        
        public void drawWalls()
        {
            for(int i = 0; i < fieldList.size(); i++)
            {
                if(fieldList.get(i).isWall)
                    graphics.setColor(Color.red);
                else
                    graphics.setColor(Color.CYAN);
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
            for(int i = 1; i < 11; i++)
            {
                for(int j = 0; j < 20; j++)
                    fieldList.add(new Field(i,j).setIsWall(false).setIsFilled(false));
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
            
            public Field setIsFilled(boolean filled)
            {
                isFilled = filled;
                isWall = false;
                return this;
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
                group.get(i).moveDown();
            }

            testCanvas.repaint();
        }
        
        public void moveGroup(int direction) // -1 = left, 1 = right
        {
            boolean isMovePossible = true;
            for(int i = 0; i < group.size(); i++)
            {
                isMovePossible = isMovePossible && group.get(i).checkIfMovePossible(direction);
            }
            for(int i = 0 ; i < group.size(); i++)
            {
                if(isMovePossible)
                {
                    if(direction == -1)
                    {
                        group.get(i).moveLeft();
                    }
                    else if(direction == 1)
                    {
                        group.get(i).moveRight();
                    }
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
