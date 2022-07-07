package com.azakamu.attendancemanager.adapters.web.controller;

import com.azakamu.attendancemanager.application.services.ExamService;
import com.azakamu.attendancemanager.application.services.TutorService;
import com.azakamu.attendancemanager.domain.entities.Exam;
import com.azakamu.attendancemanager.domain.entities.Student;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author janlingen
 */
@Secured({"ROLE_TUTOR", "ROLE_ADMIN"})
@Controller()
@Transactional
public class TutorController {

  private final TutorService tutorService;
  private final ExamService examService;

  public TutorController(TutorService tutorService, ExamService examService) {
    this.tutorService = tutorService;
    this.examService = examService;
  }

  @GetMapping("/tutor")
  public String tutor(Model model) {
    List<Student> students = tutorService.getAllStudents();
    model.addAttribute("students", students);
    return "tutor";
  }

  @GetMapping("/display-exemptions/{githubId}")
  public String displayExemptions(Model model, @PathVariable String githubId) {
    Student student = tutorService.getExistingStudent(githubId);
    List<Exam> exams = examService.getExamsByIds(student.getExamIds());
    model.addAttribute("student", student);
    model.addAttribute("exams", exams);
    return "display-exemptions";
  }
}