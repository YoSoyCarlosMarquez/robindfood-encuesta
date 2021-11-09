package com.robinfood.encuesta.exception.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.robinfood.encuesta.dto.ResponseDto;
import com.robinfood.encuesta.dto.constant.Message;
import com.robinfood.encuesta.dto.exception.ErrorInfo;
import com.robinfood.encuesta.exception.BadRequestException;
import com.robinfood.encuesta.exception.ForbiddenException;
import com.robinfood.encuesta.exception.InconsistencyException;
import com.robinfood.encuesta.exception.ResourceNotFoundException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private final Environment env;
	private final Message message;

	private static final String PACKAGE = "unisimon";
	private static final String TXT_ERROR = "ERROR - { ";
	private static final String TXT_REASON = " REASON: ";
	private static final String TXT_DETAILS = ". Más información: ";

	private ResponseEntity<ResponseDto<ErrorInfo>> response(ErrorInfo error, HttpStatus httpStatus) {
		return new ResponseEntity<>(ResponseDto.create(error), httpStatus);
	}

	private Map<String, String> getDataFromException(Exception exception) {
		String clase = exception.getClass().getSimpleName();
		String mensaje = (exception.getMessage() != null) ? exception.getMessage() : "No se identificó un mensaje";
		String cause = (exception.getCause() != null && exception.getCause().getCause() != null)
				? exception.getCause().getCause().getMessage()
				: "No se identificó una causa";
		String detalle = "";
		if (exception.getStackTrace() != null && exception.getStackTrace().length > 0) {
			StackTraceElement[] stackTrace = exception.getStackTrace();
			for (StackTraceElement stackTraceElement : stackTrace) {
				if (stackTraceElement.getClassName().contains(PACKAGE)) {
					detalle = stackTraceElement.toString();
					break;
				}
			}
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("class", clase);
		map.put("message", mensaje);
		map.put("cause", cause);
		map.put("details", detalle);
		return map;
	}

	public String processException(Exception e) {
		String messageException = "";
		String profile = env.getProperty("spring.profiles.active");
		if ("dev".equalsIgnoreCase(profile)) {
			Map<String, String> dataException = getDataFromException(e);
			messageException = "Clase: ".concat(dataException.get("class")).concat(". Causa: ")
					.concat(dataException.get("cause")).concat(". Mensaje: ").concat(dataException.get("message"))
					.concat(". Detalles: ").concat(dataException.get("details"));
		}
		return messageException;
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ResponseDto<ErrorInfo>> handleResourceNotFoundException(ResourceNotFoundException e,
			HttpServletRequest request) {
		String messageException = e.getMessage();
		log.warn(TXT_ERROR.concat(request.getMethod()).concat(" } ").concat(request.getRequestURI()).concat(TXT_REASON)
				.concat(messageException));
		return response(
				ErrorInfo.create(HttpServletResponse.SC_NOT_FOUND, message.getNoFound(), messageException),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<ResponseDto<ErrorInfo>> handleForbiddenException(ForbiddenException e,
			HttpServletRequest request) {
		String messageException = e.getMessage();
		log.warn(TXT_ERROR.concat(request.getMethod()).concat(" } ").concat(request.getRequestURI()).concat(TXT_REASON)
				.concat(messageException));
		return response(ErrorInfo.create(HttpServletResponse.SC_FORBIDDEN, message.getForbidden(), messageException),
				HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ResponseDto<ErrorInfo>> handleBadRequestException(BadRequestException e,
			HttpServletRequest request) {
		String messageException = e.getMessage();
		log.warn(TXT_ERROR.concat(request.getMethod()).concat(" } ").concat(request.getRequestURI()).concat(TXT_REASON)
				.concat(messageException));
		return response(ErrorInfo.create(HttpServletResponse.SC_BAD_REQUEST, message.getBadRequest(), messageException),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InconsistencyException.class)
	public ResponseEntity<ResponseDto<ErrorInfo>> handleInconsistencyException(InconsistencyException e,
			HttpServletRequest request) {
		String messageException = e.getMessage();
		log.warn(TXT_ERROR.concat(request.getMethod()).concat(" } ").concat(request.getRequestURI()).concat(TXT_REASON)
				.concat(messageException));
		return response(ErrorInfo.create(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message.getInconsistency(),
				messageException), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "El parámetro ".concat(e.getParameterName()).concat(" es requerido.");
		String messageException = error.concat(TXT_DETAILS).concat(Objects.requireNonNull(e.getMessage()));
		log.warn(TXT_ERROR.concat(request.getDescription(true)).concat(" } ").concat(TXT_REASON)
				.concat(messageException));
		return new ResponseEntity<>(ResponseDto
				.create(ErrorInfo.create(status.value(), message.getMissingServletRequestParameter(), error)), status);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<ResponseDto<ErrorInfo>> handleConstraintViolation(ConstraintViolationException e,
			WebRequest request) {
		String messageException = processException(e);
		StringBuilder errors = new StringBuilder();
		for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
			errors.append(
					violation.getRootBeanClass().getName().concat(" ").concat(violation.getPropertyPath().toString())
							.concat(": ").concat(violation.getMessage()).concat(" "));
		}
		messageException = messageException.concat(TXT_DETAILS).concat(errors.toString());
		log.warn(TXT_ERROR.concat(request.getDescription(true)).concat(" } ").concat(TXT_REASON)
				.concat(messageException));
		return response(ErrorInfo.create(HttpServletResponse.SC_BAD_REQUEST, message.getConstraintViolation(),
				messageException), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<ResponseDto<ErrorInfo>> handleDataIntegrityViolationException(
			DataIntegrityViolationException e, WebRequest request) {
		String messageException = processException(e);
		log.warn(TXT_ERROR.concat(request.getDescription(true)).concat(" } ").concat(TXT_REASON)
				.concat(messageException != null ? messageException : ""));
		return response(ErrorInfo.create(HttpServletResponse.SC_BAD_REQUEST,
				message.getConstraintViolation(),
				messageException), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<ResponseDto<ErrorInfo>> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException e, HttpServletRequest request) {
		String error = "El parámetro ".concat(e.getName()).concat(" debe ser de tipo ")
				.concat((e.getRequiredType() != null ? e.getRequiredType().getName() : ""));
		String messageException = error.concat(TXT_DETAILS).concat(e.getMessage() != null ? e.getMessage() : "");
		log.warn(TXT_ERROR.concat(request.getMethod()).concat(" } ").concat(request.getRequestURI()).concat(TXT_REASON)
				.concat(messageException));
		return response(ErrorInfo.create(HttpServletResponse.SC_BAD_REQUEST, message.getArgumentTypeMismatch(), error),
				HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException e, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String messageException = e.getCause() != null && e.getCause().getMessage() != null ? e.getCause().getMessage()
				: "";
		log.warn(TXT_ERROR.concat(request.getDescription(true)).concat(" } ").concat(TXT_REASON)
				.concat(messageException));
		return new ResponseEntity<>(ResponseDto.create(
				ErrorInfo.create(HttpServletResponse.SC_BAD_REQUEST, message.getBadRequest(), messageException)),
				status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder errors = new StringBuilder();
		errors.append(" El método ");
		errors.append(e.getMethod());
		errors.append(" no es soportado para esta petición. Los métodos soportados son ");
		if (e.getSupportedHttpMethods() != null) {
			e.getSupportedHttpMethods().forEach(stm -> errors.append(stm).append(" "));
		}
		log.warn(TXT_ERROR.concat(request.getDescription(true)).concat(" } ").concat(TXT_REASON)
				.concat(errors.toString()));
		return new ResponseEntity<>(ResponseDto.create(ErrorInfo.create(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
				message.getMethodNoAllow(), errors.toString())), status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder error = new StringBuilder();
		error.append("El tipo de media ");
		error.append(e.getContentType());
		error.append(" no es soportado. Los tipos de media soportados son ");
		e.getSupportedMediaTypes().forEach(smt -> error.append(smt).append(" "));
		String messageException = error.toString().concat(TXT_DETAILS).concat(error.toString());
		log.warn(TXT_ERROR.concat(request.getDescription(true)).concat(" } ").concat(TXT_REASON)
				.concat(messageException));
		return new ResponseEntity<>(ResponseDto.create(ErrorInfo.create(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
				message.getMediaTypeNoSupported(), error.toString())), status);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String messageException = processException(e);

		List<String> errorList = new ArrayList<>();
		for (FieldError error : e.getBindingResult().getFieldErrors()) {
			errorList.add(error.getField() + ": " + error.getDefaultMessage());
		}
		Optional<String> optionalErrors = errorList.stream().reduce((prev, next) -> prev.concat(", ").concat(next));
		messageException = messageException.concat(TXT_DETAILS).concat(optionalErrors.orElse(""));
		log.warn(TXT_ERROR.concat(request.getDescription(true)).concat(" } ").concat(TXT_REASON)
				.concat(messageException));
		return new ResponseEntity<>(ResponseDto.create(ErrorInfo.create(HttpServletResponse.SC_BAD_REQUEST,
				message.getBadRequest(), optionalErrors.orElse(""))), status);
	}

	@Override
	public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String messageException = "No fue posible manejar la petición ".concat(e.getRequestURL())
				.concat(" mediante el método ").concat(e.getHttpMethod());
		log.warn(TXT_ERROR.concat(request.getDescription(true)).concat(" } ").concat(TXT_REASON)
				.concat(messageException));
		return new ResponseEntity<>(
				ResponseDto.create(
						ErrorInfo.create(HttpServletResponse.SC_NOT_FOUND, message.getNoFound(), messageException)),
				status);
	}

	@Override
	public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String messageException = processException(e);
		log.warn(TXT_ERROR.concat(request.getDescription(true)).concat(" } ").concat(TXT_REASON)
				.concat(messageException));
		return new ResponseEntity<>(ResponseDto.create(ErrorInfo.create(HttpServletResponse.SC_BAD_REQUEST,
				message.getBadRequest(),
				!messageException.isEmpty() ? messageException : "El cuerpo de la petición no puede ser procesado.")),
				status);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ResponseDto<ErrorInfo>> handleAllExceptions(Exception e, HttpServletRequest request) {
		String messageException = processException(e);
		log.error(TXT_ERROR.concat(request.getMethod()).concat(" } ").concat(request.getRequestURI()).concat(TXT_REASON)
				.concat(messageException));
		log.error("Extensión de la excepción: ", e);
		return response(
				ErrorInfo.create(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message.getGeneral(), messageException),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
