package com.mycompany.vuexhibition;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantDAO {
    private static final String DB_URL = "jdbc:ucanaccess://VU_Exhibition.accdb";
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Insert a new participant
    public boolean insertParticipant(Participant participant) throws SQLException {
        String sql = "INSERT INTO Participants (RegID, StudentName, Faculty, ProjectTitle, ContactNumber, Email, ImagePath) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            setParticipantParameters(pstmt, participant);
            return pstmt.executeUpdate() > 0;
        }
    }

    // Get participant by ID
    public Participant getParticipantById(String regId) throws SQLException {
        String sql = "SELECT * FROM Participants WHERE RegID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, regId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractParticipantFromResultSet(rs);
            }
            return null;
        }
    }

    // Update participant
    public boolean updateParticipant(Participant participant) throws SQLException {
        String sql = "UPDATE Participants SET StudentName = ?, Faculty = ?, ProjectTitle = ?, " +
                    "ContactNumber = ?, Email = ?, ImagePath = ? WHERE RegID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            setParticipantParameters(pstmt, participant);
            pstmt.setString(7, participant.getRegId());
            return pstmt.executeUpdate() > 0;
        }
    }

    // Delete participant
    public boolean deleteParticipant(String regId) throws SQLException {
        String sql = "DELETE FROM Participants WHERE RegID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, regId);
            return pstmt.executeUpdate() > 0;
        }
    }

    // Get all participants
    public List<Participant> getAllParticipants() throws SQLException {
        List<Participant> participants = new ArrayList<>();
        String sql = "SELECT * FROM Participants";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                participants.add(extractParticipantFromResultSet(rs));
            }
        }
        return participants;
    }

    // Helper method to set parameters for PreparedStatement
    private void setParticipantParameters(PreparedStatement pstmt, Participant participant) 
            throws SQLException {
        pstmt.setString(1, participant.getRegId());
        pstmt.setString(2, participant.getStudentName());
        pstmt.setString(3, participant.getFaculty());
        pstmt.setString(4, participant.getProjectTitle());
        pstmt.setString(5, participant.getContactNumber());
        pstmt.setString(6, participant.getEmail());
        pstmt.setString(7, participant.getImagePath());
    }

    // Helper method to create Participant from ResultSet
    private Participant extractParticipantFromResultSet(ResultSet rs) throws SQLException {
        return new Participant(
            rs.getString("RegID"),
            rs.getString("StudentName"),
            rs.getString("Faculty"),
            rs.getString("ProjectTitle"),
            rs.getString("ContactNumber"),
            rs.getString("Email"),
            rs.getString("ImagePath")
        );
    }
}