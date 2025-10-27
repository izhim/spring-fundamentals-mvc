package com.jose.curso.springboot.webapp.springboot_web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jose.curso.springboot.webapp.springboot_web.models.dto.ParamDto;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/params")
public class RequestParamController {

    /*  WITH PARAMETER  */
    @GetMapping("/foo")
    public ParamDto foo(@RequestParam(required = false, defaultValue = "mensaje por defecto") String message) {
        ParamDto param = new ParamDto();
        param.setMessage(message);
        return param;
    }

    /*  WITH MANY PARAMETERS  */
    @GetMapping("/bar")
    public ParamDto bar(@RequestParam String text, @RequestParam Integer code) {
        ParamDto params = new ParamDto();
        params.setMessage(text);
        params.setCode(code);
        return params;
    }
    
    /* WITH HTTP SERVLET REQUEST */
    @GetMapping("/request")
    public ParamDto request(HttpServletRequest request) {
        ParamDto param = new ParamDto();
        param.setCode(Integer.parseInt(request.getParameter("code")));
        param.setMessage(request.getParameter("message"));
        return param;
    }
    

}
