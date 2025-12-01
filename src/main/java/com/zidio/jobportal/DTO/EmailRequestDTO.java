package com.zidio.jobportal.DTO;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
/** Dto used to carry email request details from controllers to the emailservices
 * example:
 * to :"navishanthi19@gmail.com"
 * cc : ["ht@example.com]
 * subject: job application status
 * body : congratulations you have been selected for interview
 * attachments: ["c:/dpcs/pffer_letter.pdf:]
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailRequestDTO {
	
	// primary recipent(required)
	@Email(message="Recipent email must be valid")
	@NotBlank(message= "Recipent email is required")
	private String to;
	
	// optional cc(carbon copy) recipents
	private List<@Email String> cc;
	
	//optional BCC(blind carbon copy)
	private List<@Email String> bcc;
	
	//subject and body are required
	@NotBlank(message="subject cannot be empty")
	private String subject;
	
	@NotBlank(message="email body cannot be empty")
    private String body;
	
	//attachments(like resume or pdf attachments)
	private List<String> attachments;
	
	
	
}
