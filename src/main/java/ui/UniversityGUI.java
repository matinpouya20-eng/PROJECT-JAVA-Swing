package ui;

import bussiness.OfferingCourseBL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UniversityGUI extends JFrame {
    private final OfferingCourseBL businessLogic;

    public UniversityGUI(OfferingCourseBL businessLogic) {
        this.businessLogic = businessLogic;
        setTitle("سیستم مدیریت دانشگاه");
        setSize(760, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        root.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        final JComboBox<String> genderBox =
                new JComboBox<>(
                        new String[]{"Male", "Female", "Unknown"}
                );

        JLabel heading = new JLabel("سیستم مدیریت دانشگاه", SwingConstants.CENTER);
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 24f));
        root.add(heading, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new GridLayout(0, 2, 10, 10));
        buttons.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        addButton(buttons, "ثبت دانشجو", this::addStudent);
        addButton(buttons, "ثبت استاد", this::addTeacher);
        addButton(buttons, "ثبت درس", this::addCourse);
        addButton(buttons, "ثبت کلاس", this::addClassRoom);
        addButton(buttons, "ارائه درس توسط استاد", this::createOffering);
        addButton(buttons, "ثبت‌نام دانشجو در درس", this::enrollStudent);
        addButton(buttons, "ثبت نمره", this::recordGrade);
        addButton(buttons, "نمایش کارنامه", this::showTranscript);
        addButton(buttons, "ذخیره اطلاعات", this::saveData);
        addButton(buttons, "خروج", this::exitProgram);
        root.add(buttons, BorderLayout.CENTER);

        JLabel footer = new JLabel("اطلاعات به‌صورت محلی در فایل ذخیره می‌شود.", SwingConstants.CENTER);
        root.add(footer, BorderLayout.SOUTH);
        setContentPane(root);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                exitProgram();
            }
        });
    }

    private void addButton(JPanel panel, String title, Runnable action) {
        JButton button = new JButton(title);
        button.setFocusPainted(false);
        button.addActionListener(event -> runSafely(action));
        panel.add(button);
    }

    private String[] askFields(String title, String... labels) {
        JPanel form = new JPanel(new GridLayout(labels.length, 2, 8, 8));
        form.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        JTextField[] fields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            form.add(new JLabel(labels[i] + ":"));
            fields[i] = new JTextField(18);
            form.add(fields[i]);
        }

        int result = JOptionPane.showConfirmDialog(
                this, form, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return null;

        String[] values = new String[fields.length];
        for (int i = 0; i < fields.length; i++) values[i] = fields[i].getText().trim();
        return values;
    }

    private void addStudent() {

        String[] values = askFields(
                "ثبت دانشجو",
                "نام دانشجو",
                "کد ملی",
                "شماره دانشجویی"
        );

        if (values == null) return;

        String[] genders = {"مرد", "زن", "نامشخص"};

        String gender = (String) JOptionPane.showInputDialog(
                this,
                "جنسیت دانشجو را انتخاب کنید:",
                "جنسیت",
                JOptionPane.QUESTION_MESSAGE,
                null,
                genders,
                genders[0]
        );

        if (gender == null) return;

        try {
            businessLogic.addStudent(
                    values[0],
                    values[1],
                    values[2],
                    gender
            );

            showSuccess("دانشجو با موفقیت ثبت شد.");

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "خطا",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    private void addTeacher() {
        String[] values = askFields("ثبت استاد", "نام استاد", "کد ملی", "شماره کارمندی");
        if (values == null) return;
        businessLogic.addTeacher(values[0], values[1], values[2]);
        showSuccess("استاد با موفقیت ثبت شد.");
    }

    private void addCourse() {
        String[] values = askFields("ثبت درس", "کد درس", "نام درس", "تعداد واحد");
        if (values == null) return;
        int units = parseInt(values[2], "تعداد واحد");
        businessLogic.addCourse(values[0], values[1], units);
        showSuccess("درس با موفقیت ثبت شد.");
    }

    private void addClassRoom() {
        String[] values = askFields("ثبت کلاس", "کد کلاس", "نام کلاس", "شماره اتاق", "ظرفیت");
        if (values == null) return;
        int capacity = parseInt(values[3], "ظرفیت");
        businessLogic.addClassRoom(values[0], values[1], values[2], capacity);
        showSuccess("کلاس با موفقیت ثبت شد.");
    }

    private void createOffering() {
        String[] values = askFields("ارائه درس توسط استاد",
                "کد ارائه", "کد درس (ابتدا درس را ثبت کن)",
                "شماره کارمندی استاد (ابتدا استاد را ثبت کن)", "ترم");
        if (values == null) return;
        businessLogic.createOffering(values[0], values[1], values[2], values[3]);
        showSuccess("ارائه درس با موفقیت ثبت شد.");
    }

    private void enrollStudent() {
        String[] values = askFields("ثبت‌نام دانشجو در درس", "شماره دانشجویی", "کد ارائه");
        if (values == null) return;
        businessLogic.enrollStudent(values[0], values[1]);
        showSuccess("ثبت‌نام با موفقیت انجام شد.");
    }

    private void recordGrade() {
        String[] values = askFields("ثبت نمره", "شماره دانشجویی", "کد ارائه", "نمره از صفر تا بیست");
        if (values == null) return;
        double grade = parseDouble(values[2], "نمره");
        businessLogic.recordGrade(values[0], values[1], grade);
        showSuccess("نمره با موفقیت ثبت شد.");
    }

    private void showTranscript() {
        String[] values = askFields("نمایش کارنامه", "شماره دانشجویی");
        if (values == null) return;
        String transcript = businessLogic.getTranscript(values[0]);
        JTextArea textArea = new JTextArea(transcript, 18, 42);
        textArea.setEditable(false);
        textArea.setCaretPosition(0);
        textArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        JOptionPane.showMessageDialog(this, new JScrollPane(textArea),
                "کارنامه دانشجو", JOptionPane.INFORMATION_MESSAGE);
    }

    private void saveData() {
        businessLogic.saveData();
        showSuccess("اطلاعات ذخیره شد.");
    }

    private void exitProgram() {
        int choice = JOptionPane.showConfirmDialog(this,
                "قبل از خروج، اطلاعات ذخیره شود؟", "خروج از سامانه",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION) return;
        if (choice == JOptionPane.YES_OPTION) {
            try {
                businessLogic.saveData();
            } catch (RuntimeException exception) {
                showError(exception.getMessage());
                return; // اگر ذخیره نشد، پنجره را نبند تا کاربر بتواند دوباره تلاش کند.
            }
        }
        dispose();
    }

    private int parseInt(String value, String label) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("«" + label + "» باید عدد صحیح باشد.");
        }
    }

    private double parseDouble(String value, String label) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("«" + label + "» باید عدد باشد.");
        }
    }

    private void runSafely(Runnable action) {
        try {
            action.run();
        } catch (RuntimeException exception) {
            showError(exception.getMessage() == null ? "خطای نامشخص رخ داد." : exception.getMessage());
        }
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "انجام شد", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "خطا", JOptionPane.ERROR_MESSAGE);
    }
}
