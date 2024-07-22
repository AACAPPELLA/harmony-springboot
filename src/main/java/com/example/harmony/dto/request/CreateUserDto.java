package com.example.harmony.dto.request;

import com.example.harmony.type.EDisabled;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateUserDto(String serialId, String password, String name, String phoneNumber, String email,@JsonProperty("eDisabled") EDisabled eDisabled) {
}
