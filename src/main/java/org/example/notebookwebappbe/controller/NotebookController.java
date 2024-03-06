package org.example.notebookwebappbe.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.notebookwebappbe.entity.Notebook;
import org.example.notebookwebappbe.service.NotebookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Notebook Controller", description = "CRUD Operations")
@RestController
@RequestMapping("/api/notebook")
public class NotebookController {
    private final NotebookService notebookService;

    public NotebookController(NotebookService notebookService) {
        this.notebookService = notebookService;
    }

    @Tag(name = "Retrieving All Notes of User")
    @GetMapping("/{username}/notes")
    public List<Notebook> retrieveAllNotesOfUser(@PathVariable String username) {
        return notebookService.getAllNotes(username);
    }

    @Tag(name = "Retrieving Single Note of User")
    @GetMapping("/{username}/notes/{id}")
    public Optional<Notebook> retrieveSingleNoteOfUser(@PathVariable String username, @PathVariable Long id) {
        return notebookService.getNoteById(id);
    }

    @Tag(name = "Create a New Note of User")
    @PostMapping("/{username}/notes")
    public ResponseEntity<Notebook> createNoteOfUser(@PathVariable String username, @RequestBody Notebook note) {
        Notebook createdNote = notebookService.createNote(note);
        return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
    }

    @Tag(name = "Update Note of User")
    @PutMapping("/{username}/notes/{id}")
    public ResponseEntity<Notebook> updateNoteOfUser(@PathVariable String username, @PathVariable Long id, @RequestBody Notebook updatedNote) {
        Notebook note = notebookService.updateNote(id, updatedNote);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @Tag(name = "Delete User's Note")
    @DeleteMapping("/{username}/notes/{id}")
    public ResponseEntity<Void> deleteNoteOfUser(@PathVariable String username, @PathVariable Long id) {
        try {
            notebookService.deleteNoteById(id);
            return ResponseEntity.noContent().build();
        } catch(Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
