package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.CarRequestDto;
import com.example.carsharingservice.dto.response.CarResponseDto;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.service.CarService;
import com.example.carsharingservice.service.mapper.DtoMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarControllerTest {
    private static final Long ID = 1L;
    @Mock
    private CarService carService;
    @Mock
    private DtoMapper<CarRequestDto, CarResponseDto, Car> carMapper;
    @Mock
    private CarRequestDto requestDto;
    @Mock
    private Car car;
    @Mock
    private Car secondCar;
    @Mock
    private Car createdCar;
    @Mock
    private CarResponseDto responseDto;
    @Mock
    private CarResponseDto secondResponseDto;
    @InjectMocks
    private CarController carController;

    @Test
    void testAdd_isOk() {
        when(carMapper.mapToModel(requestDto)).thenReturn(car);
        when(carService.add(car)).thenReturn(createdCar);
        when(carMapper.mapToDto(createdCar)).thenReturn(responseDto);

        CarResponseDto response = carController.add(requestDto);

        assertEquals(responseDto, response);
    }

    @Test
    void testGetAll_isOk() {
        when(carService.getAll()).thenReturn(List.of(car, secondCar));
        when(carMapper.mapToDto(car)).thenReturn(responseDto);
        when(carMapper.mapToDto(secondCar)).thenReturn(secondResponseDto);

        List<CarResponseDto> responseDtos = carController.getAll();

        assertEquals(List.of(responseDto, secondResponseDto), responseDtos);
    }

    @Test
    void testGet_isOk() {
        when(carService.get(ID)).thenReturn(car);
        when(carMapper.mapToDto(car)).thenReturn(responseDto);

        CarResponseDto actualResponseDto = carController.get(ID);

        assertEquals(responseDto, actualResponseDto);
    }

    @Test
    void testUpdate_isOk() {
        when(carService.update(ID, car)).thenReturn(createdCar);
        when(carMapper.mapToDto(createdCar)).thenReturn(responseDto);
        when(carMapper.mapToModel(requestDto)).thenReturn(car);

        CarResponseDto actualResponseDto = carController.update(ID, requestDto);

        assertEquals(responseDto, actualResponseDto);
    }
}
