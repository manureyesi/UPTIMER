/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mundo.manu.uptimer.pantallas;

import java.awt.Panel;
import javax.swing.JFrame;

public class UtilPantallas extends JFrame {

    public UtilPantallas () {
        
        super("Ver UPTIMER");
        
        super.setSize(600, 400);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
        super.add(new Panel());
        super.setBounds(100, 100, 600, 400);
        
    }
        
}
