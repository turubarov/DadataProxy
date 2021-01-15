package com.turubarov.dadataproxy.servises;

import com.turubarov.dadataproxy.converters.DadataJsonConverter;
import com.turubarov.dadataproxy.dadataclient.DadataClient;
import com.turubarov.dadataproxy.domain.Address;
import com.turubarov.dadataproxy.domain.Request;
import com.turubarov.dadataproxy.repositories.RequestRepository;
import com.turubarov.dadataproxy.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private RequestRepository requestRepository;

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public List<Address> processRequest(String query) {
        Request reqDB = requestRepository.findByQuery(query);

        if (reqDB != null) {
            Date useDate = reqDB.getTimeOfQuery();
            Date currentDate = new Date();
            long diff = TimeUnit.HOURS.convert(currentDate.getTime() - useDate.getTime(), TimeUnit.HOURS);
            reqDB.incCountUse();
            reqDB.setTimeOfQuery(currentDate);
            requestRepository.save(reqDB);
            //if (diff < 3)
                return reqDB.getAddresses();
        } else {
            reqDB = requestRepository.save( new Request(query, new Date()));
            List<Address> addrs = DadataJsonConverter.convert(DadataClient.suggestAdress(query));
            for (Address addr : addrs) {
                Address addrDB = addressRepository.findByValue(addr.getValue());
                if (addrDB == null) {
                    addrDB = addressRepository.save(addr);
                }
                reqDB.AddAddress(addrDB);
            }
            requestRepository.save(reqDB);
            return addrs;
        }
    }

    public void AddAddressesForQuery(String query) {
        addAddresses(DadataJsonConverter.convert(DadataClient.suggestAdress(query)));
    }

    private void addAddresses(List<Address> listAdr) {
        listAdr.stream().forEach(adr -> addAddress(adr));
    }

    private boolean addAddress(Address adr) {
        Address adrFromDb = addressRepository.findByValue(adr.getValue());

        if (adrFromDb != null) {
            return false;
        }
        addressRepository.save(adr);
        return true;
    }
}
