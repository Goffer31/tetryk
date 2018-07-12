package com.fefenet.tetryk.mainclass;


import com.fefenet.tetryk.mainclass.TestCanvas.FieldGroup.Field;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    private BlockGroup activeGroup;
    private Graphics graphics;
    private final TestCanvas testCanvas;
    private final FieldGroup fields;
    private boolean keyLock = false;
    boolean isPaused = false;
    Timer timer;
    UpdateTask timerTask;
    
    public interface Klocek{
        public void rotate();
        public void generate();
    }
    
    public class Kluska extends BlockGroup{

        public Kluska()
        {
            super();
        }
        
        @Override
        public void rotate() {
        }

        @Override
        public void generate() {
            addElement(1+5,2);
            addElement(2+5,2);
            addElement(1+5,3);
            addElement(2+5,3);
        }
        
    }
    
    public class Kijek extends BlockGroup{
        
        public Kijek()
        {
            super();
        }
        
        @Override
        public void rotate()
        {
            if(rotationStage == 1 || rotationStage == 3)
            {
                Block elem = super.group.get(0);
                if(activeGroup.checkIfMovesPossible(new int[]{-2,-1,0,1}, new int[]{3,2,1,0}))
                {
                    elem.x = elem.x - 2;
                    elem.y = elem.y + 3;
                    elem = super.group.get(1);
                    elem.x = elem.x - 1;
                    elem.y = elem.y + 2;
                    elem = super.group.get(2);
                    elem.y = elem.y + 1;
                    elem = super.group.get(3);
                    elem.x = elem.x + 1;
                    rotationStage = rotationStage + 1;
                }
            }
            else if(rotationStage == 2 || rotationStage == 4)
            {
                Block elem = super.group.get(0);
                if(activeGroup.checkIfMovesPossible(new int[]{2,1,0,-1}, new int[]{-3,-2,-1,0}))
                {
                    elem.x = elem.x + 2;
                    elem.y = elem.y - 3;
                    elem = super.group.get(1);
                    elem.x = elem.x + 1;
                    elem.y = elem.y - 2;
                    elem = super.group.get(2);
                    elem.y = elem.y - 1;
                    elem = super.group.get(3);
                    elem.x = elem.x - 1;
                    rotationStage = (rotationStage%4)+1;
                }
            }
        }
        
        @Override
        public void generate() {
            rotationStage = 1;
            if(rotationStage == 1 || rotationStage == 3)
            {
                addElement(1+5,0);
                addElement(1+5,1);
                addElement(1+5,2);
                addElement(1+5,3);
            }
            else
            {
                addElement(4,3);
                addElement(5,3);
                addElement(6,3);
                addElement(7,3);
            }
        }
    }
   
    public class Elka extends BlockGroup{
        public Elka()
        {
            super();
        }
        
        @Override
        public void rotate()
        {
            if(rotationStage == 1)
            {
                Block elem = super.group.get(0);
                if(activeGroup.checkIfMovesPossible(new int[]{1,0,-1,0}, new int[]{-1,0,1,-2}))
                {
                    elem.x = elem.x + 1;
                    elem.y = elem.y - 1;
                    elem = super.group.get(1);
                    elem.x = elem.x + 0;
                    elem.y = elem.y + 0;
                    elem = super.group.get(2);
                    elem.x = elem.x - 1;
                    elem.y = elem.y + 1;
                    elem = super.group.get(3);
                    elem.x = elem.x + 0;
                    elem.y = elem.y - 2;
                    rotationStage = rotationStage + 1;
                }
            }
            else if(rotationStage == 2)
            {
                Block elem = super.group.get(0);
                if(activeGroup.checkIfMovesPossible(new int[]{1,0,-1,2}, new int[]{2,1,0,-1}))
                {
                    elem.x = elem.x + 1;
                    elem.y = elem.y + 2;
                    elem = super.group.get(1);
                    elem.x = elem.x + 0;
                    elem.y = elem.y + 1;
                    elem = super.group.get(2);
                    elem.x = elem.x - 1;
                    elem.y = elem.y + 0;
                    elem = super.group.get(3);
                    elem.x = elem.x + 2;
                    elem.y = elem.y + 1;
                    rotationStage = (rotationStage%4)+1;
                }
            }
            else if(rotationStage == 3)
            {
                Block elem = super.group.get(0);
                if(activeGroup.checkIfMovesPossible(new int[]{-1,0,1,0}, new int[]{0,-1,-2,1}))
                {
                    elem.x = elem.x - 1;
                    elem.y = elem.y + 0;
                    elem = super.group.get(1);
                    elem.x = elem.x + 0;
                    elem.y = elem.y - 1;
                    elem = super.group.get(2);
                    elem.x = elem.x + 1;
                    elem.y = elem.y - 2;
                    elem = super.group.get(3);
                    elem.x = elem.x + 0;
                    elem.y = elem.y + 1;
                    rotationStage = (rotationStage%4)+1;
                }
            }             
            else if(rotationStage == 4)
            {
                Block elem = super.group.get(0);
                if(activeGroup.checkIfMovesPossible(new int[]{-1,0,1,-2}, new int[]{-1,0,1,0}))
                {
                    elem.x = elem.x - 1;
                    elem.y = elem.y - 1;
                    elem = super.group.get(1);
                    elem.x = elem.x + 0;
                    elem.y = elem.y - 0;
                    elem = super.group.get(2);
                    elem.x = elem.x + 1;
                    elem.y = elem.y + 1;
                    elem = super.group.get(3);
                    elem.x = elem.x - 2;
                    elem.y = elem.y + 0;
                    rotationStage = (rotationStage%4)+1;
                }
            }             
        }
        
        @Override
        public void generate() {
            rotationStage = 1;
            addElement(1+5,2);
            addElement(2+5,2);
            addElement(3+5,2);
            addElement(1+5,3);
        }   
    }
   
     public class Zygzak extends BlockGroup{
         public Zygzak()
         {
         super();
         }

        @Override
        public void rotate()
        {
            if(rotationStage == 1)
            {
                Block elem = super.group.get(0);
                if(activeGroup.checkIfMovesPossible(new int[]{2,1,0,-1}, new int[]{-1,0,-1,0}))
                {
                    elem.x = elem.x + 2;
                    elem.y = elem.y - 1;
                    elem = super.group.get(1);
                    elem.x = elem.x + 1;
                    elem.y = elem.y - 0;
                    elem = super.group.get(2);
                    elem.x = elem.x + 0;
                    elem.y = elem.y - 1;
                    elem = super.group.get(3);
                    elem.x = elem.x - 1;
                    elem.y = elem.y + 0;
                    rotationStage = rotationStage + 1;
                }
            }
            else if(rotationStage == 2)
            {
                Block elem = super.group.get(0);
                if(activeGroup.checkIfMovesPossible(new int[]{-2,-1,0,1}, new int[]{1,0,1,0}))
                {
                    elem.x = elem.x - 2;
                    elem.y = elem.y + 1;
                    elem = super.group.get(1);
                    elem.x = elem.x - 1;
                    elem.y = elem.y + 0;
                    elem = super.group.get(2);
                    elem.x = elem.x - 0;
                    elem.y = elem.y + 1;
                    elem = super.group.get(3);
                    elem.x = elem.x + 1;
                    elem.y = elem.y - 0;
                    rotationStage = (rotationStage%2)+1;
                }
            }
        }
        
        @Override
        public void generate() {
            rotationStage = 1;
            addElement(1 + 5, 2);
            addElement(2 + 5, 2);
            addElement(2 + 5, 3);
            addElement(3 + 5, 3);
        }
    }
     
     
      public class Piramidka extends BlockGroup{
          public Piramidka()
          {
          super();
          }

        @Override
        public void rotate()
        {
            if(rotationStage == 1)
            {
                Block elem = super.group.get(0);
                if(activeGroup.checkIfMovesPossible(new int[]{0,1,2,0}, new int[]{1,0,-1,1}))
                {
                    elem.x = elem.x + 0;
                    elem.y = elem.y + 1;
                    elem = super.group.get(1);
                    elem.x = elem.x + 1;
                    elem.y = elem.y - 0;
                    elem = super.group.get(2);
                    elem.x = elem.x + 2;
                    elem.y = elem.y - 1;
                    elem = super.group.get(3);
                    elem.x = elem.x + 0;
                    elem.y = elem.y + 1;
                    rotationStage = rotationStage + 1;
                }
            }
            else if(rotationStage == 2)
            {
                Block elem = super.group.get(0);
                if(activeGroup.checkIfMovesPossible(new int[]{2,1,0,0}, new int[]{-1,0,1,-1}))
                {
                    elem.x = elem.x + 2;
                    elem.y = elem.y - 1;
                    elem = super.group.get(1);
                    elem.x = elem.x + 1;
                    elem.y = elem.y + 0;
                    elem = super.group.get(2);
                    elem.x = elem.x - 0;
                    elem.y = elem.y + 1;
                    elem = super.group.get(3);
                    elem.x = elem.x + 0;
                    elem.y = elem.y - 1;
                    rotationStage = (rotationStage%4)+1;
                }
            }
            else if(rotationStage == 3)
            {
                Block elem = super.group.get(0);
                if(activeGroup.checkIfMovesPossible(new int[]{-2,-1,0,0}, new int[]{2,1,0,0}))
                {
                    elem.x = elem.x - 2;
                    elem.y = elem.y + 2;
                    elem = super.group.get(1);
                    elem.x = elem.x - 1;
                    elem.y = elem.y + 1;
                    elem = super.group.get(2);
                    elem.x = elem.x + 0;
                    elem.y = elem.y - 0;
                    elem = super.group.get(3);
                    elem.x = elem.x - 0;
                    elem.y = elem.y + 0;
                    rotationStage = (rotationStage%4)+1;
                }
            }             
            else if(rotationStage == 4)
            {
                Block elem = super.group.get(0);
                if(activeGroup.checkIfMovesPossible(new int[]{0,-1,-2,0}, new int[]{-2,-1,0,0}))
                {
                    elem.x = elem.x - 0;
                    elem.y = elem.y - 2;
                    elem = super.group.get(1);
                    elem.x = elem.x - 1;
                    elem.y = elem.y - 1;
                    elem = super.group.get(2);
                    elem.x = elem.x - 2;
                    elem.y = elem.y + 0;
                    elem = super.group.get(3);
                    elem.x = elem.x - 0;
                    elem.y = elem.y - 0;
                    rotationStage = (rotationStage%4)+1;
                }
            }             
        }
        
        @Override
        public void generate() {
            rotationStage = 1;
            addElement(1 + 5, 1);
            addElement(1 + 5, 2);
            addElement(1 + 5, 3);
            addElement(2 + 5, 2);
        }

    }

    public class OdwroconaElka extends BlockGroup {

        public OdwroconaElka() {
            super();
        }

        @Override
        public void rotate()
        {
            if(rotationStage == 1)
            {
                Block elem = super.group.get(0);
                if(activeGroup.checkIfMovesPossible(new int[]{1,0,-1,2}, new int[]{-1,-1,0,-1}))
                {
                    elem.x = elem.x + 1;
                    elem.y = elem.y - 2;
                    elem = super.group.get(1);
                    elem.x = elem.x - 0;
                    elem.y = elem.y - 1;
                    elem = super.group.get(2);
                    elem.x = elem.x - 1;
                    elem.y = elem.y + 0;
                    elem = super.group.get(3);
                    elem.x = elem.x + 2;
                    elem.y = elem.y - 1;
                    rotationStage = rotationStage + 1;
                }
            }
            else if(rotationStage == 2)
            {
                Block elem = super.group.get(0);
                if(activeGroup.checkIfMovesPossible(new int[]{1,0,-1,0}, new int[]{1,0,-1,2}))
                {
                    elem.x = elem.x + 1;
                    elem.y = elem.y + 1;
                    elem = super.group.get(1);
                    elem.x = elem.x + 0;
                    elem.y = elem.y + 0;
                    elem = super.group.get(2);
                    elem.x = elem.x - 1;
                    elem.y = elem.y - 1;
                    elem = super.group.get(3);
                    elem.x = elem.x + 0;
                    elem.y = elem.y + 2;
                    rotationStage = (rotationStage%4)+1;
                }
            }
            else if(rotationStage == 3)
            {
                Block elem = super.group.get(0);
                if(activeGroup.checkIfMovesPossible(new int[]{0,1,2,-1}, new int[]{1,0,-1,0}))
                {
                    elem.x = elem.x - 0;
                    elem.y = elem.y + 1;
                    elem = super.group.get(1);
                    elem.x = elem.x + 1;
                    elem.y = elem.y - 0;
                    elem = super.group.get(2);
                    elem.x = elem.x + 2;
                    elem.y = elem.y - 1;
                    elem = super.group.get(3);
                    elem.x = elem.x - 1;
                    elem.y = elem.y + 0;
                    rotationStage = (rotationStage%4)+1;
                }
            }             
            else if(rotationStage == 4)
            {
                Block elem = super.group.get(0);
                if(activeGroup.checkIfMovesPossible(new int[]{-2,-2,0,-1}, new int[]{-1,1,2,-1}))
                {
                    elem.x = elem.x - 2;
                    elem.y = elem.y - 0;
                    elem = super.group.get(1);
                    elem.x = elem.x - 1;
                    elem.y = elem.y + 1;
                    elem = super.group.get(2);
                    elem.x = elem.x + 0;
                    elem.y = elem.y + 2;
                    elem = super.group.get(3);
                    elem.x = elem.x - 1;
                    elem.y = elem.y - 1;
                    rotationStage = (rotationStage%4)+1;
                }
            }             
        }
        
        @Override
        public void generate() {
            rotationStage = 1;
            addElement(1 + 5, 3);
            addElement(2 + 5, 3);
            addElement(3 + 5, 3);
            addElement(1 + 5, 2);
        }
    }

    /*
   
    
    public class RotatorLibrary{
        kluska, kijek, elka, odwrocona_elka, piramidka, zygzak
    }*/

    public void generateNewBlockGroup()
    {
        Random losowanieKlocka = new Random();
        int losowa = losowanieKlocka.nextInt(6);
        switch (losowa) {
            case 0:
                activeGroup = new Kluska();
                break;
            case 1:
                activeGroup = new Kijek();
                break;
            case 2:
                activeGroup = new Elka();
                break;
            case 3:
                activeGroup = new Zygzak();
                break;
            case 4:
                activeGroup = new Piramidka();
                break;
            case 5:
                activeGroup = new OdwroconaElka();
                break;
            default:
                break;
        }
    }

    
    public TestCanvas()
    {
        generateNewBlockGroup();
        activeGroup.generate();
        testCanvas = this;
        fields = new FieldGroup();
        fields.initializeWall();
        timer = new Timer();
        timerTask = new UpdateTask().setReference(activeGroup);
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
        else if(e.getKeyCode() == 40 && keyLock == false)
        {
            boolean lockLoop;
            do{
                lockLoop = activeGroup.updateBlocks();
            } while(lockLoop);
        }
        else if(e.getKeyCode() == 38 && keyLock == false)
        {
            activeGroup.rotate();
        }
        if(e.getKeyCode() == 80 && keyLock == false) 
        {
        if(isPaused == false ){timer.cancel();isPaused=true;}
        else if(isPaused == true){timer= new Timer(); timerTask = new UpdateTask().setReference(activeGroup); timer.schedule(timerTask, 200, 500); isPaused = false;}
        }
        repaint();
        keyLock = true; //blokada, tymczasowo zdjęta
        //37 = lewo
        //39 = prawo
        //40 = dół
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
            if(!groupReference.updateBlocks())
            {
                activeGroup.fillFields();
                generateNewBlockGroup();
                activeGroup.group.clear();
                activeGroup.generate();
            }
            groupReference = activeGroup;
            fields.checkBottom();
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
        
        
        public boolean checkIfMovePossible(int whereX, int whereY)
        {
            Field pole = fields.lookForField(this.x+whereX, this.y+whereY);
            if(pole != null)
            {
                if(pole.isWall == false && pole.isFilled == false)
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
                {
                    graphics.setColor(Color.red);
                    graphics.drawRect(fieldList.get(i).x*10, fieldList.get(i).y*10, 10, 10);
                }
                else if(fieldList.get(i).isFilled)
                {
                    graphics.setColor(Color.cyan);
                    graphics.fillRect(fieldList.get(i).x*10, fieldList.get(i).y*10, 10, 10);                    
                }
                else
                {
                    graphics.setColor(Color.CYAN);
                    graphics.drawRect(fieldList.get(i).x*10, fieldList.get(i).y*10, 10, 10);                    
                }
            }
            repaint();
        }
        
        public void initializeWall()
        {
            for(int i = 0; i < 21; i++)
            {
                fieldList.add(new Field(0,i).setIsWall(true));
                fieldList.add(new Field(11,i).setIsWall(true));
            }
            for(int i = 1; i < 11; i++)
            {
                fieldList.add(new Field(i,20).setIsWall(true));
                for(int j = 0; j < 20; j++)
                    fieldList.add(new Field(i,j).setIsWall(false).setIsFilled(false));
            }
        }
        
        public FieldGroup()
        {
            fieldList = new ArrayList<>();
        }
        
        public void checkBottom()
        {
            boolean isFull;
            int index = 20;
            for(int j = 19; j > 0; j--)
            {
                isFull = true;
                for(int i = 1; i < 11; i++)
                {
                    isFull = isFull && fields.lookForField(i, j).isFilled;
                }
                if(isFull)
                {
                    index = j;
                    break;
                }
            }
            if(index < 20)
            {
                for(int i = 1; i < 11; i++)
                {
                    for(int j = index; j > 0; j--)
                    {
                        Field to = fields.lookForField(i, j);
                        Field origin = fields.lookForField(i,j-1);
                        to.isFilled = origin.isFilled;
                    }
                }
            }
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
    
    public class BlockGroup implements Klocek{
        protected List<Block> group;
        protected int defaultWidth = 10;
        protected int defaultHeight = 10;
        protected int defaultStep = 10;
        protected int rotationStage;
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
        
        public boolean checkIfMovesPossible(int[] x, int[] y)
        {
            boolean toReturn = true;
            if(x.length != y.length || x.length != activeGroup.group.size())
                return false;
            for(int i = 0; i < x.length; i++)
            {
                Field pole = fields.lookForField(group.get(i).x+x[i], group.get(i).y+y[i]);
                if(pole != null)
                {
                    if(pole.isWall == false && pole.isFilled == false)
                        toReturn = toReturn && true;
                    else
                        toReturn = toReturn && false;
                }                
            }
            return toReturn;
        }
        
        public boolean updateBlocks()
        {
            boolean isMovePossible = true;
            for (int i = 0; i < group.size(); i++) {
                isMovePossible = isMovePossible && group.get(i).checkIfMovePossible(0, 1);
            }
            if(isMovePossible) 
                for (int i = 0; i < group.size(); i++) {
                    group.get(i).moveDown();
                }
            testCanvas.repaint();
            return isMovePossible;
        }
        
        public void moveGroup(int direction) // -1 = left, 1 = right
        {
            boolean isMovePossible = true;
            for(int i = 0; i < group.size(); i++)
            {
                isMovePossible = isMovePossible && group.get(i).checkIfMovePossible(direction,0);
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

        @Override
        public void rotate() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void generate() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        public void fillFields()
        {
            for(int i = 0; i < group.size(); i++)
            {
                Block active = group.get(i);
                fields.lookForField(active.x, active.y).setIsFilled(true);
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
