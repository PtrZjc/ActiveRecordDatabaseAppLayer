package pl.zajacp;

import pl.zajacp.application.AdminPanel;
import pl.zajacp.application.Helper;
import pl.zajacp.application.UserPanel;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Helper.sc = new Scanner(System.in);
        try {
            if (args[0].equals("admin")) {
                AdminPanel.welcome();
            } else {
                UserPanel.welcome();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            UserPanel.welcome();
        } finally {
            Helper.sc.close();
        }
    }
}
