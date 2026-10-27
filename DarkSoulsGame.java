import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class DarkSoulsGame extends MIDlet {
    private Display display;
    private GameCanvasCanvas canvas;

    public DarkSoulsGame() {
        display = Display.getDisplay(this);
        canvas = new GameCanvasCanvas(this);
    }

    public void startApp() {
        display.setCurrent(canvas);
        canvas.start();
    }

    public void pauseApp() {}

    public void destroyApp(boolean unconditional) {}

    public void exit() {
        destroyApp(true);
        notifyDestroyed();
    }
}

class GameCanvasCanvas extends GameCanvas implements Runnable {
    private boolean isRunning;
    private Thread thread;
    private int x = 140, y = 100;
    private int hp = 100, stamina = 100, estus = 3;
    private int enemyX = 80, enemyY = 80, enemyHp = 40;
    private String message = "Mueve el D-pad y pulsa 5 para atacar";

    public GameCanvasCanvas(DarkSoulsGame midlet) {
        super(true);
    }

    public void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        Graphics g = getGraphics();
        while (isRunning) {
            input();
            render(g);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {}
        }
    }

    private void input() {
        int keys = getKeyStates();
        if ((keys & LEFT_PRESSED) != 0 && x > 15) x -= 4;
        if ((keys & RIGHT_PRESSED) != 0 && x < getWidth() - 15) x += 4;
        if ((keys & UP_PRESSED) != 0 && y > 25) y -= 4;
        if ((keys & DOWN_PRESSED) != 0 && y < getHeight() - 35) y += 4;
    }

    protected void keyPressed(int keyCode) {
        if (keyCode == KEY_NUM5 || getGameAction(keyCode) == FIRE) {
            if (Math.abs(x - enemyX) < 30 && Math.abs(y - enemyY) < 30) {
                enemyHp -= 20;
                if (enemyHp <= 0) {
                    message = "¡Enemigo abatido!";
                } else {
                    message = "¡Golpe al enemigo!";
                }
            } else {
                message = "¡Atacas al aire!";
            }
        } 
        else if (keyCode == KEY_NUM0) {
            if (estus > 0 && hp < 100) {
                estus--;
                hp = Math.min(100, hp + 50);
                message = "Bebiste Estus (+50 HP)";
            } else {
                message = "Sin estus o vida llena.";
            }
        } 
        else if (keyCode == KEY_POUND) {
            hp = 100;
            stamina = 100;
            estus = 3;
            enemyHp = 40;
            message = "¡Hoguera encendida! Todo restaurado.";
        }
    }

    private void render(Graphics g) {
        g.setColor(0x111111);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(0x444444);
        g.drawRect(5, 5, getWidth() - 10, getHeight() - 30);

        g.setColor(0xFF6600);
        g.fillArc(150, 100, 12, 12, 0, 360);

        if (enemyHp > 0) {
            g.setColor(0xAA0000);
            g.fillArc(enemyX - 6, enemyY - 6, 12, 12, 0, 360);
        }

        g.setColor(0x0055AA);
        g.fillRect(x - 5, y - 5, 10, 10);

        g.setColor(0x000000);
        g.fillRect(0, getHeight() - 25, getWidth(), 25);

        g.setColor(0xCC0000);
        g.fillRect(5, getHeight() - 20, hp * 50 / 100, 6);
        g.setColor(0xFFFFFF);
        g.drawRect(5, getHeight() - 20, 50, 6);

        g.setColor(0xFFCC00);
        g.drawString(message, 60, getHeight() - 22, Graphics.TOP | Graphics.LEFT);

        flushGraphics();
    }
}
