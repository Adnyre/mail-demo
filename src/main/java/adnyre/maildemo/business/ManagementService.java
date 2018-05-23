package adnyre.maildemo.business;

public interface ManagementService {
    void sendToNewAddressees(long campaignId);

    void sendToAll(long campaignId);

    void resend(long campaignId);

    void checkEmail(long userId);
}