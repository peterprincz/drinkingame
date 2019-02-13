package hu.kb.app.service;

import hu.kb.app.modell.User;
import hu.kb.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public String sayHelloToEveryone(){

        Iterable<User> everyone = userRepository.findAll();
        StringBuilder response = new StringBuilder();

        response.append("Hello guys:");
        everyone.forEach(user->{
            response.append(user.getName());
            response.append("\n");
        });

        return response.toString();
    }

    public void addUser(User user){
        userRepository.save(user);
    }
}
