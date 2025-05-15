package br.edu.undf.sga_ic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/professor")
@Tag(name = "ProfessorController", description = "Endpoints relacionados a requisições de Professor")
public class ProfessorController {

}