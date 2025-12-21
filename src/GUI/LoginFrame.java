package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Scanner;

public class LoginFrame extends JFrame {

    private JPanel contentPane;
    private JTextField userTF;
    private JPasswordField passPF;
    private JLabel errLbl;

    private final File userFile = new File("users.txt");

    public LoginFrame() {

        setTitle("Friefind - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 420, 280);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(199, 229, 252));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel title = new JLabel("Login");
        title.setFont(new Font("Tahoma", Font.BOLD, 18));
        title.setBounds(170, 10, 100, 25);
        contentPane.add(title);

        JLabel userLbl = new JLabel("Username:");
        userLbl.setBounds(40, 60, 100, 20);
        contentPane.add(userLbl);

        userTF = new JTextField();
        userTF.setBounds(150, 60, 200, 25);
        contentPane.add(userTF);

        JLabel passLbl = new JLabel("Password:");
        passLbl.setBounds(40, 100, 100, 20);
        contentPane.add(passLbl);

        passPF = new JPasswordField();
        passPF.setBounds(150, 100, 200, 25);
        contentPane.add(passPF);

        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(40, 150, 145, 30);
        contentPane.add(loginBtn);

        JButton signupBtn = new JButton("SIGN UP");
        signupBtn.setBounds(205, 150, 145, 30);
        contentPane.add(signupBtn);

        errLbl = new JLabel("");
        errLbl.setForeground(Color.RED);
        errLbl.setBounds(40, 195, 330, 20);
        contentPane.add(errLbl);

        ensureUserFileExists();

        loginBtn.addActionListener(this::handleLogin);

        // Key event: Enter on password triggers login
        passPF.addActionListener(e -> loginBtn.doClick());

        signupBtn.addActionListener(e -> {
            errLbl.setText("");
            SignupFrame sf = new SignupFrame(this);
            sf.setVisible(true);
            setVisible(false);
        });
    }

    private void handleLogin(ActionEvent e) {
        errLbl.setText("");

        String u = userTF.getText().trim();
        String p = new String(passPF.getPassword());

        if (u.isEmpty() || p.isEmpty()) {
            errLbl.setText("Please fill username & password.");
            return;
        }

        if (checkLogin(u, p)) {
            JOptionPane.showMessageDialog(this, "Login Successful!");

            // Open MainFrame (NO system object needed)
            MainFrame mf = new MainFrame();
            mf.setVisible(true);
            dispose();

        } else {
            errLbl.setText("Invalid username or password!");
        }
    }

    private void ensureUserFileExists() {
        try {
            if (!userFile.exists()) userFile.createNewFile();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Cannot create users.txt");
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) sb.append('0');
                sb.append(hex);
            }
            return sb.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private boolean checkLogin(String user, String pass) {

        if (!userFile.exists()) return false;

        try {
            Scanner sc = new Scanner(userFile);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    String fileUser = parts[0].trim();
                    String filePass = parts[1].trim();

                    if (fileUser.equals(user) && filePass.equals(pass)) {
                        sc.close();
                        return true;
                    }
                }
            }

            sc.close();

        } catch (FileNotFoundException e) {
            // file not found → login fails
        }

        return false;
    }

    // called from SignupFrame to come back
    public void showAgain() {
        userTF.setText("");
        passPF.setText("");
        errLbl.setText("");
        setVisible(true);
    }
}
