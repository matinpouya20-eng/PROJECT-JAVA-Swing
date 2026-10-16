import bussiness.OfferingCourseBL;
import ui.UniversityGUI;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try {
                OfferingCourseBL businessLogic = new OfferingCourseBL();
                UniversityGUI window = new UniversityGUI(businessLogic);
                window.setVisible(true);
            } catch (RuntimeException exception) {
                JOptionPane.showMessageDialog(
                        null,
                        "برنامه نتوانست اطلاعات قبلی را بارگذاری کند.\n"
                                + exception.getMessage(),
                        "خطا در شروع برنامه",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
