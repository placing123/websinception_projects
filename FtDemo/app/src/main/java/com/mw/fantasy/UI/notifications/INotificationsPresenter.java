package com.mw.fantasy.UI.notifications;


import com.mw.fantasy.beanInput.NotificationDeleteInput;
import com.mw.fantasy.beanInput.NotificationInput;
import com.mw.fantasy.beanInput.NotificationMarkReadInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface INotificationsPresenter {
    void actionNotificationsList(NotificationInput notificationInput);

    void notificationRead(NotificationMarkReadInput markReadInput);

    void clear_badges(String loginSessionKey);

    void deleteNotification(NotificationDeleteInput mDeleteInput);

}
