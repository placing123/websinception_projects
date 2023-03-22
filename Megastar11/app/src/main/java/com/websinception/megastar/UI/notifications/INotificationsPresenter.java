package com.websinception.megastar.UI.notifications;


import com.websinception.megastar.beanInput.NotificationDeleteInput;
import com.websinception.megastar.beanInput.NotificationInput;
import com.websinception.megastar.beanInput.NotificationMarkReadInput;

/**
 * Created by hp on 06-07-2017.
 */

public interface INotificationsPresenter {
    void actionNotificationsList(NotificationInput notificationInput);

    void notificationRead(NotificationMarkReadInput markReadInput);

    void clear_badges(String loginSessionKey);

    void deleteNotification(NotificationDeleteInput mDeleteInput);

}
