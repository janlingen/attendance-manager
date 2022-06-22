package com.azakamu.attendencemanager.adapters.webcontroller;

import com.azakamu.attendencemanager.application.services.ExamService;
import com.azakamu.attendencemanager.application.services.StudentService;
import org.springframework.stereotype.Controller;

@Controller
public class StudentController {

  private final StudentService studentService;

  private final ExamService examService;

  public StudentController(StudentService studentService, ExamService examService) {
    this.studentService = studentService;
    this.examService = examService;
  }
}
