package is412.concurrencia;

import javax.swing.*;
import java.awt.*;

public class ButtonStatus extends JButton {
    public ButtonStatus(String caption) {
        super(caption);
        iniciar();
    }

    public void iniciar() {
        this.setBackground(Color.white);
    }

    public void entrar() {
        this.setBackground(Color.green);
    }

    public void salir() {
        this.setBackground(Color.red);
    }

    public void esperar() {
        this.setBackground(Color.orange);
    }

}
