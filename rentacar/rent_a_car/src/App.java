import Business.UserManager;
import View.AdminView;
import View.LoginGUI;
import core.Helper;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        Helper.setTheme();

      //LoginGUI loginGUI = new LoginGUI();
        UserManager userManager = new UserManager();
        AdminView adminView = new AdminView(userManager.findByLogin("admin","1234"));


    }
}
