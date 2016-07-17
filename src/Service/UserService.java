package Service;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Model.User;

@Service("userService")
@Transactional
public class UserService {

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	public User get(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		return (User) session.get(User.class, userId);
	}

	public boolean validate(String userName, String password) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.like("userName", userName));

		boolean flag = false;
		Object result = criteria.uniqueResult();
		if (result != null) {
			User user = (User) result;
			if (user.getPassword().equalsIgnoreCase(password)) {
				flag = true;
			}
		}

		if (flag == true) {
			return true;
		} else {
			return false;
		}
	}

}
