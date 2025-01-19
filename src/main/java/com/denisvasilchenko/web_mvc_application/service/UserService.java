package com.denisvasilchenko.web_mvc_application.service;


import com.denisvasilchenko.web_mvc_application.entity.User;
import com.denisvasilchenko.web_mvc_application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;


    public User findUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        //как я уже писал лучше либо возвращать Optional в этом методе либо сделать
        //user.orElseThrow(() -> new UsernameNotFoundException("Cannot find user by id:%s".formatted(id)));
        //null никогда не возвращается
        return user.orElse(null);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    //результат saveUser нигде не используется, если пытаешься добавить юзера с тем же именем то ни ошибки не будет
    //и юзер не добавится, подумай как можно улучшить (может стоить выбрасывать ошибку?)
    public boolean saveUser(User user) {
        User savedUser = userRepository.findUserByName(user.getName());
        if (savedUser != null) {
            return false;
        }

        userRepository.save(user);
        return true;
    }

    public void deleteUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        }
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    //есть такая конвенция, если метод начинается с find то он возвращает либо List либо Optional
    //если у тебя User то должно быть getUserByName
    //посмотри все похожие методы и поправь
    public User findUserByName(String name) {
        return userRepository.findUserByName(name);
    }
}
