package core;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static void setTheme() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;

            }
        }
    }

    public static void showMessage(String str) {
        String message;
        String title;

        switch (str) {
            case "fill" -> {
                message = "Lütfen tüm alanları doldurunuz !";
                title = "Mesaj";
            }
            case "done" -> {
                message = "İşlem Başarılı!";
                title = "Sonuç";
            }
            case "notFound" -> {
                message = "Kayıt Bulunamadı !";
                title = "Bulunamadı";
            }
            case "error" -> {
                message = "Hatalı işlem yaptınız !";
                title = "Hata";
            }
            default -> {
                message = str;
                title = "Mesaj";
            }
        }

        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    public static boolean confirm(String str){
        String msg;
        if (str.equals("sure")){
            msg = "Bu işlemi yapmak istediğine emin misin ?";
        }else {
            msg = str;
        }
        return JOptionPane.showConfirmDialog(null,msg,"Emin misin ?",JOptionPane.YES_NO_OPTION) == 0;
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static boolean isFieldListEmpty(JTextField[] fieldList) {
        for (JTextField field : fieldList) {
            if (isFieldEmpty(field)) return true;
        }
        return false;
    }

    public static int getLocationPoint(String type, Dimension size) {
        return switch (type) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> 0;
        };
    }
}
