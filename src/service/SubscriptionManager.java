package service;

import interfaces.SubscriptionService;
import model.PremiumUser;
import model.StandardUser;
import model.User;

public class SubscriptionManager implements SubscriptionService {

	public void upgradeToPremium(User user) {
        if (user instanceof PremiumUser) {
            System.out.println(user.getName() + " is already a Premium user.");
            return;
        }

        System.out.println("Upgrading " + user.getName() + " to Premium...");
        FriefindSystem.userList.remove(user);
        PremiumUser newPremiumUser = new PremiumUser(user.getId(), user.getName(), user.getAge(), user.getDetails(), user.getCity(), false);
        FriefindSystem.userList.add(newPremiumUser);
        System.out.println("Upgrade successful! Welcome to the elite club.");
    }

    public double calculateMonthlyFee(User user) {
        if (user instanceof PremiumUser) {
            return 100.0;
        } else if (user instanceof StandardUser) {
            return 0.0;   
        }
        return 0.0;
    }
}
