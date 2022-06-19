package com.azakamu.attendencemanager.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

import com.azakamu.attendencemanager.AttendenceManagerApplication;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.GeneralCodingRules;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@AnalyzeClasses(
    packagesOf = AttendenceManagerApplication.class,
    importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchUnitTests {

  @ArchTest
  ArchRule noMembersShouldBeAutowired = GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION;

  @ArchTest
  ArchRule noJodaTime = GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME;

  @ArchTest
  ArchRule noGenericException = GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

  @ArchTest
  ArchRule onion =
      onionArchitecture()
          .withOptionalLayers(true)
          .domainModels("..domain..")
          .applicationServices("..application..")
          .adapter("webcontroller", "..adapters.webcontroller..")
          .adapter("database", "..adapters.database..");

  @ArchTest
  ArchRule fieldsShouldBePrivate =
      fields()
          .that()
          .areDeclaredInClassesThat()
          .areAnnotatedWith(Controller.class)
          .should()
          .bePrivate();

  @ArchTest
  ArchRule serviceClassAnnotation =
      classes()
          .that()
          .areTopLevelClasses()
          .and()
          .areNotInterfaces()
          .and()
          .resideInAPackage("..application.services..")
          .should()
          .beMetaAnnotatedWith(Service.class);

  @ArchTest
  ArchRule controllerClassAnnotation =
      classes()
          .that()
          .areTopLevelClasses()
          .and()
          .areNotInterfaces()
          .and()
          .resideInAPackage("..adapters.webcontroller..")
          .should()
          .beMetaAnnotatedWith(Controller.class);

  @ArchTest
  ArchRule configurationClassesAnnotation =
      classes()
          .that()
          .areTopLevelClasses()
          .and()
          .areNotInterfaces()
          .and()
          .resideInAPackage("..configuration..")
          .should()
          .beMetaAnnotatedWith(Configuration.class);

  @ArchTest
  ArchRule interfaces =
      classes()
          .that()
          .areTopLevelClasses()
          .and()
          .resideInAPackage("..application.repositories..")
          .should()
          .beInterfaces();

  @ArchTest
  ArchRule implementingInterfaces =
      classes()
          .that()
          .areTopLevelClasses()
          .and()
          .resideInAPackage("..adapters.database.implementation")
          .should()
          .implement(JavaClass.Predicates.resideInAPackage("..application.repositories.."));
}
