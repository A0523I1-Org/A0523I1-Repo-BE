package com.example.bebuildingmanagement.service.implement;

import com.example.bebuildingmanagement.repository.IFloorRepository;
import com.example.bebuildingmanagement.service.interfaces.IFloorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FloorServiceImpl implements IFloorService {
    IFloorRepository iFloorRepository;


}
