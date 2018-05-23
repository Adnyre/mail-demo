package adnyre.maildemo.dao.impl;

import adnyre.maildemo.dao.CustomAddresseeDao;
import adnyre.maildemo.model.Addressee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

import static adnyre.maildemo.model.Addressee.SELECT_ALL_ADDRESSEES_FOR_CAMPAIGN;
import static adnyre.maildemo.model.Addressee.SELECT_NEW_ADDRESSEES_FOR_CAMPAIGN;

@Slf4j
@Repository
public class AddresseeDaoImpl implements CustomAddresseeDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Addressee> selectNewAddresseesForCampaign(long campaignId) {
        TypedQuery<Addressee> query = entityManager.createNamedQuery(SELECT_NEW_ADDRESSEES_FOR_CAMPAIGN, Addressee.class);
        query.setParameter("id", campaignId);
        return query.getResultList();
    }

    @Override
    public List<Addressee> selectAllAddresseesForCampaign(long campaignId) {
        TypedQuery<Addressee> query = entityManager.createNamedQuery(SELECT_ALL_ADDRESSEES_FOR_CAMPAIGN, Addressee.class);
        query.setParameter("id", campaignId);
        return query.getResultList();
    }
}