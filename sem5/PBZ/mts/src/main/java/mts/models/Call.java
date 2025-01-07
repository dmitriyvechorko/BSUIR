package mts.models;

import lombok.*;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Call {
    private int id;
    private int clientId;
    private LocalDateTime dateOfCall;
    private String cityCalledTo;
    private String phoneNumber;
    private LocalTime callDuration;
}