package com.turubarov.dadataproxy.service;

import com.turubarov.dadataproxy.converter.DadataJsonConverter;
import com.turubarov.dadataproxy.dadataclient.DadataClient;
import com.turubarov.dadataproxy.dadataclient.SearchTypes;
import com.turubarov.dadataproxy.domain.Address;
import com.turubarov.dadataproxy.domain.Request;
import com.turubarov.dadataproxy.repository.AddressRepository;
import com.turubarov.dadataproxy.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private RequestRepository requestRepository;

    private final int HOURS_AFTER_LAST_REQUEST = 3;
    private final int MIN_LENGTH_FOR_QUERY = 2;

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

    public List<Address> processRequest(String query)throws Exception {
        if (query.length() < MIN_LENGTH_FOR_QUERY) {
            throw new Exception("Длина запроса должна быть не меньше "+Integer.toString(MIN_LENGTH_FOR_QUERY));
        }
        Request reqDb = saveRequest(query);
        if (reqDb.isNew() || (!reqDb.isNew() && hoursAfterLastQuery(reqDb) > HOURS_AFTER_LAST_REQUEST)) {
            reqDb = refreshRequest(reqDb);
        }
        List<Address> addrs = reqDb.getAddresses();

        return addrs;
    }
}
