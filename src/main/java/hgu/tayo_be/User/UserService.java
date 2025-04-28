package hgu.tayo_be.User;
import hgu.tayo_be.exception.AlreadyExistsException;
import hgu.tayo_be.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserDTO createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .bankAccount(request.getBankAccount())
                .carNum(request.getCarNum())
                .build();

        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return mapToDTO(user);
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return mapToDTO(user);
    }

    @Transactional
    public UserDTO updateUser(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (request.getName() != null) {
            user.setName(request.getName());
        }

        if (request.getBankAccount() != null) {
            user.setBankAccount(request.getBankAccount());
        }

        if (request.getCarNum() != null) {
            user.setCarNum(request.getCarNum());
        }

        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    private UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .bankAccount(user.getBankAccount())
                .carNum(user.getCarNum())
                .build();
    }
}