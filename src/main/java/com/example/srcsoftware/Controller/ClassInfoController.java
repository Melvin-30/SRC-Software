package com.example.srcsoftware.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.srcsoftware.Entity.ClassInfo;
import com.example.srcsoftware.Service.ClassInfoService;

import java.util.List;

@RestController
public class ClassInfoController {

    private final ClassInfoService classInfoService;

    public ClassInfoController(ClassInfoService classInfoService){
        this.classInfoService = classInfoService;
    }

    @GetMapping("/classes")
    public List<ClassInfo> getAllClasses(){
        return classInfoService.getAllClasses();
    }
}
