import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.Display;

public class DarkSoulsGame extends MIDlet {
    // Tu juego debe heredar de MIDlet, no de GameCanvas directamente
    
    public void startApp() {
        // Aquí inicias tu canvas o pantalla del juego
    }
    
    public void pauseApp() {}
    
    public void destroyApp(boolean unconditional) {
        notifyDestroyed();
    }
}
 
