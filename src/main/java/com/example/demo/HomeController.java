package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @RequestMapping("/")
    public String courseList(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        model.addAttribute("subjects", subjectRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String courseForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("subject", subjectRepository.findAll());
        return "courseform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Course course, BindingResult result, Model model) {
        if (result.hasErrors()) {
           model.addAttribute("subjects", subjectRepository.findAll());
            return "courseform";
        }
        courseRepository.save(course);
        return "redirect:/";
    }
    @RequestMapping("/addsubject")
    public String processSubject( Model model){
//        if (result.hasErrors()) {
//            return "subject";
//        }
        model.addAttribute("subject" , new Subject());
        return "subjectform";
    }

    @PostMapping("/processSubject")
    public String processForm(@Valid Subject subject, BindingResult result, Model model) {
        if (result.hasErrors()) {
//            model.addAttribute("subjects", subjectRepository.findAll());
            return "subjectform";
        }
        if (subjectRepository.findBysubjectName(subject.getSubjectName()) != null){

            model.addAttribute("message", "You already have a subject");
            return "subjectform";
        }
        subjectRepository.save(subject);
        return "redirect:/";
    }


}
