/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package view.utils;

/**
 *
 * @author José Quiroz
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SlideMotion {

    // Método para deslizar un JPanel hacia adentro
    public void slideInPanel(JPanel panel, int targetX, int speed) {
        Timer timer = new Timer(5, null);
        timer.addActionListener(new ActionListener() {
            int x = panel.getX();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (x < targetX) {
                    x += speed;
                    panel.setLocation(x, panel.getY());
                } else {
                    panel.setLocation(targetX, panel.getY());
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    // Método para deslizar un JPanel hacia afuera
    public void slideOutPanel(JPanel panel, int startX, int speed) {
        Timer timer = new Timer(5, null);
        timer.addActionListener(new ActionListener() {
            int x = panel.getX();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (x > startX) {
                    x -= speed;
                    panel.setLocation(x, panel.getY());
                } else {
                    panel.setLocation(startX, panel.getY());
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    // Método para deslizar un JButton hacia adentro
    public void slideInButton(JButton button, int targetX, int speed) {
        Timer timer = new Timer(5, null);
        timer.addActionListener(new ActionListener() {
            int x = button.getX();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (x < targetX) {
                    x += speed;
                    button.setLocation(x, button.getY());
                } else {
                    button.setLocation(targetX, button.getY());
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    // Método para deslizar un JButton hacia afuera
    public void slideOutButton(JButton button, int startX, int speed) {
        Timer timer = new Timer(5, null);
        timer.addActionListener(new ActionListener() {
            int x = button.getX();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (x > startX) {
                    x -= speed;
                    button.setLocation(x, button.getY());
                } else {
                    button.setLocation(startX, button.getY());
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    // Método para deslizar un JLabel hacia adentro
    public void slideInLabel(JLabel label, int targetX, int speed) {
        Timer timer = new Timer(5, null);
        timer.addActionListener(new ActionListener() {
            int x = label.getX();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (x < targetX) {
                    x += speed;
                    label.setLocation(x, label.getY());
                } else {
                    label.setLocation(targetX, label.getY());
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    // Método para deslizar un JLabel hacia afuera
    public void slideOutLabel(JLabel label, int startX, int speed) {
        Timer timer = new Timer(5, null);
        timer.addActionListener(new ActionListener() {
            int x = label.getX();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (x > startX) {
                    x -= speed;
                    label.setLocation(x, label.getY());
                } else {
                    label.setLocation(startX, label.getY());
                    timer.stop();
                }
            }
        });
        timer.start();
    }
    
    public void slideUpLabel(JLabel label, int startY, int speed){
        Timer timer = new Timer(5,null);
        timer.addActionListener(new ActionListener(){
            int y = label.getY();
            
            @Override
            public void actionPerformed(ActionEvent e){
                if (y > startY) {
                    y -= speed;
                    label.setLocation(label.getX(), y);
                } else {
                    label.setLocation(label.getX(), startY);
                    timer.stop();
                }
            }
        });
        timer.start();
    }
    
    public void slideDownLabel(JLabel label, int startY, int speed){
        Timer timer = new Timer(5,null);
        timer.addActionListener(new ActionListener(){
            int y = label.getY();
            
            @Override
            public void actionPerformed(ActionEvent e){
                if (y < startY) {
                    y += speed;
                    label.setLocation(label.getX(), y);
                } else {
                    label.setLocation(label.getX(), startY);
                    timer.stop();
                }
            }
        });
        timer.start();
    }
}

