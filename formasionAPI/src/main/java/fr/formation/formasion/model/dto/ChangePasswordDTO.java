package fr.formation.formasion.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDTO {
	private String username;
	private String oldPassword;
	private String newPassword;
}
