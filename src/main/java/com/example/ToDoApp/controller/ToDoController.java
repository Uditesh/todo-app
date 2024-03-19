package com.example.ToDoApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.ToDoApp.model.ToDo;
import com.example.ToDoApp.service.ToDoService;

import org.springframework.ui.Model;

@Controller
public class ToDoController {

	@Autowired
	private ToDoService todoService;
	
	@GetMapping({"/","/viewToDoList"})
	public String viewAllToDoItems(Model model, @ModelAttribute("message") String message) {
		model.addAttribute("list", todoService.getAllToDoItems());
		model.addAttribute("message", message);
		return "ViewToDoList";
	}
	
	@GetMapping("/updateToDoStatus/{id}")
	public String updateToDoStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		if(todoService.updateStatus(id)) {
			redirectAttributes.addFlashAttribute("message", "Update Success");
			return "redirect:/viewToDoList";
		}
		redirectAttributes.addFlashAttribute("message", "Update Failure");
		return "redirect:/viewToDoList";
	}
	
	@GetMapping("/addToDoItem")
	public String addToDoItem(Model model) {
		model.addAttribute("todo", new ToDo());
		return "AddToDoItem";
	}
	
	@PostMapping("/saveToDoItem")
	public String saveToDoItem(ToDo todo, RedirectAttributes redirectAttributes) {
		if(todoService.saveOrUpdateToDoItem(todo)) {
			redirectAttributes.addFlashAttribute("message", "Item was saved successfully");
			return "redirect:/viewToDoList";
		}
		redirectAttributes.addFlashAttribute("message", "Item was not saved successfully");
		return "redirect:/addToDoItem";
	}
	
	@GetMapping("/editToDoItem/{id}")
	public String editToDoItem(@PathVariable Long id, Model model) {
		model.addAttribute("todo", todoService.getToDoById(id));
		return "EditToDoItem";
	}
	
	@PostMapping("/editSaveToDoItem")
	public String editSaveToDoItem(ToDo todo, RedirectAttributes redirectAttributes) {
		if(todoService.saveOrUpdateToDoItem(todo)) {
			redirectAttributes.addFlashAttribute("message", "Item was edited successfully");
			return "redirect:/viewToDoList";
		}
		redirectAttributes.addFlashAttribute("message", "Item was not edited successfully");
		return "redirect:/editToDoItem/"+todo.getId();
	}
	
	@GetMapping("/deleteToDoItem/{id}")
	public String deleteToDoItem(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		if(todoService.deleteToDoItem(id)) {
			redirectAttributes.addFlashAttribute("message", "Item deleted successfully");
			return "redirect:/viewToDoList";
		}
		redirectAttributes.addFlashAttribute("message", "Item was not deleted successfully");
		return "redirect:/viewToDoList";
	}
}
