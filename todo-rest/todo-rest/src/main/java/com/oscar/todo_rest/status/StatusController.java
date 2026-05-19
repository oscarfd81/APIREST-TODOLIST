package com.oscar.todo_rest.status;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/status/")
public class StatusController {

    private final StatusRepository statusRepository;

    public StatusController(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    //OBTIENES STAUS CON SU ID
    @GetMapping
    public List<Status> getAll() {
        return statusRepository.findAll();
    }

    /* 
    // CERAS TU STATU
    @PostMapping
    public Status create(@RequestBody Status status) {
        return statusRepository.save(status);
    }
        
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        statusRepository.deleteById(id);
    }*/
}