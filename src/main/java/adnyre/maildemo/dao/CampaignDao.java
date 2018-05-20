package adnyre.maildemo.dao;

import adnyre.maildemo.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignDao extends JpaRepository<Campaign, Long> {
}