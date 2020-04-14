package com.github.paycraft.cmsauthentication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Authenticate {

	private String onBoardingToken;
	private String deviceId;
}
