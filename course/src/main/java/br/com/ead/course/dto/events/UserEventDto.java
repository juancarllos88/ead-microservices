package br.com.ead.course.dto.events;

import java.util.UUID;

import br.com.ead.course.enums.ActionType;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserEventDto {
	private UUID Id;
	private String userName;
	private String email;
	private String fullName;
	private String status;
	private String type;
	private String phoneNumber;
	private String cpf;
	private String imageUrl;
	private ActionType actionType;
}
