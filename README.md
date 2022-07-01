# Attendence-Manager

An application with a web user interface to manage project
participant absences in a university environment where students
need to build daily attendance into their flexible schedule,
which may be filled with exams or other appointments away from home.

## Table of Contents

- [Installation and configuration](#installation-and-configuration)
- [Different stakeholder views](#different-stakeholder-views)
- [Stakeholder requirements](#stakeholder-requirements)
- [Vacation and Exam enrollment logic](#vacation-and-exam-enrollment-logic)
- [Architectural overview](#architectural-overview)

## Installation and configuration

## Different stakeholder views

### Student view

![Screenshot](misc/student_view/student1.png)
![Screenshot](misc/student_view/student2.png)
![Screenshot](misc/student_view/student3.png)
![Screenshot](misc/student_view/student4.png)

### Tutor view

![Screenshot](misc/tutor_view/tutor1.png)
![Screenshot](misc/tutor_view/tutor2.png)

### Admin view

![Screenshot](misc/admin_view/admin1.png)

## Stakeholder requirements

### Student

- Students use the application to enter planned absences. They can enter into the application when
  they want to write exams or when they want to take vacation. They get an overview of their
  remaining vacation time, their vacations and exams in general.

### Tutor

- Tutors are responsible for monitoring student attendance and contacting an administrator if a
  student is unlawfully absent. For this purpose, they can see when students are absent. The
  application is designed to provide tutors with a detailed overview of scheduled absences.

### Admin

- Admins get an overview of log messages which are generated when a student performs an action for
  himself in the system. They also have access to the functionalities of a tutor.

## Vacation and exam enrollment logic

## Architectural overview
