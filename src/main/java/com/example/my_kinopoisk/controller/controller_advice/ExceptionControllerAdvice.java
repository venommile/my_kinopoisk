package com.example.my_kinopoisk.controller.controller_advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

@ControllerAdvice
public class ExceptionControllerAdvice implements ProblemHandling, SecurityAdviceTrait {


}
