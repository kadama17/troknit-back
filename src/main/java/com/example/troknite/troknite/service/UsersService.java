package com.example.troknite.troknite.service;

import com.example.troknite.troknite.domain.Users;
import com.example.troknite.troknite.model.UsersDTO;
import com.example.troknite.troknite.repos.UsersRepository;
import com.example.troknite.troknite.util.NotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsersService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
    private final UsersRepository usersRepository;

    public UsersService(final UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<UsersDTO> findAll() {
        final List<Users> userss = usersRepository.findAll(Sort.by("id"));
        return userss.stream()
                .map((users) -> mapToDTO(users, new UsersDTO()))
                .toList();
    }

    public UsersDTO get(final Long id) {
        return usersRepository.findById(id)
                .map(users -> mapToDTO(users, new UsersDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UsersDTO usersDTO) {
        final Users users = new Users();
        mapToEntity(usersDTO, users);
        System.out.println("User registered successfully: " + users.getEmail());

        return usersRepository.save(users).getId();
    }

    public void update(final Long id, final UsersDTO usersDTO) {
        final Users users = usersRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usersDTO, users);
        usersRepository.save(users);
    }

    public void delete(final Long id) {
        usersRepository.deleteById(id);
    }

    private UsersDTO mapToDTO(final Users users, final UsersDTO usersDTO) {
        usersDTO.setId(users.getId());
        usersDTO.setName(users.getName());
        usersDTO.setSurname(users.getSurname());
        usersDTO.setEmail(users.getEmail());
        return usersDTO;
    }

    private Users mapToEntity(final UsersDTO usersDTO, final Users users) {
        users.setName(usersDTO.getName());
        users.setSurname(usersDTO.getSurname());
        users.setEmail(usersDTO.getEmail());
        users.setPassword(usersDTO.getPassword());
        return users;
    }
    
    
    
    
    public Users register(UsersDTO usersDTO) {
        Users user = new Users();
        user.setName(usersDTO.getName());
        user.setSurname(usersDTO.getSurname());
        user.setEmail(usersDTO.getEmail());
        user.setPassword(passwordEncoder.encode(usersDTO.getPassword()));
        return usersRepository.save(user);
     }

    public UsersDTO convertToDto(Users user) {
        UsersDTO userDTO = new UsersDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
    
    public Users findByEmail(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
    }

    public Users getUserById(Long id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }
    
    public Users registerFacebookUser(String email) {
        Users user = new Users();
        user.setEmail(email);
        // Set other properties for the Facebook user as needed
        return usersRepository.save(user);
    }
    
}
