package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Activity;
import model.Location;
import model.Meeting;
import model.User;
import service.FriefindSystem;

public class MeetingFrame extends JFrame {

    private JPanel contentPane;
    private JTextField txtUser1Id, txtUser2Id, txtTime;
    private JTextField txtCity;
    private JTextField txtActType, txtPrice, txtDuration;
    private JCheckBox chkHasCoupon;
    private JTextArea displayTA;
    
    private MainFrame mainFrame;

    /**
     * Create the frame.
     */
    public MeetingFrame(MainFrame mf) {
        this.mainFrame = mf;
        
        setTitle("Meeting Frame");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 750);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblU1 = new JLabel("User 1 ID:");
        lblU1.setBounds(23, 20, 100, 14);
        contentPane.add(lblU1);

        txtUser1Id = new JTextField();
        txtUser1Id.setBounds(120, 17, 150, 20);
        contentPane.add(txtUser1Id);

        JLabel lblU2 = new JLabel("User 2 ID:");
        lblU2.setBounds(23, 50, 100, 14);
        contentPane.add(lblU2);

        txtUser2Id = new JTextField();
        txtUser2Id.setBounds(120, 47, 150, 20);
        contentPane.add(txtUser2Id);

        JLabel lblTime = new JLabel("Meeting Time:");
        lblTime.setBounds(23, 80, 100, 14);
        contentPane.add(lblTime);

        txtTime = new JTextField();
        txtTime.setBounds(120, 77, 150, 20);
        contentPane.add(txtTime);

        JLabel lblLocHeader = new JLabel("---- LOCATION DETAILS ----");
        lblLocHeader.setBounds(23, 115, 200, 14);
        contentPane.add(lblLocHeader);

        JLabel lblCity = new JLabel("City Name:");
        lblCity.setBounds(23, 140, 80, 14);
        contentPane.add(lblCity);
        
        txtCity = new JTextField();
        txtCity.setBounds(120, 137, 150, 20);
        contentPane.add(txtCity);

        JLabel lblActHeader = new JLabel("---- ACTIVITY DETAILS ----");
        lblActHeader.setBounds(23, 175, 200, 14);
        contentPane.add(lblActHeader);

        JLabel lblType = new JLabel("Type:");
        lblType.setBounds(23, 200, 80, 14);
        contentPane.add(lblType);

        txtActType = new JTextField();
        txtActType.setBounds(120, 197, 150, 20);
        contentPane.add(txtActType);

        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setBounds(23, 230, 80, 14);
        contentPane.add(lblPrice);

        txtPrice = new JTextField();
        txtPrice.setBounds(120, 227, 150, 20);
        contentPane.add(txtPrice);

        JLabel lblDur = new JLabel("Duration:");
        lblDur.setBounds(23, 260, 80, 14);
        contentPane.add(lblDur);

        txtDuration = new JTextField();
        txtDuration.setBounds(120, 257, 150, 20);
        contentPane.add(txtDuration);

        chkHasCoupon = new JCheckBox("Apply Discount Coupon");
        chkHasCoupon.setBounds(116, 285, 200, 23);
        contentPane.add(chkHasCoupon);

        JButton btnSet = new JButton("Set Meeting");
     
        btnSet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                   
                    int id1 = Integer.parseInt(txtUser1Id.getText().trim());
                    int id2 = Integer.parseInt(txtUser2Id.getText().trim());
                    
                    User u1 = FriefindSystem.searchUser(id1);
                    User u2 = FriefindSystem.searchUser(id2);

                    if (u1 == null || u2 == null) {
                        displayTA.setText("Error: User ID(s) not found!");
                        return;
                    }

                  
                    String actType = txtActType.getText().trim();
                    if (actType.isEmpty()) {
                        displayTA.setText("Error: Activity Type cannot be empty!");
                        return;
                    }

                    
                    if (txtCity.getText().trim().isEmpty()) {
                        displayTA.setText("Error: City Name cannot be empty!");
                        return;
                    }

                  
                    double lat = Math.random() * 50;
                    double lon = Math.random() * 50;
                    Location loc = new Location(txtCity.getText(), lat, lon);

                    Activity act = new Activity(actType, 
                            Double.parseDouble(txtPrice.getText()), 
                            Integer.parseInt(txtDuration.getText()));

                    if (chkHasCoupon.isSelected()) {
                        act.applyDiscount(true);
                    }

                    Meeting m = FriefindSystem.setMeeting(u1, u2, loc, act, txtTime.getText());
                    displayTA.setText("Meeting Created Successfully!\n" + m.toString());

                } catch (NumberFormatException nfe) {
                    displayTA.setText("Error: Please check numeric fields (ID, Price, Duration)!");
                }
            }
        });
        btnSet.setBounds(330, 17, 120, 23);
        contentPane.add(btnSet);

        JButton btnDisplay = new JButton("DISPLAY ALL");
        btnDisplay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayTA.setText(FriefindSystem.displayMeetings());
            }
        });
        btnDisplay.setBounds(330, 50, 120, 23);
        contentPane.add(btnDisplay);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                mainFrame.setVisible(true);
            }
        });
        btnBack.setBounds(330, 83, 120, 23);
        contentPane.add(btnBack);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(23, 330, 440, 340);
        contentPane.add(scrollPane);

        displayTA = new JTextArea();
        displayTA.setEditable(false);
        scrollPane.setViewportView(displayTA);
    }
}