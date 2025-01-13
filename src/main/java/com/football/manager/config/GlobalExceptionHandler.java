package com.football.manager.config;

import com.football.manager.exception.InsufficientFundsException;
import com.football.manager.exception.PlayerNotFoundException;
import com.football.manager.exception.TeamNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePlayerNotFound(PlayerNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Player not found", ex.getMessage());
    }

    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTeamNotFound(TeamNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Team not found", ex.getMessage());
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<Map<String, Object>> handleInsufficientFunds(InsufficientFundsException ex) {
        return buildErrorResponse(HttpStatus.PAYMENT_REQUIRED, "Insufficient funds", ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid argument", ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String error, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("error", error);
        response.put("message", message);
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.status(status).body(response);
    }
}