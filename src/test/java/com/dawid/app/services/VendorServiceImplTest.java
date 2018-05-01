package com.dawid.app.services;

import com.dawid.app.api.v1.mapper.VendorMapper;
import com.dawid.app.api.v1.model.VendorDTO;
import com.dawid.app.domain.Vendor;
import com.dawid.app.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VendorServiceImplTest {


    @Mock
    VendorRepository vendorRepository;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    VendorService vendorService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        vendorService = new VendorServiceImpl(vendorRepository,vendorMapper);
    }

    @Test
    public void getAllVendorsTest() throws Exception{
        Vendor vendor1 = new Vendor();
        vendor1.setId(1L);
        vendor1.setName("Name1");

        Vendor vendor2 = new Vendor();
        vendor2.setId(2L);
        vendor2.setName("Name2");

        when(vendorRepository.findAll()).thenReturn(Arrays.asList(vendor1,vendor2));

        List<VendorDTO> vendors = vendorService.getAllVendors();

        assertEquals(2, vendors.size());
    }


    @Test
    public void getVendorByIdTest() throws Exception{
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName("Name");

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

        VendorDTO vendorDTO = vendorService.getVendorById(anyLong());

        assertEquals(vendor.getName(),vendorDTO.getName());
    }

    @Test
    public void createNewVendorTest() throws Exception{

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Name");

        Vendor savedVendor = new Vendor();
        savedVendor.setName(vendorDTO.getName());
        savedVendor.setId(1L);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        VendorDTO returnedDTO = vendorService.createNewVendor(vendorDTO);

        assertEquals("Name", returnedDTO.getName());
        assertEquals("/api/v1/vendors/1",returnedDTO.getVendor_url());
    }

    @Test
    public void saveCustomerByDTOTest() throws Exception{
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Name");

        Vendor vendor = new Vendor();
        vendor.setName(vendorDTO.getName());
        vendor.setId(1L);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDTO returnedDTO = vendorService.saveCustomerByDTO(1L,vendorDTO);

        assertEquals("Name", returnedDTO.getName());
        assertEquals("/api/v1/vendors/1",returnedDTO.getVendor_url());
    }


    @Test
    public void patchVendorTest() throws Exception{
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("ChangedName");

        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName("Name");

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDTO returnedDTO = vendorService.patchVendor(1L, vendorDTO);

        assertEquals("ChangedName",returnedDTO.getName());
        assertEquals("/api/v1/vendors/1",returnedDTO.getVendor_url());
    }


    @Test
    public void deleteVendorTest() throws Exception{
        Long id = 1L;

        vendorRepository.deleteById(id);

        verify(vendorRepository,times(1)).deleteById(id);
    }

}