package ai.innergrow.memory.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import ai.innergrow.memory.domain.user.model.Email;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Embedded
    private Email email;

    private String phoneNumber;
    private String avatar;
}
