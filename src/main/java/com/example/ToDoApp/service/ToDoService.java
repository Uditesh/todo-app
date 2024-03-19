package com.example.ToDoApp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ToDoApp.model.ToDo;
import com.example.ToDoApp.repository.ToDoRepo;

@Service
public class ToDoService {

	@Autowired
	ToDoRepo repo;
	public List<ToDo> getAllToDoItems(){
		ArrayList<ToDo> todolist = new ArrayList<>();
		repo.findAll().forEach(todo -> todolist.add(todo));
		return todolist;
	}
	
	public ToDo getToDoById(Long id) {
		return repo.findById(id).get();
	}
	
	public boolean updateStatus(Long id) {
		ToDo todo = getToDoById(id);
		todo.setStatus("Completed");
		return saveOrUpdateToDoItem(todo);
	}
	
	public boolean saveOrUpdateToDoItem(ToDo todo) {
		ToDo updatedObj = repo.save(todo);
		if(getToDoById(updatedObj.getId()) != null) {
			return true;
		}
		return false;
	}
	
	public boolean deleteToDoItem(Long id) {
		repo.deleteById(id);
		// repo.findById(id) == null wont work as findById is Optional is never null
		if(repo.findById(id).isEmpty()) {
			return true;
		}
		return false;
	}
	
}
