package com.pawmart.DTO.Address;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressResponse{
    private Long id;
    private String receiverName;
    private String phone;
    private String province;
    private String district;
    private String ward;
    private String street;
    private Boolean isDefault;
}
