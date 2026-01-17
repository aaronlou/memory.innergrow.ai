package ai.innergrow.memory.domain.event.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location implements Serializable {
    
    @Column(name = "location_name")
    private String name;
    
    @Column(name = "latitude")
    private Double latitude;
    
    @Column(name = "longitude")
    private Double longitude;

    public Location(String name) {
        this(name, null, null);
    }

    public Location(String name, Double latitude, Double longitude) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Location name cannot be empty");
        }
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return name;
    }
}
