package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Scanner;

public class SignupFrame extends JFrame {

    private JPanel contentPane;
    private JTextField userTF;
    private JPasswordField passPF;
    private JPasswordField pass2PF;
    private JLabel errLbl;

    private File userFile = new File("users.txt");
    private LoginFrame backFrame;

    public SignupFrame(LoginFrame backFrame) {
        this.backFrame = backFrame;

        setTitle("Friefind - Signup");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setBounds(100, 100, 420, 310);
        setLocationRelativeTo(null); 

  
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                backFrame.showAgain();
            }
        });

        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel title = new JLabel("Create Account");
        title.setFont(new Font("Tahoma", Font.BOLD, 18));
        title.setBounds(130, 10, 200, 25);
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

        JLabel pass2Lbl = new JLabel("Repeat:");
        pass2Lbl.setBounds(40, 140, 100, 20);
        contentPane.add(pass2Lbl);

        pass2PF = new JPasswordField();
        pass2PF.setBounds(150, 140, 200, 25);
        contentPane.add(pass2PF);

        JButton regBtn = new JButton("REGISTER");
        regBtn.setBounds(40, 190, 145, 30);
        contentPane.add(regBtn);

        JButton backBtn = new JButton("BACK");
        backBtn.setBounds(205, 190, 145, 30);
        contentPane.add(backBtn);

        errLbl = new JLabel("");
        errLbl.setForeground(Color.RED);
        errLbl.setBounds(40, 230, 330, 20);
        contentPane.add(errLbl);

        regBtn.addActionListener(this::handleRegister);

        // Enter on repeat password triggers register
        pass2PF.addActionListener(e -> regBtn.doClick());

        backBtn.addActionListener(e -> {
            dispose();
            backFrame.showAgain();
        });

        ensureUserFileExists();
    }

    private void handleRegister(ActionEvent e) {
        errLbl.setText("");

        String u = userTF.getText().trim();
        String p1 = new String(passPF.getPassword());
        String p2 = new String(pass2PF.getPassword());

        if (u.isEmpty() || p1.isEmpty() || p2.isEmpty()) {
            errLbl.setText("Please fill all fields.");
            return;
        }

        if (!p1.equals(p2)) {
            errLbl.setText("Passwords do not match.");
            return;
        }

        if (usernameExists(u)) {
            errLbl.setText("Username already exists.");
            return;
        }

        if (saveUser(u, p1)) {
            JOptionPane.showMessageDialog(this, "Registration Successful!");
            dispose();
            backFrame.showAgain();
        } else {
            errLbl.setText("Cannot save user.");
        }
    }

    private void ensureUserFileExists() {
        if (!userFile.exists()) {
            try {
                userFile.createNewFile();
            } catch (IOException ignored) { }
        }
    }

    private boolean usernameExists(String user) {

        try {
            Scanner sc = new Scanner(userFile);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(",", 2);

                if (parts.length == 2) {
                    String fileUser = parts[0].trim();
                    if (fileUser.equals(user)) {
                        sc.close();
                        return true;
                    }
                }
            }

            sc.close();

        } catch (FileNotFoundException e) {
           
        }

        return false;
    }
    
    
    private boolean saveUser(String user, String pass) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(userFile, true));
            pw.println(user + "," + pass);
            pw.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
