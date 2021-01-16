package com.turubarov.dadataproxy.servises;

import com.turubarov.dadataproxy.converters.DadataJsonConverter;
import com.turubarov.dadataproxy.dadataclient.DadataClient;
import com.turubarov.dadataproxy.domain.Address;
import com.turubarov.dadataproxy.domain.Request;
import com.turubarov.dadataproxy.repositories.AddressRepository;
import com.turubarov.dadataproxy.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private RequestRepository requestRepository;

    private final int TIME_THRESHOLD_FOR_DELETE = 1;
    private final int COUNT_USE_THRESHOLD_FOR_DELETE = 3;

    public List<Address> processSearch(String type, String query) {
        switch (type) {
            case "city": return addressRepository.findByCity(query);
            case "region": return addressRepository.findByCity(query);
            case "settlement": return addressRepository.findByCity(query);
            case "street": return addressRepository.findByCity(query);
        }
        return null;
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
            if (diff < 3)
                return reqDB.getAddresses();
        }
        if (reqDB == null) {
            reqDB = requestRepository.save(new Request(query, new Date()));
        }
        List<Address> addrs = DadataJsonConverter.convert(DadataClient.suggestAdress(query));
        for (Address addr : addrs) {
            Address addrDB = addressRepository.findByValue(addr.getValue());
            if (addrDB == null) {
                addrDB = addressRepository.save(addr);
            }
            reqDB.AddAddress(addrDB);
        }
        requestRepository.save(reqDB);

        deleteOldRequests();

        return addrs;

    }

    private void deleteOldRequests() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -TIME_THRESHOLD_FOR_DELETE);
        Date timeThreshold = cal.getTime();
        requestRepository.deleteOldRequests(timeThreshold,
                COUNT_USE_THRESHOLD_FOR_DELETE);
    }
}
