package kz.pandev.command.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static kz.pandev.command.constant.Constant.DATA_TIME_FORMATTER;

@Getter
@Builder
@ToString
public class ApiError {
    private List<String> errors;
    private String message;
    private String reason;
    private String status;
    @Builder.Default
    private String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATA_TIME_FORMATTER));
}
