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

public class SnakeGame extends JFrame implements ActionListener, KeyListener {
    private static final int WIDTH = 40;
    private static final int HEIGHT = 20;
    private static final int CELL_SIZE = 20;
    private static final int GAME_SPEED = 100; // скорость игры

    private List<int[]> snake;
    private int[] food;
    private int directionX, directionY;
    private boolean gameOver;
    private Timer timer;
    private Random random;

    public SnakeGame() {
        // Настройка окна
        setTitle("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Создание панели для отображения игрового поля
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.BLACK);
        add(panel);

        // Настройка обработки событий клавиатуры
        addKeyListener(this);
        setFocusable(true);

        // Инициализация переменных
        snake = new ArrayList<>();
        random = new Random();
        directionX = 1;
        directionY = 0;
        gameOver = false;

        // Инициализация начального состояния игры
        initialize();

        // Создание таймера для автоматического движения змейки
        timer = new Timer(GAME_SPEED, this);
        timer.start();
    }

    // Инициализация начального состояния игры
    private void initialize() {
        // Начальные координаты головы змейки
        int initialX = WIDTH / 2;
        int initialY = HEIGHT / 2;
        snake.add(new int[]{initialY, initialX});

        // Инициализация координат для новой еды
        food = new int[]{0, 0};
        spawnFood();
    }

    // Размещение еды на поле
    private void spawnFood() {
        int x, y;
        do {
            // Генерация случайных координат для еды
            x = random.nextInt(WIDTH);
            y = random.nextInt(HEIGHT);
        } while (isCellOccupied(x, y) || isFoodOccupied(x, y));

        // Инициализация координат для новой еды
        food[0] = y;
        food[1] = x;
    }

    // Проверка, занята ли ячейка змейкой
    private boolean isCellOccupied(int x, int y) {
        for (int[] segment : snake) {
            if (segment[0] == x && segment[1] == y) {
                return true;
            }
        }
        return false;
    }

    // Проверка, занята ли ячейка едой
    private boolean isFoodOccupied(int x, int y) {
        return (food[0] == x && food[1] == y);
    }

    // Движение змейки
    private void moveSnake() {
        // Получение координат головы змейки
        int[] head = snake.get(0);
        // Вычисление новых координат головы в зависимости от направления
        int[] newHead = new int[]{head[0] + directionY, head[1] + directionX};

        // Проверка на столкновение со стеной или самой собой
        if (newHead[0] < 0 || newHead[0] >= HEIGHT || newHead[1] < 0 || newHead[1] >= WIDTH || isCellOccupied(newHead[1], newHead[0])) {
            gameOver = true;
            timer.stop();
            return;
        }

        // Добавление новой головы змейки
        snake.add(0, newHead);

        // Проверка на поедание еды
        if (newHead[0] == food[0] && newHead[1] == food[1]) {
            // Если голова змейки совпадает с координатами еды, размещаем новую еду
            spawnFood();
        } else {
            // Если еду не съели, удаляем последний сегмент змейки
            snake.remove(snake.size() - 1);
        }
    }

    // Обработка события таймера (автоматическое движение змейки)
    @Override
    public void actionPerformed(ActionEvent e) {
        // Если игра не завершена, вызываем метод движения змейки и перерисовываем экран
        if         (!gameOver) {
            moveSnake();
            repaint();
        }
    }

    // Отрисовка элементов игры
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Отрисовка еды красным цветом
        g.setColor(Color.RED);
        // Уменьшаем размер еды
        g.fillRect(food[1] * CELL_SIZE, food[0] * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        // Отрисовка змейки зеленым цветом
        g.setColor(Color.GREEN);
        for (int[] segment : snake) {
            g.fillRect(segment[1] * CELL_SIZE, segment[0] * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        if (gameOver) {
            // Вывод сообщения о завершении игры
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over!", WIDTH * CELL_SIZE / 4, HEIGHT * CELL_SIZE / 2);
        }
    }

    // Обработка события нажатия клавиши
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP && directionY != 1) {
            // Изменение направления движения вверх
            directionY = -1;
            directionX = 0;
        } else if (key == KeyEvent.VK_DOWN && directionY != -1) {
            // Изменение направления движения вниз
            directionY = 1;
            directionX = 0;
        } else if (key == KeyEvent.VK_LEFT && directionX != 1) {
            // Изменение направления движения влево
            directionY = 0;
            directionX = -1;
        } else if (key == KeyEvent.VK_RIGHT && directionX != -1) {
            // Изменение направления движения вправо
            directionY = 0;
            directionX = 1;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Не используется, но должен быть реализован из-за интерфейса KeyListener
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Не используется, но должен быть реализован из-за интерфейса KeyListener
    }

    // Метод main - входная точка приложения
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SnakeGame game = new SnakeGame();
            game.setVisible(true); // Сделаем окно видимым
        });
    }
}

