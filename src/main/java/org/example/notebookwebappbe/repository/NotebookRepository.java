package org.example.notebookwebappbe.repository;

import org.example.notebookwebappbe.entity.Notebook;
import org.example.notebookwebappbe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotebookRepository extends JpaRepository<Notebook, Long> {
    List<Notebook> findByUser(User user);
}
