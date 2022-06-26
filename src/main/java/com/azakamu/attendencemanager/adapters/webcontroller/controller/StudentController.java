package com.azakamu.attendencemanager.adapters.webcontroller;

import com.azakamu.attendencemanager.adapters.webcontroller.datainput.VacationForm;
import com.azakamu.attendencemanager.application.services.ExamService;
import com.azakamu.attendencemanager.application.services.StudentService;
import com.azakamu.attendencemanager.application.services.helper.TimeService;
import com.azakamu.attendencemanager.application.validators.VacationValidator;
import com.azakamu.attendencemanager.domain.entities.Exam;
import com.azakamu.attendencemanager.domain.entities.Student;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Secured("ROLE_STUDENT")
@Transactional
public class StudentController {

  private final StudentService studentService;
  private final ExamService examService;
  private final TimeService timeService;
  private final List<String> vacationTimes =
      List.of(
          "09:45", "10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45", "12:00",
          "12:15", "12:30", "12:45", "13:00", "13:15");
  private final List<String> examTimes =
      List.of(
          "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30",
          "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30",
          "19:00");

  public StudentController(StudentService studentService, ExamService examService,
      TimeService timeService) {
    this.studentService = studentService;
    this.examService = examService;
    this.timeService = timeService;
  }

  @ModelAttribute("githubId")
  private static String getGithubId(@AuthenticationPrincipal OAuth2User principal) {
    return principal == null ? "unknown" : principal.getAttribute("id").toString();
  }

  @ModelAttribute("githubName")
  private static String getGithubName(@AuthenticationPrincipal OAuth2User principal) {
    return principal == null ? "unknown" : principal.getName();
  }

  @GetMapping("/")
  public String index() {
    return "redirect:/student";
  }

  @GetMapping("/student")
  public String student(Model model, @ModelAttribute("githubName") String githubName,
      @ModelAttribute("githubId") String githubId) {
    Student student = studentService.getStudent(githubName, githubId);
    List<Exam> exams = examService.getExamsByIds(student.getExamIds());
    model.addAttribute("student", student);
    model.addAttribute("exams", exams);
    model.addAttribute("maxVacTime", timeService.getVacationTime());
    return "student";
  }

  @PostMapping("/cancelVacation")
  public String cancelVacation(VacationForm form, @ModelAttribute("githubName") String githubName,
      @ModelAttribute("githubId") String githubId) {
    studentService.cancelVacation(githubName, githubId, form.date(), form.start(), form.end(),
        form.reason());
    return "redirect:/";
  }

  @PostMapping("/cancelExam")
  public String cancelExam(Long exam, @ModelAttribute("githubName") String githubName,
      @ModelAttribute("githubId") String githubId) {
    studentService.cancelExam(githubName, githubId, exam);
    return "redirect:/";
  }

  @GetMapping("/vacationregistration")
  public String vacationregistration(Model model) {
    model.addAttribute("times", vacationTimes);
    VacationValidator result = (VacationValidator) model.getAttribute("result");
    if (result != null) {
      model.addAttribute("errorMsg", result.getMsg());
    }
    return "vacationregistration";
  }

}