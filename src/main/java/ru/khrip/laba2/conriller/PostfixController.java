package ru.khrip.laba2.conriller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.khrip.laba2.Postfix;

@Controller
@RequestMapping("/postfix")
public class PostfixController {
    private Postfix postfix;
    @Autowired
    public PostfixController(Postfix postfix)
    {
        this.postfix=postfix;
    }
    @GetMapping()
    public String showPage()
    {
        return "postfix/showPage";
    }

    @PostMapping()
    public String change(@RequestParam(value = "expression", required = false) String expression){
        if(expression!=null) {
            postfix.setIsRes(true);
            postfix.setResult( postfix.eval(expression));
            if(!postfix.getCorrect())
                return "postfix/false";
        }

        return "postfix/showPage";
    }
    @GetMapping("/result")
    public String result(Model model)
    {
        if(postfix.getIsRes())
        model.addAttribute("number", postfix.getResult());
        return "postfix/result";
    }



}
