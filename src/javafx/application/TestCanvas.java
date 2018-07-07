package javafx.application;


import java.awt.Color;
import java.awt.Graphics;
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
public class TestCanvas extends JPanel {
    private BlockGroup activeGroup;
    private Graphics graphics;
    private TestCanvas testCanvas;
    
    public TestCanvas()
    {
        activeGroup = new BlockGroup();
        activeGroup.addElement(10, 10);
        activeGroup.addElement(20, 10);
        activeGroup.addElement(20, 20);
        activeGroup.addElement(30, 10);
        testCanvas = this;
        Timer timer = new Timer();
        UpdateTask timerTask = new UpdateTask().setReference(activeGroup);
        timer.schedule(timerTask, 200, 200);    
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
            if(g != null)
                g.fillRect(x, y, width, height);
        }
        
        public void move(int step)
        {
            this.y = this.y + step;
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
                group.get(i).move(defaultStep);
            }

            testCanvas.repaint();
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        //System.out.println(width + " " + height);
        graphics = g;
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.black);
        activeGroup.drawGroup(g);
        //g.setColor(Color.BLACK);
        //g.fillRect(50, 50, 10, 10);
      }

}
