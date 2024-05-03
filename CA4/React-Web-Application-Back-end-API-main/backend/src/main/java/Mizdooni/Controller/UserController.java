package Mizdooni.Controller;


import Mizdooni.Model.User.User;
import Mizdooni.Model.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepo;
    @Autowired
    public UserController() throws Exception {
        userRepo = UserRepository.getInstance();
    }
    @GetMapping("")
    public ArrayList<User> getAll() {
        return userRepo.getAll();
    }

//    @GetMapping("/{id}")
//    public User findById(@PathVariable String id) {
//        return userRepo.findById(id);
//    }
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping
//    public LiveStream create(@Valid @RequestBody LiveStream stream) {
//        return repository.create(stream);
//    }
//
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PutMapping("/{id}")
//    public void update(@RequestBody LiveStream stream, @PathVariable String id) {
//        repository.update(stream,id);
//    }
//
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable String id) {
//        repository.delete(id);
//    }

}
