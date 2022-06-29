package com.azakamu.attendencemanager.adapters.webcontroller.controller;

import javax.transaction.Transactional;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Secured("ROLE_TUTOR")
@Controller
@Transactional
public class TutorController {

  @ModelAttribute("githubId")
  private static String getGithubId(@AuthenticationPrincipal OAuth2User principal) {
    return principal == null ? "unknown" : principal.getAttribute("id").toString();
  }

  @ModelAttribute("githubName")
  private static String getGithubName(@AuthenticationPrincipal OAuth2User principal) {
    return principal == null ? "unknown" : principal.getName();
  }

  @GetMapping("/tutor")
  public String tutor() {
    return "tutor";
  }
}