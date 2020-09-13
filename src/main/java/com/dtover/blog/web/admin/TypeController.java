package com.dtover.blog.web.admin;

import com.dtover.blog.po.Type;
import com.dtover.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    // Show type
    @GetMapping("/types")
    public String list(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                                   Pageable pageable, Model model){

        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    // Add type
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    // add tag
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type t1 = typeService.getTypeByName(type.getName());
        if(t1 != null){
            result.rejectValue("name", "nameError", "No repeat !");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type t2 = typeService.saveType(type);
        if(t2 == null){
            attributes.addFlashAttribute("message", "Operation Failed!");
        }else{
            attributes.addFlashAttribute("message", "Operation Successed!");
        }
        return "redirect:/admin/types";
    }

    // update type
    @PostMapping("/types/{id}")
    public String post(@Valid Type type, BindingResult result,
                       @PathVariable Long id, RedirectAttributes attributes){
        Type t1 = typeService.getTypeByName(type.getName());
        if(t1 != null){
            result.rejectValue("name", "nameError", "No repeat !");
        }
        Type t2 = typeService.updateType(id, type);
        if(t2 == null){
            attributes.addFlashAttribute("message", "Update Failed!");
        }else{
            attributes.addFlashAttribute("message", "Update Succeed!");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        return "redirect:/admin/types";
    }

    // Delete type
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        attributes.addFlashAttribute("message", "Delete Succeed !");
        typeService.deleteType(id);
        return "redirect:/admin/types";
    }
}
