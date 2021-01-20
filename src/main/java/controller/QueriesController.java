package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import helper.QueriesHelper;

@RequestMapping("queries")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class QueriesController {
	
	@Autowired
	QueriesHelper queriesHelper;
	
	@PostMapping("/readExcel")
	public String readExcel() {
		return queriesHelper.readExcel();
	}

}
