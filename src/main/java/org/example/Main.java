package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class SnakeGame {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 10;
    private static final int CELL_SIZE = 20;
    private static final int GAME_SPEED = 550;

    private List<int[]> snake;
    private int[] food;
    private int directionX, directionY;
    private boolean gameOver;
    private Timer timer;
    private Random random;


    public SnakeGame() {
        setTitle("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.BLACK);
        add(panel);

        addKeyListener((KeyListener) this);
        panel.setFocusable(true);

        snake = new ArrayList<>();
        random = new Random();
        directionX = 1;
        directionY = 0;
        gameOver = false;
    }

    private void setResizable(boolean b) {
    }

    private void setLocationRelativeTo(Object o) {
    }

    private void setSize(int i, int i1) {
    }

    private void setDefaultCloseOperation(int exitOnClose) {
    }

    private void setTitle(String snakeGame) {
    }


}
