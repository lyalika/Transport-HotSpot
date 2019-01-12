package me.vyara.transporthotspot.exceptions;
import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class StopNotFoundAdvice {
	@ResponseBody
	@ExceptionHandler(StopNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	Map<String, String> stopNotFoundHandler(StopNotFoundException ex) {
		return Collections.singletonMap("error", ex.getMessage());
	}
}
