package HospitalManagmentSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Scanner;

public class Patient extends JFrame {
    private Connection connection;
    private Scanner scanner;

    // Constructor for Patient class
    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;

        // Set up the frame
        setTitle("Patient Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        // Create buttons
        JButton addPatientButton = new JButton("Add Patient");
        JButton viewPatientsButton = new JButton("View Patients");
        JButton checkPatientButton = new JButton("Search Patient");

        // Set up the layout
        setLayout(new GridLayout(3, 1));
        add(addPatientButton);
        add(viewPatientsButton);
        add(checkPatientButton);

        // Add action listeners to the buttons
        addPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPatient();
            }
        });

        viewPatientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPatients();
            }
        });

        checkPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkPatient();
            }
        });
    }

    // Empty constructor
    public Patient(Connection connection) {
    }

    // Method to add a new patient
    private void addPatient() {
        String name = JOptionPane.showInputDialog("Enter Patient Name:");
        String ageString = JOptionPane.showInputDialog("Enter Patient Age:");

        // Check if ageString is not null and not empty
        if (ageString != null && !ageString.isEmpty()) {
            try {
                int age = Integer.parseInt(ageString);
                String gender = JOptionPane.showInputDialog("Enter Patient Gender:");

                try {
                    // SQL query to insert a new patient into the 'patients' table
                    String query = "INSERT INTO patients(name, age, gender) VALUES(?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, name);
                    preparedStatement.setInt(2, age);
                    preparedStatement.setString(3, gender);

                    // Execute the query and check if the patient was added successfully
                    int affectedRows = preparedStatement.executeUpdate();
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(this, "Patient Added Successfully!!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to add Patient!!");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid age format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Handle the case where the ageString is empty
            JOptionPane.showMessageDialog(this, "Bruh Do your Work and Add The Required Information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
