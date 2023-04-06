package org.example;

import javax.swing.*;
import java.io.IOException;

public class Menu extends JFrame {
    int opcionMenu = 1;
    String[] botones = {"1.- Ver gatos", "2.- Salir"};
    public Menu() throws IOException {
        do{
            String opcion = (String) JOptionPane.showInputDialog(null,"Gatitos as java", "Menu", JOptionPane.INFORMATION_MESSAGE,
                        null,botones,botones[0]);

            //validacion de la opcion que escoge el usuario
            for (int i = 0; i<botones.length; i++){
                if (opcion == botones[i]){
                    opcionMenu = i;
                }
            }
            switch (opcionMenu){
                case 0:
                    GatoService.verGatos();
                    break;
                default:
                    break;
            }
        }while(opcionMenu != 1);

    }
}
