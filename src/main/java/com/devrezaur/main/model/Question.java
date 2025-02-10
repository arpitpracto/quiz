package com.devrezaur.main.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "questions")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer questionId;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private String optionA;

	@Column(nullable = false)
	private String optionB;

	@Column(nullable = false)
	private String optionC;

	@Column(nullable = false)
	private String optionD;

	@Column(nullable = false)
	private String correctAnswer;

	// Many-to-Many Relationship with Test
	@ManyToMany(mappedBy = "questions")  // Inverse relationship
	private Set<Test> tests = new HashSet<>();

	// Constructors
	public Question() {}

	public Question(String description,String optionA,String optionB,String optionC,String optionD ,String correctAnswer) {
		this.description = description;
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionC = optionC;
		this.optionD = optionD;
		this.correctAnswer = correctAnswer;

	}

	public Integer getQuestionId() {
		return questionId;
	}



	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	// Getters and Setters

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOptionA() {
		return optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getOptionC() {
		return optionC;
	}

	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public String getOptionD() {
		return optionD;
	}

	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public Set<Test> getTests() {
		return tests;
	}

	public void setTests(Set<Test> tests) {
		this.tests = tests;
	}
}