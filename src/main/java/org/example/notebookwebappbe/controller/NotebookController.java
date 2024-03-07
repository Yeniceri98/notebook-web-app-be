package org.example.notebookwebappbe.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.notebookwebappbe.entity.Notebook;
import org.example.notebookwebappbe.entity.User;
import org.example.notebookwebappbe.repository.UserRepository;
import org.example.notebookwebappbe.service.NotebookService;
import org.example.notebookwebappbe.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Notebook Controller", description = "CRUD Operations")
@RestController
@RequestMapping("/api")
public class NotebookController {
    private final NotebookService notebookService;
    private final UserService userService;

    public NotebookController(NotebookService notebookService, UserRepository userRepository, UserService userService) {
        this.notebookService = notebookService;
        this.userService = userService;
    }

    @Tag(name = "Retrieving All Notes from Selected User")
    @GetMapping("/{username}/notes")
    public ResponseEntity<List<Notebook>> retrieveAllNotesOfUser(@PathVariable String username) {
        Optional<User> userOptional = userService.getUserByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Notebook> notebooks = notebookService.getAllNotes(user);
            return ResponseEntity.ok(notebooks);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Tag(name = "Retrieving Single Note from Selected User")
    @GetMapping("/{username}/notes/{id}")
    public ResponseEntity<Notebook> retrieveSingleNoteOfUser(@PathVariable String username, @PathVariable Long id) {
        Optional<Notebook> notebookOptional = notebookService.getNoteById(id);
        return notebookOptional.map(notebook -> ResponseEntity.ok(notebook))
                .orElse(ResponseEntity.notFound().build());
    }

    @Tag(name = "Creating a New Note for Selected User")
    @PostMapping("/{username}/notes")
    public ResponseEntity<Notebook> createNoteOfUser(@PathVariable String username, @RequestBody Notebook note) {
        Optional<User> userOptional = userService.getUserByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Notebook createdNote = notebookService.createNote(note, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Tag(name = "Updating Selected User's Note")
    @PutMapping("/{username}/notes/{id}")
    public ResponseEntity<Notebook> updateNote(@PathVariable String username, @PathVariable Long id, @RequestBody Notebook updatedNote) {
        Optional<Notebook> selectedNoteOptional = notebookService.getNoteById(id);

        if (selectedNoteOptional.isPresent()) {
            Notebook selectedNote = selectedNoteOptional.get();
            selectedNote.setContent(updatedNote.getContent());
            selectedNote.setTargetDate(updatedNote.getTargetDate());
            selectedNote.setDone(updatedNote.isDone());

            Notebook updatedNotebook = notebookService.updateNote(id, selectedNote);
            return ResponseEntity.ok(updatedNotebook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Tag(name = "Deleting Selected User's Note")
    @DeleteMapping("/{username}/notes/{id}")
    public ResponseEntity<Void> deleteNoteOfUser(@PathVariable String username, @PathVariable Long id) {
        try {
            notebookService.deleteNoteById(id);
            return ResponseEntity.noContent().build();
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
