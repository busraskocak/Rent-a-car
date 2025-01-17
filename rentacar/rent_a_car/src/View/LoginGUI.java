package View;

import Business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends Layout {
    private JPanel container;
    private JPanel w_top;
    private JLabel lbl_welcome;
    private JLabel lbl_welcome2;
    private JPanel w_bottom;
    private JTextField fld_username;
    private JPasswordField fld_pass;
    private JButton btn_login;
    private JLabel lbl_username;
    private JLabel lbl_password;
    private final UserManager userManager;

    public LoginGUI() {
        this.userManager = new UserManager();
        this.add(container);
        this.guiInitilaze(400,400);

        btn_login.addActionListener(e -> {
            JTextField[] checkFieldList = {
                    this.fld_username,this.fld_pass};
            if (Helper.isFieldListEmpty(checkFieldList)) {
                Helper.showMessage("fill");
            }else {
                User loginUser = this.userManager.findByLogin(this.fld_username.getText(), this.fld_pass.getText());
                if (loginUser == null){
                    Helper.showMessage("notFound");
                }else {
                    AdminView adminView = new AdminView(loginUser);

                }
            }


        });
    }
}
