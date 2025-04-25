package com.Integration.hubstaff.Service;

import com.Integration.hubstaff.DTO.ActivityDTO;
import com.Integration.hubstaff.DTO.AppActivityDTO;
import com.Integration.hubstaff.DTO.AppDTO;
import com.Integration.hubstaff.Exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AppActivityService {


   public  void getNewAppActivity();

   public ActivityDTO timeSpent(AppActivityDTO appActivityDTO) throws EntityNotFoundException;

   //apps that user uses
   public List<AppDTO> getApps(Integer userId) throws EntityNotFoundException;
}
