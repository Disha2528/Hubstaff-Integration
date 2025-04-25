package com.Integration.hubstaff.Controller;

import com.Integration.hubstaff.DTO.OrganizationDTO;
import com.Integration.hubstaff.Service.OrganizationService;
import com.Integration.hubstaff.Util.MessageUtil;
import com.Integration.hubstaff.Util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private MessageUtil messageUtil;


    @GetMapping("/fetch")
    public ResponseEntity<ResponseHandler> fetchOrganizations(){
        try{
            List<OrganizationDTO> orgs= organizationService.getOrgList().getOrgList();
            ResponseHandler response= new ResponseHandler(messageUtil.getMessage("organization.fetch.success"),
                    HttpStatus.OK.value(), true, "Organizations", orgs);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseHandler response= new ResponseHandler(e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), false, "Organizations", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}
