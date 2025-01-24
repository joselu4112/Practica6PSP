package animacion2_swing_cohete;

import java.util.Random;

public class Rocket implements Runnable {
    private String name;
    private int height; // Altura actual del cohete
    private boolean running; // Para saber si el cohete está en ejecución
    private Random random = new Random();

    public Rocket(String name) {
        this.name = name;
        this.height = 0;
        this.running = true;
    }

    @Override
    public void run() {
        while (height < 1000 && running) {
            try {
                Thread.sleep(random.nextInt(100)); // Espera aleatoria (0-100 ms)
                height += random.nextInt(10) + 1;  // Incremento aleatorio entre 1 y 10 metros
                if (height > 1000) {
                    height = 1000; // Asegura que no pase de 1000
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        running = false; // Finaliza la ejecución
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public boolean isRunning() {
        return running;
    }
}
