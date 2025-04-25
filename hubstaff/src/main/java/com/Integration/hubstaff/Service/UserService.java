package com.Integration.hubstaff.Service;

import com.Integration.hubstaff.DTO.UserDTO;
import com.Integration.hubstaff.DTO.UserResponse;
import com.Integration.hubstaff.Entity.User;
import com.Integration.hubstaff.Exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public UserResponse getUsers();

    public UserDTO getUSerById(Integer userId);

    public UserResponse getUsersByOrganization(Integer OrganizationId) throws EntityNotFoundException;
}
