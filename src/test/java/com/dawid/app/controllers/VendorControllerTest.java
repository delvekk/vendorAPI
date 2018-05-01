package com.dawid.app.controllers;

import com.dawid.app.api.v1.model.VendorDTO;
import com.dawid.app.domain.Vendor;
import com.dawid.app.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static com.dawid.app.controllers.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class VendorControllerTest {

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
    }



    @Test
    public void getAllVendorsTest() throws Exception{
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setName("Andrzej");
        vendor1.setVendor_url("/api/v1/vendors/1");

        VendorDTO vendor2 = new VendorDTO();
        vendor2.setName("Janek");
        vendor2.setVendor_url("/api/v1/vendors/2");

        when(vendorService.getAllVendors()).thenReturn(Arrays.asList(vendor1,vendor2));


        mockMvc.perform(get("/api/v1/vendors")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors",hasSize(2)));

    }

    @Test
    public void getVendorByIdTest() throws Exception{
        VendorDTO vendor = new VendorDTO();
        vendor.setName("Name");
        vendor.setVendor_url("/api/v1/vendors/1");

        when(vendorService.getVendorById(anyLong())).thenReturn(vendor);

        mockMvc.perform(get("/api/v1/vendors/1")
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo("Name")));
    }

    @Test
    public void addNewVendor() throws Exception{

        VendorDTO vendor = new VendorDTO();
        vendor.setName("Name");

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendor.getName());
        returnDTO.setVendor_url("/api/v1/vendors/1");

        when(vendorService.createNewVendor(vendor)).thenReturn(returnDTO);

        mockMvc.perform(post("/api/v1/vendors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Name")))
                .andExpect(jsonPath("$.vendor_url", equalTo("/api/v1/vendors/1")));
    }

    @Test
    public void updateVendorTest() throws Exception{
        VendorDTO vendor = new VendorDTO();
        vendor.setName("Name");

        VendorDTO returnDTo = new VendorDTO();
        returnDTo.setName(vendor.getName());
        returnDTo.setVendor_url("/api/v1/vendors/1");

        when(vendorService.saveCustomerByDTO(anyLong(),any(VendorDTO.class))).thenReturn(returnDTo);

        mockMvc.perform(put("/api/v1/vendors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendor)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name",equalTo("Name")))
                        .andExpect(jsonPath("$.vendor_url",equalTo("/api/v1/vendors/1")));
    }

    @Test
    public void patchVendorTest() throws Exception{
        VendorDTO vendor = new VendorDTO();
        vendor.setName("Name");

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendor.getName());
        returnDTO.setVendor_url("/api/v1/vendors/1");

        when(vendorService.patchVendor(anyLong(),any(VendorDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch("/api/v1/vendors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo("Name")))
                .andExpect(jsonPath("$.vendor_url",equalTo("/api/v1/vendors/1")));

    }

    @Test
    public void deleteVendor() throws Exception{
        Long id = 1L;

        mockMvc.perform(delete("/api/v1/vendors/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        verify(vendorService,times(1)).deleteVendorById(id);
    }

}