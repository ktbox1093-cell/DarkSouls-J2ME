import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.Display;

public class DarkSoulsGame extends MIDlet {

    // CONSTRUCTOR OBLIGATORIO VACÍO
    public DarkSoulsGame() {
        super();
    }

    public void startApp() {
        // Tu código de inicio aquí
    }

    public void pauseApp() {}

    public void destroyApp(boolean unconditional) {
        notifyDestroyed();
    }
}
