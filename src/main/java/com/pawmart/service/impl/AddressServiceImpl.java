package com.pawmart.service.impl;

import com.pawmart.DTO.Address.AddressRequest;
import com.pawmart.DTO.Address.AddressResponse;
import com.pawmart.entity.Address;
import com.pawmart.entity.User;
import com.pawmart.exception.AppException;
import com.pawmart.repository.AddressRepository;
import com.pawmart.repository.UserRepository;
import com.pawmart.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    private AddressResponse toResponse(Address address) {

        AddressResponse response = new AddressResponse();

        response.setId(address.getId());
        response.setReceiverName(address.getReceiverName());
        response.setPhone(address.getPhone());
        response.setProvince(address.getProvince());
        response.setDistrict(address.getDistrict());
        response.setWard(address.getWard());
        response.setStreet(address.getStreet());
        response.setIsDefault(address.getIsDefault());

        return response;
    }

    @Override
    public AddressResponse create( Long userId, AddressRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "User not found"));
        if (Boolean.TRUE.equals(request.getIsDefault())) {
            addressRepository.findByUserId(userId)
                    .forEach(address -> address.setIsDefault(false));
        }
        Address address = new Address();

        address.setUser(user);
        address.setReceiverName(request.getReceiverName());
        address.setPhone(request.getPhone());
        address.setProvince(request.getProvince());
        address.setDistrict(request.getDistrict());
        address.setWard(request.getWard());
        address.setStreet(request.getStreet());
        address.setIsDefault(request.getIsDefault());

        return toResponse(addressRepository.save(address));
    }

    @Override
    public AddressResponse update(Long userId, Long addressId, AddressRequest request) {

        Address address = addressRepository
                .findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Address not found"));

        if (Boolean.TRUE.equals(request.getIsDefault())) {
            addressRepository.findByUserId(userId)
                    .forEach(item -> item.setIsDefault(false));
        }

        address.setReceiverName(request.getReceiverName());
        address.setPhone(request.getPhone());
        address.setProvince(request.getProvince());
        address.setDistrict(request.getDistrict());
        address.setWard(request.getWard());
        address.setStreet(request.getStreet());
        address.setIsDefault(request.getIsDefault());

        return toResponse(addressRepository.save(address));
    }

    @Override
    public void delete(Long userId,Long addressId) {
        Address address = addressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Address not found"));
        addressRepository.delete(address);
    }

    @Override
    public List<AddressResponse> getAll(Long userId) {
        return addressRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }
    @Override
    public AddressResponse getDefault(Long userId) {

        Address address = addressRepository
                .findByUserIdAndIsDefaultTrue(userId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Default address not found"));

        return toResponse(address);
    }

    @Override
    public void setDefault(Long userId, Long addressId) {
        Address address = addressRepository
                .findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Address not found"));
        addressRepository.findByUserId(userId)
                .forEach(item -> item.setIsDefault(false));
        address.setIsDefault(true);
        addressRepository.save(address);
    }
}
