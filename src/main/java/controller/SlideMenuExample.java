/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controller;

/**
 *
 * @author José Quiroz
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.utils.SlideMotion;

public class SlideMenuExample extends JFrame {
    private JPanel menuPanel;
    private JButton toggleButton;
    private SlideMotion slideMotion = new SlideMotion();
    private boolean menuVisible = false;

    public SlideMenuExample() {
        setTitle("Slide Menu Example");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Configuración del panel del menú
        menuPanel = new JPanel();
        menuPanel.setBackground(Color.LIGHT_GRAY);
        menuPanel.setBounds(-200, 0, 200, 400); // Posición inicial fuera de la ventana
        add(menuPanel);

        // Botón para alternar el menú
        toggleButton = new JButton("Toggle Menu");
        toggleButton.setBounds(150, 20, 120, 30);
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (menuVisible) {
                    slideMotion.slideOutPanel(menuPanel, -200, 5); // Deslizar hacia afuera
                } else {
                    slideMotion.slideInPanel(menuPanel, 0, 5); // Deslizar hacia adentro
                }
                menuVisible = !menuVisible;
            }
        });
        add(toggleButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SlideMenuExample example = new SlideMenuExample();
            example.setVisible(true);
        });
    }
}
