package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.util.ArrayList;

import model.ProfileDetails;
import model.StandardUser;
import model.PremiumUser;
import model.User;
import service.FriefindSystem;

public class MainFrame extends JFrame {

    private JPanel contentPane;

    private JTextField idTF;
    private JTextField nameTF;
    private JTextField ageTF;
    private JTextField cityTF;

    private JTextField bioTF;    
    private JTextField hobbiesTF;
    private JComboBox<String> packetCMB;
    private JCheckBox incognitoCB;

    private JTextArea dispTA;

    private DefaultListModel<User> listModel;
    private JList<User> userList;
    
	MeetingFrame mf = new MeetingFrame(this);

    public MainFrame() {

        setTitle("Friefind - Main");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 450);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel idLbl = new JLabel("ID:");
        idLbl.setBounds(20, 20, 80, 14);
        contentPane.add(idLbl);

        idTF = new JTextField();
        idTF.setBounds(120, 17, 220, 20);
        contentPane.add(idTF);

        JLabel nameLbl = new JLabel("Name:");
        nameLbl.setBounds(20, 55, 80, 14);
        contentPane.add(nameLbl);

        nameTF = new JTextField();
        nameTF.setBounds(120, 52, 220, 20);
        contentPane.add(nameTF);

        JLabel ageLbl = new JLabel("Age:");
        ageLbl.setBounds(20, 90, 80, 14);
        contentPane.add(ageLbl);

        ageTF = new JTextField();
        ageTF.setBounds(120, 87, 220, 20);
        contentPane.add(ageTF);

        JLabel cityLbl = new JLabel("City:");
        cityLbl.setBounds(20, 125, 80, 14);
        contentPane.add(cityLbl);

        cityTF = new JTextField();
        cityTF.setBounds(120, 122, 220, 20);
        contentPane.add(cityTF);

        JLabel bioLbl = new JLabel("Bio:");
        bioLbl.setBounds(20, 160, 80, 14);
        contentPane.add(bioLbl);

        bioTF = new JTextField();
        bioTF.setBounds(120, 157, 220, 20);
        contentPane.add(bioTF);

        JLabel hobbiesLbl = new JLabel("Hobbies:");
        hobbiesLbl.setBounds(20, 195, 80, 14);
        contentPane.add(hobbiesLbl);

        hobbiesTF = new JTextField();
        hobbiesTF.setBounds(120, 192, 220, 20);
        contentPane.add(hobbiesTF);

        JLabel packetLbl = new JLabel("Packet:");
        packetLbl.setBounds(20, 230, 80, 14);
        contentPane.add(packetLbl);

        packetCMB = new JComboBox<String>();
        packetCMB.setModel(new DefaultComboBoxModel<String>(new String[] { "Standard", "Premium" }));
        packetCMB.setBounds(120, 226, 220, 22);
        contentPane.add(packetCMB);

        incognitoCB = new JCheckBox("Incognito (Premium)");
        incognitoCB.setBounds(120, 255, 220, 23);
        contentPane.add(incognitoCB);

        // disable by default because Standard is selected
        incognitoCB.setEnabled(false);

