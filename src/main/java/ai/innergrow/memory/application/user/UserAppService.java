package ai.innergrow.memory.application.user;

import ai.innergrow.memory.domain.user.model.Email;
import ai.innergrow.memory.domain.user.model.User;
import ai.innergrow.memory.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.innergrow.memory.infrastructure.config.JwtUtils;

@Service
@RequiredArgsConstructor
public class UserAppService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Transactional
    public String login(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return jwtUtils.generateToken(user.getUsername());
    }

    @Transactional
    public User register(String username, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        User user = User.builder()
                .username(username)
                .email(new Email(email))
                .build();
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
