package org.example.notebookwebappbe.service;

import org.example.notebookwebappbe.entity.Notebook;
import org.example.notebookwebappbe.entity.User;
import org.example.notebookwebappbe.repository.NotebookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotebookService {

    private final NotebookRepository notebookRepository;

    public NotebookService(NotebookRepository notebookRepository) {
        this.notebookRepository = notebookRepository;
    }

    public List<Notebook> getAllNotes(User username) {
        return notebookRepository.findByUser(username);
    }

    public Optional<Notebook> getNoteById(Long id) {
        return notebookRepository.findById(id);
    }

    public Notebook createNote(Notebook note, User loggedInUser) {
        note.setUser(loggedInUser);
        return notebookRepository.save(note);
    }

    public Notebook updateNote(Long id, Notebook updatedNote) {
        Optional<Notebook> selectedNote = notebookRepository.findById(id);

        if (selectedNote.isPresent()) {
            updatedNote.setId(id);
            return notebookRepository.save(updatedNote);
        } else {
            throw new IllegalArgumentException("Note with id " + id + " is not found!");
        }
    }

    public void deleteNoteById(Long id) {
        notebookRepository.deleteById(id);
    }
}
