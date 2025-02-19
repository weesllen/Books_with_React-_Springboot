package com.well.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.well.demo.data.vo.v1.PersonVO;
import com.well.demo.services.PersonServices;
import com.well.demo.util.MediaType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;


@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController {
	
		@Autowired
		private PersonServices service;
	
		@CrossOrigin(origins = {"http//localhost8080","https://well.com.br"})
		@GetMapping (produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.APPLICATION_YML})
		@Operation(summary = "Finds all Pepople", description = "Fins all People",
				tags = {"People"},
				responses = {
					@ApiResponse(description = "Sucess", responseCode = "200", 
						content = {
							@Content(
									mediaType = "application/json",
									array = @ArraySchema(schema = @Schema(implementation = PersonVO.class ))
									)
					}),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content ),	
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content ),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content ),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content )
				})
		public ResponseEntity<PagedModel<EntityModel<PersonVO >>> findAll(
			@RequestParam (value = "page", defaultValue = "0")Integer page,
			@RequestParam (value = "size", defaultValue = "12") Integer size, 
			@RequestParam (value = "direction", defaultValue = "asc") String direction) {
			
			var sortDirection = "desc".equalsIgnoreCase(direction)
					? Direction.DESC : Direction.ASC;
					
			Pageable pageable = PageRequest.of(page,size, Sort.by(sortDirection, "FirstName"));
			return ResponseEntity.ok(service.findAll(pageable));			
		  }
		
		
		@GetMapping (value = "/findPersonByName/{firstName}",
				produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.APPLICATION_YML})
		@Operation(summary = "Finds persons by name", description = "Finds persons by name",
		tags = {"People"},
		responses = {
				@ApiResponse(description = "Sucess", responseCode = "200", 
						content = {
								@Content(
										mediaType = "application/json",
										array = @ArraySchema(schema = @Schema(implementation = PersonVO.class ))
										)
				}),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content ),	
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content ),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content ),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content )
		})
		public ResponseEntity<PagedModel<EntityModel<PersonVO >>> findPersonByName(
				@PathParam (value = "firstName")String firstName,
				@RequestParam (value = "page", defaultValue = "0")Integer page,
				@RequestParam (value = "size", defaultValue = "12") Integer size, 
				@RequestParam (value = "direction", defaultValue = "asc") String direction) {
			
			var sortDirection = "desc".equalsIgnoreCase(direction)
					? Direction.DESC : Direction.ASC;
			
			Pageable pageable = PageRequest.of(page,size, Sort.by(sortDirection, "firstName"));
			return ResponseEntity.ok(service.findPersonsByName(firstName,pageable));			
		}
		
		
		
		@CrossOrigin(origins = {"http//localhost8080","https://well.com.br"})
		@GetMapping(value = "/{id}",
				produces =  { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.APPLICATION_YML})
		@Operation(summary = "Find a Person", 
			description = "Find a Person",
			tags = {"People"},
			responses = {
				@ApiResponse(description = "Sucess", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = PersonVO.class ))
			),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content ),	
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content ),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content ),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content )
			})
		public PersonVO findById( @PathVariable(value = "id") Long id) {
				return service.findById(id);			
		}
		
		
		@PatchMapping(value = "/{id}",
				produces =  { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.APPLICATION_YML})
		@Operation(summary = "disabling  a Person", 
			description = "disabling a Person",
			tags = {"People"},
			responses = {
				@ApiResponse(description = "Sucess", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = PersonVO.class ))
			),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content ),	
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content ),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content ),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content )
			})
		public PersonVO disablePerson( @PathVariable(value = "id")Long id ) {
				return service.disablePerson(id);			
		}
		
		
		
		
		@CrossOrigin(origins = {"http://localhost8080","http://well.com.br"})
		@PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.APPLICATION_YML},
					 produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.APPLICATION_YML})
		@Operation(summary = "Adds a new Person",
			description = "Adds a new person  by passing  in a Jason XML  or YML Representation of the pe",
			tags = {"People"},
			responses = {
				@ApiResponse(description = "Sucess", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = PersonVO.class ))
			),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content ),	
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content ),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content )
			})
		
		public PersonVO create( @RequestBody PersonVO person){
				return service.create(person);	
		}
		
		
		
		@PutMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.APPLICATION_YML},
					produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.APPLICATION_YML})
		@Operation(summary = "Update a Person",
			description = "Update a person  by passing  in a Jason XML  or YML Representation of the pe",
			tags = {"People"},
			responses = {
				@ApiResponse(description = "Updated", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = PersonVO.class ))
			),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content ),	
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content ),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content ),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content )
			})
		public PersonVO update( @RequestBody PersonVO person){
				return service.update(person);			
		}
		
		
		
		@DeleteMapping("/{id}")
		@Operation(summary = "Delete a Person",
			description = "Delete a person  by passing  in a Jason XML  or YML Representation of the pe",
			tags = {"People"},
			responses = {
				@ApiResponse(description = "No Content", responseCode = "200",content = @Content),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content ),	
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content ),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content ),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content )
				})
		public ResponseEntity<?> delete( @PathVariable(value = "id") Long id) {
				service.delete(id);
				return ResponseEntity.noContent().build();
		}	
}
		
			
		
		
				
		
		
