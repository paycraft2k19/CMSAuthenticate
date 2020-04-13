package com.paycraft.cmsauthentication.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "device")
public class Device {

	private String deviceId;
	private String name;
	private String onBoardingToken;
}
