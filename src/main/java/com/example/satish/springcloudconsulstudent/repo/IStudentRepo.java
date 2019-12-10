package com.example.satish.springcloudconsulstudent.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.satish.springcloudconsulstudent.model.Student;


@Repository
@Transactional
public interface IStudentRepo extends MongoRepository<Student, String> {

	List<Student> findByCourseId(String cid);

}


