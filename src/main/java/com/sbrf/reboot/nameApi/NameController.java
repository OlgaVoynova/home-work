package com.sbrf.reboot.nameApi;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class NameController {
    /**
     * Метод для получения приветствия в виде текста в body
     * @param name имя для приветствия (обязательный параметр)
     * @param model возвращаемая модель для заполнения
     * @return текст Hello, ${name}
     */
    @GetMapping("/user")
    public String getMessage (@RequestParam (name = "name", required = true) String name,
                              Model model) {
        model.addAttribute("name",name);
        return "namePageTemplateTxt";
    }
}
