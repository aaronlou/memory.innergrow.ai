package ai.innergrow.memory.domain.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email implements Serializable {
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Column(name = "email", nullable = false)
    private String address;

    public Email(String address) {
        if (address == null || !VALID_EMAIL_ADDRESS_REGEX.matcher(address).find()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.address = address;
    }

    @Override
    public String toString() {
        return address;
    }
}
