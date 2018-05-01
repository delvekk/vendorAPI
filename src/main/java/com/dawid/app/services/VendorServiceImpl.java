package com.dawid.app.services;

import com.dawid.app.api.v1.mapper.VendorMapper;
import com.dawid.app.api.v1.model.VendorDTO;
import com.dawid.app.domain.Vendor;
import com.dawid.app.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;
    private static final String BASE_URL = "/api/v1/vendors/";

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendroToVendorDTO(vendor);
                    vendorDTO.setVendor_url(BASE_URL + vendor.getId());

                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        VendorDTO vendorDTO = vendorMapper.vendroToVendorDTO(vendorRepository.findById(id).get());
        if(vendorDTO != null) {
            vendorDTO.setVendor_url(BASE_URL + id);
            return vendorDTO;
        } else {
            //throw todo
            throw new ResourceNotFoundException();
        }

    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO returnVendor = vendorMapper.vendroToVendorDTO(savedVendor);
        returnVendor.setVendor_url(BASE_URL + savedVendor.getId());

        return returnVendor;
    }

    @Override
    public VendorDTO saveCustomerByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendor.setId(id);

        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO returnVendor = vendorMapper.vendroToVendorDTO(savedVendor);
        returnVendor.setVendor_url(BASE_URL + id);

        return returnVendor;


    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
       Vendor vendor = vendorRepository.findById(id).get();
       if(vendor == null) throw new ResourceNotFoundException();
       if(vendorDTO.getName() != null){
           vendor.setName(vendorDTO.getName());
       }
       VendorDTO returnVendor = vendorMapper.vendroToVendorDTO(vendorRepository.save(vendor));
       returnVendor.setVendor_url(BASE_URL + id);

       return returnVendor;

    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }
}
