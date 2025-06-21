package com.mycompany.vuexhibition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class RegistrationFrame extends JFrame {
    private ParticipantDAO participantDao;
    private JTextField txtRegId, txtName, txtFaculty, txtProjectTitle, txtContact, txtEmail;
    private JLabel lblImage;
    private JButton btnRegister, btnSearch, btnUpdate, btnDelete, btnClear, btnExit, btnUpload;
    private String imagePath = "";

    public RegistrationFrame() {
        this.participantDao = new ParticipantDAO();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Victoria University Innovation and Technology Exhibition Registration");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Form panel (left)
        JPanel formPanel = createFormPanel();
        
        // Image display panel (right)
        JPanel imagePanel = createImagePanel();
        
        // Button panel (bottom)
        JPanel buttonPanel = createButtonPanel();
        
        // Add panels to main panel
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        contentPanel.add(formPanel);
        contentPanel.add(imagePanel);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Participant Details"));
        
        formPanel.add(new JLabel("Registration ID:"));
        txtRegId = new JTextField();
        formPanel.add(txtRegId);
        
        formPanel.add(new JLabel("Student Name:"));
        txtName = new JTextField();
        formPanel.add(txtName);
        
        formPanel.add(new JLabel("Faculty:"));
        txtFaculty = new JTextField();
        formPanel.add(txtFaculty);
        
        formPanel.add(new JLabel("Project Title:"));
        txtProjectTitle = new JTextField();
        formPanel.add(txtProjectTitle);
        
        formPanel.add(new JLabel("Contact Number:"));
        txtContact = new JTextField();
        formPanel.add(txtContact);
        
        formPanel.add(new JLabel("Email Address:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);
        
        formPanel.add(new JLabel("Project Image:"));
        btnUpload = new JButton("Upload Image");
        btnUpload.addActionListener(e -> uploadImage());
        formPanel.add(btnUpload);
        
        return formPanel;
    }

    private JPanel createImagePanel() {
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBorder(BorderFactory.createTitledBorder("Project Image Preview"));
        lblImage = new JLabel();
        lblImage.setHorizontalAlignment(JLabel.CENTER);
        imagePanel.add(lblImage, BorderLayout.CENTER);
        return imagePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnRegister = new JButton("Register");
        btnRegister.addActionListener(e -> registerParticipant());
        
        btnSearch = new JButton("Search");
        btnSearch.addActionListener(e -> searchParticipant());
        
        btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(e -> updateParticipant());
        
        btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteParticipant());
        
        btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> clearForm());
        
        btnExit = new JButton("Exit");
        btnExit.addActionListener(e -> System.exit(0));
        
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnExit);
        
        return buttonPanel;
    }

    private void uploadImage() {
        String path = ImageUtilis.chooseImageFile(this);
        if (path != null) {
            imagePath = path;
            ImageIcon icon = ImageUtilis.createImageIcon(path, lblImage.getWidth(), lblImage.getHeight());
            lblImage.setIcon(icon);
        }
    }

    private void registerParticipant() {
        if (!validateInput()) return;
        
        try {
            Participant participant = createParticipantFromForm();
            if (participantDao.insertParticipant(participant)) {
                JOptionPane.showMessageDialog(this, "Participant registered successfully!");
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    private void searchParticipant() {
        String regId = txtRegId.getText().trim();
        if (regId.isEmpty()) {
            showInputError("Please enter a Registration ID to search");
            return;
        }
        
        try {
            Participant participant = participantDao.getParticipantById(regId);
            if (participant != null) {
                populateForm(participant);
            } else {
                JOptionPane.showMessageDialog(this, "No participant found with ID: " + regId, 
                    "Not Found", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    private void updateParticipant() {
        if (!validateInput()) return;
        
        try {
            Participant participant = createParticipantFromForm();
            if (participantDao.updateParticipant(participant)) {
                JOptionPane.showMessageDialog(this, "Participant updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No participant found with this ID", 
                    "Not Found", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    private void deleteParticipant() {
        String regId = txtRegId.getText().trim();
        if (regId.isEmpty()) {
            showInputError("Please enter a Registration ID to delete");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete participant with ID: " + regId + "?", 
            "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) return;
        
        try {
            if (participantDao.deleteParticipant(regId)) {
                JOptionPane.showMessageDialog(this, "Participant deleted successfully!");
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "No participant found with this ID", 
                    "Not Found", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    private void clearForm() {
        txtRegId.setText("");
        txtName.setText("");
        txtFaculty.setText("");
        txtProjectTitle.setText("");
        txtContact.setText("");
        txtEmail.setText("");
        imagePath = "";
        lblImage.setIcon(null);
    }

    private boolean validateInput() {
        if (txtRegId.getText().trim().isEmpty() ||
            txtName.getText().trim().isEmpty() ||
            txtFaculty.getText().trim().isEmpty() ||
            txtProjectTitle.getText().trim().isEmpty() ||
            txtContact.getText().trim().isEmpty() ||
            txtEmail.getText().trim().isEmpty()) {
            
            showInputError("All fields are required!");
            return false;
        }
        
        if (!txtEmail.getText().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            showInputError("Please enter a valid email address");
            return false;
        }
        
        if (!txtContact.getText().matches("\\d+")) {
            showInputError("Contact number should contain only digits");
            return false;
        }
        
        return true;
    }

    private Participant createParticipantFromForm() {
        return new Participant(
            txtRegId.getText().trim(),
            txtName.getText().trim(),
            txtFaculty.getText().trim(),
            txtProjectTitle.getText().trim(),
            txtContact.getText().trim(),
            txtEmail.getText().trim(),
            imagePath
        );
    }

    private void populateForm(Participant participant) {
        txtRegId.setText(participant.getRegId());
        txtName.setText(participant.getStudentName());
        txtFaculty.setText(participant.getFaculty());
        txtProjectTitle.setText(participant.getProjectTitle());
        txtContact.setText(participant.getContactNumber());
        txtEmail.setText(participant.getEmail());
        imagePath = participant.getImagePath();
        
        ImageIcon icon = ImageUtilis.createImageIcon(imagePath, lblImage.getWidth(), lblImage.getHeight());
        lblImage.setIcon(icon);
    }

    private void showInputError(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.WARNING_MESSAGE);
    }

    private void showDatabaseError(SQLException ex) {
        JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}


