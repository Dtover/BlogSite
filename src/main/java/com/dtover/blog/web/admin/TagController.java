package com.dtover.blog.web.admin;

import com.dtover.blog.po.Tag;
import com.dtover.blog.service.TagService;
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
public class TagController {

    @Autowired
    private TagService tagService;

    // Show tags
    @GetMapping("/tags")
    public String list(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                       Pageable pageable, Model model){
        model.addAttribute("page", tagService.listTag(pageable));
        return "/admin/tags";
    }

    // Swicth to tag page
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag", new Tag());
        return "/admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag", tagService.getTag(id));
        return "/admin/tags-input";
    }

    // Add tag
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        // Judge if repeat !
        Tag t1 = tagService.getTagByName(tag.getName());
        if(t1 != null){
            result.rejectValue("name", "nameError", "No repeat !");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        // Judge if null
        Tag t2 = tagService.saveTag(tag);
        if(t2 == null){
            attributes.addFlashAttribute("message", "Operation failed !");
        }else{
            attributes.addFlashAttribute("message", "Operation succeed !");
        }
        return "redirect:/admin/tags";
    }

    // Update tag
    @PostMapping("/tags/{id}")
    public String post(@Valid Tag tag, BindingResult result,
                       @PathVariable Long id,
                       RedirectAttributes attributes){
        // Judge if repeat !
        Tag t1 = tagService.getTagByName(tag.getName());
        if(t1 != null){
            result.rejectValue("name", "nameError", "No repeat !");
        }
        // Judge if null
        Tag t2 = tagService.updateTag(id, tag);
        if(t2 == null && t1 != null){
            attributes.addFlashAttribute("message", "Operation failed !");
        }else{
            attributes.addFlashAttribute("message", "Operation succeed !");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        return "redirect:/admin/tags";
    }

    // Delete tag
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        attributes.addFlashAttribute("message", "Delete Succeed !");
        tagService.deleteTag(id);
        return "redirect:/admin/tags";
    }
}
