package model;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StandardError {

    private Instant timestamp;
    private String message;
    private String path;
}
