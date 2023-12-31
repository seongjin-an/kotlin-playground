package com.ansj.todoj.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    Optional<List<Todo>> findAllByDoneIsFalseOrderByIdDesc();
}