        // enable only for Premium
        packetCMB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String packet = (String) packetCMB.getSelectedItem();
                boolean premium = "Premium".equalsIgnoreCase(packet);
                incognitoCB.setEnabled(premium);
                if (!premium) incognitoCB.setSelected(false);
            }
        });

        JButton addBtn = new JButton("ADD");
        addBtn.setBounds(30, 290, 140, 23);
        contentPane.add(addBtn);

        JButton delBtn = new JButton("DELETE");
        delBtn.setBounds(200, 290, 140, 23);
        contentPane.add(delBtn);

        JButton dispBtn = new JButton("DISPLAY ALL");
        dispBtn.setBounds(30, 325, 140, 23);
        contentPane.add(dispBtn);

        JButton searchBtn = new JButton("SEARCH");
        searchBtn.setBounds(200, 325, 140, 23);
        contentPane.add(searchBtn);

        JButton openSearchFrameBtn = new JButton("SEARCH WINDOW");
        openSearchFrameBtn.setBounds(30, 360, 140, 23);
        contentPane.add(openSearchFrameBtn);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(360, 20, 520, 170);
        contentPane.add(scrollPane);

        dispTA = new JTextArea();
        scrollPane.setViewportView(dispTA);
        dispTA.setEditable(false);

        listModel = new DefaultListModel<User>();
        userList = new JList<User>(listModel);

        JScrollPane listScroll = new JScrollPane();
        listScroll.setBounds(360, 210, 520, 180);
        listScroll.setViewportView(userList);
        contentPane.add(listScroll);
        
        JButton setmeetingbtn = new JButton("SET MEETING");
        setmeetingbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mf.setVisible(true);
				setVisible(false);
				
			}
		});
        setmeetingbtn.setBounds(200, 358, 140, 25);
        contentPane.add(setmeetingbtn);

        // Button actions
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doAdd();
            }
        });

        delBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doDelete();
            }
        });

        dispBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doDisplay();
            }
        });

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doSearch();
            }
        });

        // Key event: Enter on name triggers search
        nameTF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    doSearch();
                }
            }
        });

        // Mouse click: clicking list fills fields
        userList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                User u = userList.getSelectedValue();
                if (u != null) {
                    idTF.setText("" + u.getId());
                    nameTF.setText(u.getName());
                    ageTF.setText("" + u.getAge());
                    cityTF.setText(u.getCity());
                    dispTA.setText(u.toString());
                }
            }
        });

        openSearchFrameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchUserFrame sf = new SearchUserFrame(MainFrame.this);
                sf.setVisible(true);
            }
        });

        doDisplay();
    }

    private void doAdd() {
        dispTA.setText("");

        try {
            int id = Integer.parseInt(idTF.getText().trim());
            String name = nameTF.getText().trim();
            int age = Integer.parseInt(ageTF.getText().trim());
            String city = cityTF.getText().trim();

            String bio = bioTF.getText().trim();
            String hobbies = hobbiesTF.getText().trim();
            String packet = (String) packetCMB.getSelectedItem();
            boolean incognito = incognitoCB.isSelected();

            if (name.isEmpty() || city.isEmpty()) {
                dispTA.setText("Name/City cannot be empty.");
                return;
            }

            ProfileDetails details = new ProfileDetails(bio, hobbies);

            User u;
            if ("Standard".equalsIgnoreCase(packet)) {
                u = new StandardUser(id, name, age, details, city);
            } else {
                u = new PremiumUser(id, name, age, details, city, incognito);
            }

            boolean ok = FriefindSystem.addUser(u);

            if (ok) dispTA.setText("User added.");
            else dispTA.setText("User could not be added (ID already exists).");

            doDisplay();

        } catch (NumberFormatException ex) {
            dispTA.setText("ID and Age must be numbers.");
        } catch (Exception ex) {
            dispTA.setText("Add error: " + ex.getMessage());
        }
    }

    private void doDelete() {
        dispTA.setText("");

        try {
            int id = Integer.parseInt(idTF.getText().trim());
            FriefindSystem.deleteUser(id);
            dispTA.setText("Deleted user with id: " + id);
            doDisplay();

        } catch (NumberFormatException ex) {
            dispTA.setText("ID must be a number.");
        } catch (Exception ex) {
            dispTA.setText("Delete error: " + ex.getMessage());
        }
    }

    private void doSearch() {
        dispTA.setText("");

        String name = nameTF.getText().trim();
        if (name.isEmpty()) {
            dispTA.setText("Enter a name to search.");
            return;
        }

        try {
            User u = FriefindSystem.searchUser(name);
            if (u == null) dispTA.setText("User not found.");
            else dispTA.setText(u.toString());

        } catch (Exception ex) {
            dispTA.setText("Search error: " + ex.getMessage());
        }
    }

    private void doDisplay() {
        dispTA.setText("");

        try {
            ArrayList<User> users = FriefindSystem.getUsers();

            listModel.clear();
            for (User u : users) {
                listModel.addElement(u);
            }

            // If your system has displayAllUsersString(), keep this:
            dispTA.setText(FriefindSystem.displayAllUsersString());

            // If NOT, comment above line and use:
            // dispTA.setText("Total users: " + users.size());

        } catch (Exception ex) {
            dispTA.setText("Display error: " + ex.getMessage());
        }
    }

    public void fillUserFields(User u) {
        if (u == null) return;
        idTF.setText("" + u.getId());
        nameTF.setText(u.getName());
        ageTF.setText("" + u.getAge());
        cityTF.setText(u.getCity());
        dispTA.setText(u.toString());
    }
}
