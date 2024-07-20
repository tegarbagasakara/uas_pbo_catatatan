/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Kidoo
 */
package notetakingapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDAO {
    private final Connection connection;

    public NoteDAO() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:notes.db");
        createTable();
    }

    private void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS notes (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "description TEXT NOT NULL)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void addNote(String description) throws SQLException {
        String sql = "INSERT INTO notes (description) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, description);
            pstmt.executeUpdate();
        }
    }

    public List<Note> getAllNotes() throws SQLException {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM notes ORDER BY id DESC";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Note note = new Note();
                note.setId(rs.getInt("id"));
                note.setDescription(rs.getString("description"));
                notes.add(note);
            }
        }
        return notes;
    }

    public void deleteNote(int id) throws SQLException {
        String sql = "DELETE FROM notes WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
