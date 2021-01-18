package com.turubarov.dadataproxy.servises;

import com.turubarov.dadataproxy.converters.DadataJsonConverter;
import com.turubarov.dadataproxy.dadataclient.DadataClient;
import com.turubarov.dadataproxy.dadataclient.SearchTypes;
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
    private final int HOURS_AFTER_LAST_REQUEST = 3;

    public List<Address> processSearch(String type, String query) throws Exception {
        if (SearchTypes.CITY.equals(type)) {
            return addressRepository.findByCity(query);
        } else if (SearchTypes.REGION.equals(type)) {
            return addressRepository.findByRegion(query);
        } else if (SearchTypes.SETTLEMENT.equals(type)) {
            return addressRepository.findBySettlement(query);
        } else if (SearchTypes.STREET.equals(type)) {
            return addressRepository.findByStreet(query);
        }
        throw new Exception("Неправильный параметр для поиска");
    }

    private Request saveRequest(String query) {
        Request reqDb = requestRepository.findByQuery(query);
        if (reqDb == null) {
            reqDb = requestRepository.save(new Request(query, new Date()));
        }
        reqDb.incCountUse();
        reqDb.setTimeOfQuery(new Date());
        return reqDb;
    }

    private long hoursAfterLastQuery(Request request) {
        Date currentDate = new Date();
        Date lastUseDate = request.getTimeOfQuery();
        return TimeUnit.MILLISECONDS.toHours(
                currentDate.getTime() - lastUseDate.getTime());
    }

    private Request refreshRequest(Request request) {
        List<Address> addrs = DadataJsonConverter.convert(DadataClient.suggestAdress(request.getQuery()));
        for (Address addr : addrs) {
            Address addrDB = addressRepository.findByValue(addr.getValue());
            if (addrDB == null) {
                addrDB = addressRepository.save(addr);
            }
            request.AddAddress(addrDB);
        }
        return requestRepository.save(request);
    }


    public List<Address> processRequest(String query) {
        Request reqDb = saveRequest(query);
        if (reqDb.isNew() || (!reqDb.isNew() && hoursAfterLastQuery(reqDb) > HOURS_AFTER_LAST_REQUEST)) {
            reqDb = refreshRequest(reqDb);
        }
        List<Address> addrs = reqDb.getAddresses();
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
