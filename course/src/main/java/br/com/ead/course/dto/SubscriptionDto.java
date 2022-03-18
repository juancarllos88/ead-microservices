package br.com.ead.course.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SubscriptionDto implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@NotNull
	private UUID userId;

}
