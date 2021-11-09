package com.robinfood.encuesta.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.robinfood.encuesta.dto.EncuestaDto;
import com.robinfood.encuesta.dto.GuardarRespuestaDto;
import com.robinfood.encuesta.dto.ResponseDto;
import com.robinfood.encuesta.exception.BadRequestException;
import com.robinfood.encuesta.service.EncuestaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/encuesta")
@Api(value = "Servicios asociados a la gestión de Encuestas")
@ApiOperation(value = "Servicios asociados a la gestión de Encuestas")
public class EncuestaController {

	@Autowired
	EncuestaService service;

	@GetMapping("/get/{id}")
	@ApiOperation(value = "Obtenemos una encuesta por ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Información obtenida exitosamente", response = EncuestaDto.class) })
	public ResponseDto<EncuestaDto> getById(@PathVariable(name = "id") Long id) {
		EncuestaDto data = service.getById(id);
		return ResponseDto.create(data);
	}

	@PostMapping("/create")
	@ApiOperation(value = "Crear una encuesta")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Encuesta creada correctamente", response = Map.class) })
	public ResponseDto<Map<String, Object>> save(@Valid @RequestBody EncuestaDto data, BindingResult bindingResult)
			throws Exception {
		if (bindingResult.hasErrors())
			throw new BadRequestException(bindingResult.getAllErrors().get(0).getDefaultMessage());
		Map<String, Object> response = service.create(data);
		return ResponseDto.create(response);
	}

	@PutMapping("/update")
	@ApiOperation(value = "Actualizar una encuesta")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Encuesta actualizada correctamente", response = Map.class) })
	public ResponseDto<Map<String, Object>> update(@Valid @RequestBody EncuestaDto data, BindingResult bindingResult)
			throws Exception {
		if (bindingResult.hasErrors())
			throw new BadRequestException(bindingResult.getAllErrors().get(0).getDefaultMessage());
		Map<String, Object> response = service.create(data);
		return ResponseDto.create(response);
	}

	@DeleteMapping("/delete")
	@ApiOperation(value = "Eliminar una encuesta")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Encuesta eliminada correctamente", response = Map.class) })
	public ResponseDto<Map<String, Object>> delete(@RequestParam(name = "id") Long id) throws Exception {
		if (id == null || id < 1)
			throw new BadRequestException("El ID enviado no es valido");
		Map<String, Object> response = service.delete(id);
		return ResponseDto.create(response);
	}

	@PostMapping("/responder")
	@ApiOperation(value = "Responder una encuesta")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Respuesta guardada correctamente", response = Map.class) })
	public ResponseDto<Map<String, Object>> responder(@Valid @RequestBody ResponseDto<List<GuardarRespuestaDto>> data,
			BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors())
			throw new BadRequestException(bindingResult.getAllErrors().get(0).getDefaultMessage());
		
		Map<String, Object> response = service.responder(data.getData());					
		return ResponseDto.create(response);
	}

}
