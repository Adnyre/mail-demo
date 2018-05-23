package adnyre.maildemo.dao;

import adnyre.maildemo.model.Addressee;

import java.util.List;

public interface CustomAddresseeDao {
    List<Addressee> selectNewAddresseesForCampaign(long campaignId);

    List<Addressee> selectAllAddresseesForCampaign(long campaignId);
}