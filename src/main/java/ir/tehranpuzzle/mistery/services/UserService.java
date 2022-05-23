package ir.tehranpuzzle.mistery.services;

import java.util.HashSet;
import java.util.Set;

import ir.tehranpuzzle.mistery.exception.DuplicatException;
import ir.tehranpuzzle.mistery.exception.NotFoundException;
import ir.tehranpuzzle.mistery.models.ERole;
import ir.tehranpuzzle.mistery.models.Role;
import ir.tehranpuzzle.mistery.models.User;
import ir.tehranpuzzle.mistery.payload.request.EditUserRequest;
import ir.tehranpuzzle.mistery.payload.request.SignupRequest;
import ir.tehranpuzzle.mistery.repositorys.RoleRepository;
import ir.tehranpuzzle.mistery.repositorys.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;

    public User create(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new DuplicatException("Username");
        }
        if (userRepository.existsByPhone(signUpRequest.getPhone())) {
            throw new DuplicatException("Phone");
        }
        // Create new user's account

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getFullname(),
                signUpRequest.getEmail(),
                signUpRequest.getPhone(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public User get(Long user_id) throws UsernameNotFoundException {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new NotFoundException("User Not Found with id" + user_id));
        return user;
    }

    public User getByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User Not Found with username" + username));
        return user;
    }
    public User Edit(Long user_id,EditUserRequest request){
        User user = this.get(user_id);
        //TODO edit user staff

        return userRepository.save(user);
    }

}
