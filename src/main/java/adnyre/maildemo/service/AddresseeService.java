package adnyre.maildemo.service;

import adnyre.maildemo.dto.AddresseeDto;
import adnyre.maildemo.model.Addressee;

import java.util.List;
import java.util.Set;

public interface AddresseeService {
    Addressee get(long id);

    Addressee getByEmail(String email);

    List<Addressee> getByEmails(Set<String> emails);

    Addressee save(AddresseeDto dto);

    Addressee update(AddresseeDto dto);

    void delete(long id);

    Set<Addressee> selectNewAddresseesForCampaign(long campaignId);

    Set<Addressee> selectAllAddresseesForCampaign(long campaignId);
}