package com.example.SpringTaskList.controller;

import com.example.SpringTaskList.model.Task;
import com.example.SpringTaskList.model.TaskRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author marcin
 */

//dzieki kontrolerowi mozemy zarzadzac naszym modelem (repozytorium), jego danymi
//umozliwia on rowniez wyswietlanie informacji
@RepositoryRestController
class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    
    private final TaskRepository repository;
    
    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }
    
    //zwraca obiekt z wszystkimi taskami
    //mapowanie zapytan informuje, ze ta metoda ma zastapic metode z repozytorium
    //parametry informuja o wykorzystywanej metodzie i sciezce
    //dzieki temu mozemy dopilnowac, co zostanie wyslane w odpowiedzi
    //mozemy takze operowac na wyszukanych danych
    //repozytorium owija dane w hateoas, tutaj to pomijamy
    @RequestMapping(method = RequestMethod.GET, path = "/tasks",
            //spring wie, ze moze skorzystac z metody, jezeli nie uzyjemy
            //ponizszych metod
                    params = {"!sort", "!page", "!size"})
    //@GetMapping(value = "/tasks") //RequestMapping dla metody GET
    ResponseEntity<List<Task>> getAllTasks() {
        logger.info("Here are your tasks!");
        return ResponseEntity.ok(repository.findAll());
    }
    
    //zapytanie ze stronicowaniem
    @GetMapping(value = "/tasks", params = "page")
    ResponseEntity<List<Task>> getAllTasks(Pageable page) {
        logger.info("Here are your tasks! Pageable");
        //metoda get content, aby z naszego obiektu Page wybrac tylko
        //interesujaca nas liste taskow
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }
}
