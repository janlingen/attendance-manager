package com.azakamu.attendancemanager.adapters.web.controller;

import com.azakamu.attendancemanager.adapters.web.datainput.ExamForm;
import com.azakamu.attendancemanager.adapters.web.datainput.VacationForm;
import com.azakamu.attendancemanager.application.services.ExamService;
import com.azakamu.attendancemanager.application.services.StudentService;
import com.azakamu.attendancemanager.application.services.helper.TimeService;
import com.azakamu.attendancemanager.application.validators.ExamValidator;
import com.azakamu.attendancemanager.application.validators.VacationValidator;
import com.azakamu.attendancemanager.domain.entities.Exam;
import com.azakamu.attendancemanager.domain.entities.Student;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author janlingen
 */
@Controller()
@Secured("ROLE_STUDENT")
@Transactional
public class StudentController {

  private final StudentService studentService;
  private final ExamService examService;
  private final TimeService timeService;

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

  @GetMapping("/vacation-enrollment")
  public String vacationEnrollment(Model model) {
    model.addAttribute("times", timeService.getTimefractions());
    VacationValidator result = (VacationValidator) model.getAttribute("result");
    if (result != null) {
      model.addAttribute("errorMsg", result.getMsg());
    }
    return "vacation-enrollment";
  }

  @GetMapping("/exam-enrollment")
  public String examEnrollment(Model model) {
    model.addAttribute("exams", examService.getUpcommingExams());
    return "exam-enrollment";
  }

  @GetMapping("/exam-creation")
  public String examCreation(Model model) {
    model.addAttribute("times", timeService.getTimefractions());
    ExamValidator result = (ExamValidator) model.getAttribute("result");
    if (result != null) {
      model.addAttribute("errorMsg", result.getMsg());
    }
    return "exam-creation";
  }

  @PostMapping("/cancelVacation")
  public String cancelVacation(VacationForm form,
      @ModelAttribute("githubName") String githubName,
      @ModelAttribute("githubId") String githubId) {
    studentService.cancelVacation(githubName, githubId, form.date(), form.start(),
        form.end(),
        form.reason());
    return "redirect:/";
  }

  @PostMapping("/cancelExam")
  public String cancelExam(Long exam, @ModelAttribute("githubName") String githubName,
      @ModelAttribute("githubId") String githubId) {
    studentService.cancelExam(githubName, githubId, exam);
    return "redirect:/";
  }

  @PostMapping("/enrollVacation")
  public String enrollVacation(
      RedirectAttributes redirectAttributes, VacationForm vacationForm,
      @ModelAttribute("githubName") String githubName,
      @ModelAttribute("githubId") String githubId) {
    VacationValidator result =
        studentService.enrollVacation(githubName, githubId, vacationForm.date(),
            vacationForm.start(), vacationForm.end(),
            vacationForm.reason());
    if (result == VacationValidator.SUCCESS) {
      return "redirect:/";
    } else {
      redirectAttributes.addFlashAttribute("result", result);
      return "redirect:/vacation-enrollment";
    }
  }

  @PostMapping("/enrollExam")
  public String enrollExam(Long examid, @ModelAttribute("githubName") String githubName,
      @ModelAttribute("githubId") String githubId) {
    studentService.enrollExam(githubName, githubId, examid);
    return "redirect:/";
  }

  @PostMapping("/createExam")
  public String createExam(
      RedirectAttributes redirectAttributes, @ModelAttribute("form") ExamForm form) {
    ExamValidator result =
        examService.createExam(
            form.name(),
            form.date(),
            form.start(),
            form.end(),
            form.onlineExam());
    if (result == ExamValidator.SUCCESS) {
      return "redirect:/exam-enrollment";
    } else {
      redirectAttributes.addFlashAttribute("result", result);
      return "redirect:/exam-creation";
    }
  }


}
