package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import model.User;
import service.FriefindSystem;

public class SearchUserFrame extends JFrame {

    private JPanel contentPane;
    private JComboBox<Integer> idCMB;
    private JTextArea dispTA;

    private MainFrame bf; // back frame reference

    public SearchUserFrame(MainFrame bf) {
        this.bf = bf;

        setTitle("Search User");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(120, 120, 430, 360);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(24, 15, 48, 14);
        contentPane.add(idLabel);

        idCMB = new JComboBox<Integer>();
        idCMB.setBounds(82, 11, 130, 22);
        contentPane.add(idCMB);

        JButton showBtn = new JButton("SHOW");
        showBtn.setBounds(240, 11, 120, 22);
        contentPane.add(showBtn);

        JScrollPane sp = new JScrollPane();
        sp.setBounds(25, 52, 367, 220);
        contentPane.add(sp);

        dispTA = new JTextArea();
        dispTA.setEditable(false);
        sp.setViewportView(dispTA);

        JButton closeBtn = new JButton("CLOSE");
        closeBtn.setBounds(167, 286, 89, 23);
        contentPane.add(closeBtn);

        JButton sendToMainBtn = new JButton("SEND TO MAIN");
        sendToMainBtn.setBounds(25, 286, 130, 23);
        contentPane.add(sendToMainBtn);

        // Actions (teacher-style)
        closeBtn.addActionListener(e -> dispose());
        showBtn.addActionListener(this::showSelected);
        sendToMainBtn.addActionListener(this::sendSelectedToMain);

        loadIdsToCombo();
    }

    private void loadIdsToCombo() {
        dispTA.setText("");
        idCMB.removeAllItems();

        try {
            ArrayList<User> users = FriefindSystem.getUsers(); // static
            for (User u : users) {
                idCMB.addItem(u.getId());
            }

            if (idCMB.getItemCount() == 0) {
                dispTA.setText("No users loaded.\nTip: Make sure FriefindSystem.readTxt() is called at startup.");
            }

        } catch (Exception ex) {
            dispTA.setText("Cannot load IDs: " + ex.getMessage());
        }
    }

    private void showSelected(ActionEvent e) {
        dispTA.setText("");

        Integer id = (Integer) idCMB.getSelectedItem();
        if (id == null) {
            dispTA.setText("No user selected.");
            return;
        }

        try {
            User found = findUserById(id);

            if (found == null) {
                dispTA.setText("User not found.");
            } else {
                dispTA.setText(found.toString());
            }

        } catch (Exception ex) {
            dispTA.setText("Error: " + ex.getMessage());
        }
    }

    private void sendSelectedToMain(ActionEvent e) {
        dispTA.setText("");

        Integer id = (Integer) idCMB.getSelectedItem();
        if (id == null) {
            dispTA.setText("No user selected.");
            return;
        }

        try {
            User found = findUserById(id);

            if (found == null) {
                dispTA.setText("User not found.");
                return;
            }

            bf.fillUserFields(found);
            dispTA.setText("Sent to MainFrame:\n" + found.toString());

        } catch (Exception ex) {
            dispTA.setText("Error: " + ex.getMessage());
        }
    }

    // Helper to avoid repeating code
    private User findUserById(int id) {
        for (User u : FriefindSystem.getUsers()) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }
}
