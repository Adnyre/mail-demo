package adnyre.maildemo.service;

import adnyre.maildemo.dto.AddresseeDto;
import adnyre.maildemo.model.Addressee;

import java.util.List;

public interface AddresseeService {
    Addressee get(long id);

    Addressee getByEmail(String email);

    Addressee save(AddresseeDto dto);

    Addressee update(AddresseeDto dto);

    void delete(long id);

    List<Addressee> selectAddresseesForCampaign(long campaignId);
}