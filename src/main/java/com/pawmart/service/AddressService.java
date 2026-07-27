package com.pawmart.service;

import com.pawmart.DTO.Address.AddressRequest;
import com.pawmart.DTO.Address.AddressResponse;

import java.util.List;

public interface AddressService {
    AddressResponse create(Long userId, AddressRequest request);

    AddressResponse update(Long userId, Long addressId, AddressRequest request);

    void delete(Long userId, Long addressId);

    List<AddressResponse> getAll(Long userId);

    AddressResponse getDefault(Long userId);

    void setDefault(Long userId,Long addressId);
}
