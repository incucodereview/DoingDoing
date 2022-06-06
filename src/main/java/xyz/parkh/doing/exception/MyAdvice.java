package xyz.parkh.doing.exception;

import com.sun.mail.smtp.SMTPAddressFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.parkh.doing.domain.model.ErrorDto;

@Slf4j
@RestControllerAdvice
public class MyAdvice {

    // 처리하지 못한 예외 확인하는 기능
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> exception(Exception e) {
        String message = "처리하지 못한 에러 발생 { " + e.getClass().getName() + " } ExceptionHandler 추가";
        ErrorDto error = ErrorDto.builder().error(message).build();

        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(NoJwtException.class)
    public ResponseEntity<ErrorDto> noJwtException(Exception e) {
        String message = e.getMessage();
        ErrorDto error = ErrorDto.builder().error(message).build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorDto> nullPointException(Exception e) {
        ErrorDto error = ErrorDto.builder().error(e.getClass().getName()).build();
        return ResponseEntity.badRequest().body(error);
    }

    // 인자가 없을 경우 발생하는 에러
    @ExceptionHandler(ValueException.class)
    public ResponseEntity<ErrorDto> requiredValueException(Exception e) {
        String message = e.getMessage();
        ErrorDto error = ErrorDto.builder().error(message).build();

        return ResponseEntity.badRequest().body(error);
    }

    // 메일 전송 실패시 발생하는 에러
    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<ErrorDto> mailSendException() {
        String message = "이메일 전송에 실패했습니다.";
        ErrorDto error = ErrorDto.builder().error(message).build();

        return ResponseEntity.badRequest().body(error);
    }

    // TODO
    //  메시지 전송 오류 세부 처리를 하고 싶으나
    //  MailSendException 이 먼저 발생해 아래로 내려오지 않음
    @ExceptionHandler(SMTPAddressFailedException.class)
    public ResponseEntity<ErrorDto> smtpAddressFailedException() {
        String message = "유효하지 않은 이메일 주소입니다.";
        ErrorDto error = ErrorDto.builder().error(message).build();

        return ResponseEntity.badRequest().body(error);
    }

    // 권한이 없는 사용자 정보를 수정하려 할 경우
    @ExceptionHandler(DifferentAuthException.class)
    public ResponseEntity<ErrorDto> differentAuthException(Exception e) {
        String message = e.getMessage();
        ErrorDto error = ErrorDto.builder().error(message).build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}
