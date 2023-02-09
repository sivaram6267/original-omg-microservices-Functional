//package com.example.demo.entity;
//
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import javax.persistence.OneToMany;
//
//import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.annotations.Parameter;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
////@Entity
////@Table(name="registration")
//public class RegistrationEntity {
//
//
//	
//	private String user_id;
//	private String firstName;
//	private String lastName;
//	private String email;
//	private String dob;
//	private String phoneNumber;
//	private String userName;
//	private String password; 
//	private String gender;
//	
//	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
//	@JoinTable(name="user_role", joinColumns = @JoinColumn(name="user_Id"), inverseJoinColumns = @JoinColumn(name="role_id"))
//	private List<Authorities> authorities;
//
//}
