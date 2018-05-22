package adnyre.maildemo.dao.impl;

import adnyre.maildemo.dao.CustomCampaignDao;
import adnyre.maildemo.dto.CampaignStatsView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static adnyre.maildemo.model.Campaign.GET_CAMPAIGN_STATS;
import static adnyre.maildemo.model.Campaign.GET_CAMPAIGN_STATS_BY_USER_ID;

public class CampaignDaoImpl implements CustomCampaignDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<CampaignStatsView> getCampaignStats(){
        TypedQuery<CampaignStatsView> query = entityManager.createNamedQuery(GET_CAMPAIGN_STATS, CampaignStatsView.class);
        return query.getResultList();
    }

    @Override
    public List<CampaignStatsView> getCampaignStatsByUserId(long userId) {
        TypedQuery<CampaignStatsView> query = entityManager.createNamedQuery(GET_CAMPAIGN_STATS_BY_USER_ID, CampaignStatsView.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}