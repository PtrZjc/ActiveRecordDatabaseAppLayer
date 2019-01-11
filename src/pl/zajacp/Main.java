package pl.zajacp;

import pl.zajacp.application.AdminPanel;
import pl.zajacp.application.UserPanel;

public class Main {
    public static void main(String[] args) {
        try {
            if ("admin".equals(args[0])) {
                AdminPanel.welcome();
            } else {
                UserPanel.welcome();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            UserPanel.welcome();
        }
    }
}
