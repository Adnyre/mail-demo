package adnyre.maildemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatsView {
    private long userId;
    private String userName;
    private String email;
    // number of campaigns
    private long campaigns;
    // number of emails sent by this user in all campaigns
    private long sentEmails;
    // number of emails that can be sent to potentially interested people
    // in all campaigns
    private long potentialEmails;
    // number of people from addressee table who sent mail to this user
    private long responses;
    // number of people who received mail from this user
    private long potentialResponses;
}