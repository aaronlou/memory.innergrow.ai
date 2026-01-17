package ai.innergrow.memory.application.user;

import ai.innergrow.memory.domain.user.model.Email;
import ai.innergrow.memory.domain.user.model.User;
import ai.innergrow.memory.domain.user.repository.UserRepository;
import ai.innergrow.memory.infrastructure.config.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAppServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private UserAppService userAppService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .username("testuser")
                .email(new Email("test@example.com"))
                .build();
    }

    @Test
    void register_Success() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User result = userAppService.register("testuser", "test@example.com");

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_UserAlreadyExists_ThrowsException() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        assertThrows(RuntimeException.class, () -> 
            userAppService.register("testuser", "test@example.com")
        );
    }

    @Test
    void login_Success() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(jwtUtils.generateToken("testuser")).thenReturn("mock-token");

        String token = userAppService.login("testuser");

        assertEquals("mock-token", token);
        verify(jwtUtils).generateToken("testuser");
    }
}
