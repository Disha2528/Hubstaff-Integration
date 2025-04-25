package com.Integration.hubstaff.Controller;

import com.Integration.hubstaff.DTO.ActivityDTO;
import com.Integration.hubstaff.DTO.AppActivityDTO;
import com.Integration.hubstaff.DTO.AppDTO;
import com.Integration.hubstaff.Service.AppActivityService;
import com.Integration.hubstaff.Util.MessageUtil;
import com.Integration.hubstaff.Util.ResponseHandler;
import com.Integration.hubstaff.Groups.Onfetch;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appActivity")
public class AppActivityController {

    @Autowired
    private AppActivityService appActivityService;

    @Autowired
    private MessageUtil messageUtil;


    @PostMapping("/timeSpent")
    public ResponseEntity<ResponseHandler> getTimeSpent( @RequestBody @Validated(Onfetch.class) AppActivityDTO appActivityDTO){
        try{
            ActivityDTO activityDTO=appActivityService.timeSpent(appActivityDTO);
            ResponseHandler response= new ResponseHandler(messageUtil.getMessage("AppActivity.fetch.success"), HttpStatus.OK.value(), true, "AppActivity", activityDTO);
            return ResponseEntity.ok(response);
            //add Entity not found exception
        }catch (Exception e){
            ResponseHandler response= new ResponseHandler(messageUtil.getMessage(e.getMessage()),400, false, "AppActivity", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/getUserApps/{userId}")
    public ResponseEntity<ResponseHandler> getuserApps(@PathVariable @Valid Integer userId){
        try{
            List<AppDTO> appDTOList= appActivityService.getApps(userId);
            ResponseHandler response= new ResponseHandler(messageUtil.getMessage("Apps.fetch.success"), HttpStatus.OK.value(), true, "Apps", appDTOList);
            return ResponseEntity.ok(response);
            //add Entity not found exception
        }catch (Exception e){
            ResponseHandler response= new ResponseHandler(e.getMessage(), 400, false, "Apps", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

//    //testing scheduler
//    @GetMapping("/run-scheduler")
//    public ResponseEntity<ResponseHandler> runScheduler() {
//        try {
//            appActivityService.getNewAppActivity();
//            ResponseHandler response = new ResponseHandler(
//                    messageUtil.getMessage("Scheduler.run.success"),
//                    HttpStatus.OK.value(),
//                    true,
//                    "Scheduler",
//                    "Scheduler executed successfully"
//            );
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            ResponseHandler response = new ResponseHandler(
//                    messageUtil.getMessage(e.getMessage()),
//                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                    true,
//                    "Scheduler",
//                    null
//            );
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }



}
