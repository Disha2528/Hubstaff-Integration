package com.Integration.hubstaff.Controller;

import com.Integration.hubstaff.DTO.UserDTO;
import com.Integration.hubstaff.Exception.EntityNotFoundException;
import com.Integration.hubstaff.Service.UserService;
import com.Integration.hubstaff.Util.MessageUtil;
import com.Integration.hubstaff.Util.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageUtil messageUtil;

    @GetMapping("/get/{orgId}")
    public ResponseEntity<ResponseHandler> getUsers(@PathVariable  Integer orgId){
        try{
            List<UserDTO> userDTOList= userService.getUsersByOrganization(orgId).getUserList();
            ResponseHandler response = new ResponseHandler(messageUtil.getMessage("user.get.success"), HttpStatus.OK.value(), true, "User", userDTOList);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            log.error("UserList",e);
            ResponseHandler response = new ResponseHandler(e.getMessage(), 400, false, "User", null);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            log.error("UserList",e);
            ResponseHandler response = new ResponseHandler(e.getMessage(), 400, false, "User", null);
            return ResponseEntity.ok(response);
        }
    }
//
//    //testing scheduler
//    @GetMapping("/sync")
//    public ResponseEntity<String> syncUsers() {
//        userService.getUsers();
//        return ResponseEntity.ok("User sync triggered successfully");
//    }
//


}
