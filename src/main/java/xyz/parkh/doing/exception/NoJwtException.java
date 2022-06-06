package xyz.parkh.doing.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoJwtException extends RuntimeException {
    String message;
}
