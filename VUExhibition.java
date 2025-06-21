package com.mycompany.vuexhibition;

import javax.swing.SwingUtilities;

public class VUExhibition {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegistrationFrame frame = new RegistrationFrame();
            frame.setVisible(true);
        });
    }
}

   