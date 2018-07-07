/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fefenet.tetryk.mainclass;

import com.fefenet.tetryk.gui.AppMainFrame;
import javafx.application.TestCanvas;
import javax.swing.JFrame;

/**
 *
 * @author mati
 */
public class Tetryk extends TestCanvas{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame frame = new JFrame("Gra");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TestCanvas kanwa = new TestCanvas();
        frame.add(kanwa);
        System.out.println("Test");
        frame.setSize(800, 500);
        frame.setVisible(true);
        frame.addKeyListener(kanwa);
    }
    
}
