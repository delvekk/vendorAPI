package com.dawid.app.api.v1.mapper;

import com.dawid.app.api.v1.model.VendorDTO;
import com.dawid.app.domain.Vendor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VendorMapperTest {

    public static final String NAME = "name";
    VendorMapper vendorMapper = VendorMapper.INSTANCE;


    @Test
    public void vendorToVendorDTOTest() throws Exception{
        Vendor vendor = new Vendor();
        vendor.setName(NAME);

        VendorDTO vendorDTO = vendorMapper.vendroToVendorDTO(vendor);

        assertEquals(NAME,vendorDTO.getName());

    }


    @Test
    public void vendorDTOToVendorTest() throws Exception{
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

        assertEquals(NAME,vendor.getName());

    }

}