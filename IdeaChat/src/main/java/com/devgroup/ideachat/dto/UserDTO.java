package com.devgroup.ideachat.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	@NotEmpty(message = "Please enter firstName")
	private String firstName;

	@NotEmpty(message = "Please enter lastName")
	private String lastName;

	@Email(message = "Enter valid emailId")
	private String email;

	@Size(min = 10, max = 10, message = "Enter 10 digit phone number only")
	private String phone;

	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Please enter Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character")
	private String password;

}
