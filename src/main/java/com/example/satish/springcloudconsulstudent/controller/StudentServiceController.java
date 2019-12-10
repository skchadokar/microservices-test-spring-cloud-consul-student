package com.example.satish.springcloudconsulstudent.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.satish.springcloudconsulstudent.model.Student;
import com.example.satish.springcloudconsulstudent.repo.IStudentRepo;
import com.example.satish.springcloudconsulstudent.response.ServiceResponse;

@RestController
public class StudentServiceController {

	@Autowired
	IStudentRepo iStudentRepo;

	@GetMapping(path = "/students", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Student>> getStudents() {
		List<Student> mList = iStudentRepo.findAll();
		return new ResponseEntity<List<Student>>(mList, HttpStatus.OK);

	}
	
	//webclient call
	@GetMapping(path = "/students/{cid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Student>> getStudentByCourseId(@PathVariable String cid) {
		List<Student> mList = iStudentRepo.findByCourseId(cid);
		return new ResponseEntity<List<Student>>(mList, HttpStatus.OK);

	}
	
	@GetMapping(path = "/students/courses/{cid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Student>> updateStudentByCourseId(@PathVariable String cid) {
		List<Student> mList = iStudentRepo.findByCourseId(cid);
		mList.forEach(l->{
			l.setCourseId(null);//update course id
		});
		iStudentRepo.saveAll(mList);//save back to db
		return new ResponseEntity<List<Student>>(mList, HttpStatus.OK);

	}

	@PostMapping(path = "/students", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Student> addStudents(@Valid @RequestBody Student aStudent) {
		Student mStudent = iStudentRepo.save(aStudent);
		return new ResponseEntity<Student>(mStudent, HttpStatus.OK);
	}

	@PostMapping(path = "/students/{sid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ServiceResponse> removeStudents(@PathVariable String sId) {
		ServiceResponse mServiceResponse = new ServiceResponse();
		try {
			iStudentRepo.deleteById(sId);
			mServiceResponse.setIsSuccess(true);
		} catch (Exception e) {
			mServiceResponse.setIsSuccess(true);
		}
		mServiceResponse.setAction("delete-student");
		mServiceResponse.setsId(sId);
		return new ResponseEntity<ServiceResponse>(mServiceResponse, HttpStatus.OK);
	}

}
