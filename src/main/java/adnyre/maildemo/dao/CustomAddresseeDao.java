package adnyre.maildemo.dao;

import adnyre.maildemo.model.Addressee;

import java.util.Set;

public interface CustomAddresseeDao {
    Set<Addressee> selectNewAddresseesForCampaign(long campaignId);

    Set<Addressee> selectAllAddresseesForCampaign(long campaignId);
}