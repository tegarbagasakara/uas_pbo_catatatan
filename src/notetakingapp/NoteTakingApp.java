/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


/**
 *
 * @author Kidoo
 */
package notetakingapp;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class NoteTakingApp {
    private NoteDAO noteDAO;
    private final Scanner scanner;

    public NoteTakingApp() {
        try {
            noteDAO = new NoteDAO();
        } catch (SQLException e) {
        }
        scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("1. Tambah Catatan");
            System.out.println("2. Tampilkan Catatan");
            System.out.println("3. Hapus Catatan");
            System.out.println("4. Keluar");
            System.out.print("Pilih opsi: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline
            } catch (InputMismatchException e) {
                scanner.nextLine();  // Consume invalid input
                System.out.println("Pilihan tidak valid. Coba lagi.");
                continue;
            }

            switch (choice) {
                case 1 -> addNote();
                case 2 -> displayNotes();
                case 3 -> deleteNote();
                case 4 -> {
                    System.out.println("Keluar dari aplikasi.");
                    return;
                }
                default -> System.out.println("Pilihan tidak valid. Coba lagi.");
            }
        }
    }

    private void addNote() {
        System.out.print("Masukkan deskripsi catatan: ");
        String description = scanner.nextLine();
        try {
            noteDAO.addNote(description);
            System.out.println("Catatan berhasil ditambahkan.");
        } catch (SQLException e) {
            System.out.println("Gagal menambahkan catatan.");
        }
    }

    private void displayNotes() {
        try {
            List<Note> notes = noteDAO.getAllNotes();
            if (notes.isEmpty()) {
                System.out.println("Tidak ada catatan.");
            } else {
                for (Note note : notes) {
                    System.out.println(note);
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal menampilkan catatan.");
        }
    }

    private void deleteNote() {
        displayNotes();
        System.out.print("Masukkan ID catatan yang ingin dihapus: ");
        int id;
        try {
            id = scanner.nextInt();
            scanner.nextLine();  // Consume newline
        } catch (InputMismatchException e) {
            scanner.nextLine();  // Consume invalid input
            System.out.println("ID tidak valid. Coba lagi.");
            return;
        }

        try {
            noteDAO.deleteNote(id);
            System.out.println("Catatan berhasil dihapus.");
        } catch (SQLException e) {
            System.out.println("Gagal menghapus catatan.");
        }
    }

    public static void main(String[] args) {
        NoteTakingApp app = new NoteTakingApp();
        app.run();
    }
}

