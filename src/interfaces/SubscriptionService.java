package interfaces;

import model.User;

public interface SubscriptionService {
	
	void upgradeToPremium(User user);
	
	double calculateMonthlyFee(User user);
	
}
