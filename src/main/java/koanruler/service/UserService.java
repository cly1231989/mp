package koanruler.service;

import koanruler.repository.UserRepository;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import koanruler.entity.*;
import koanruler.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JPAQueryFactory queryFactory;

	private Map<Integer, String> allUserFullName = new HashMap();
	private LocalDateTime getTime;

	//获取所有的上级用户的ID
	public List<Integer> getAllParentID(int userID)
	{
		QUser user1 = QUser.user;
		QUser user2 = new QUser("user2");
		QUser user3 = new QUser("user3");
		QUser user4 = new QUser("user4");
		QUser user5 = new QUser("user5");
		QUser user6 = new QUser("user6");
		QUser user7 = new QUser("user7");

		List<Tuple> results = queryFactory.select(user1.id, user2.id, user3.id, user4.id, user5.id, user6.id, user7.id)
				.from(user1)
				.leftJoin(user2).on(user1.parentuserid.eq(user2.id))
				.leftJoin(user3).on(user2.parentuserid.eq(user3.id))
				.leftJoin(user4).on(user3.parentuserid.eq(user4.id))
				.leftJoin(user5).on(user4.parentuserid.eq(user5.id))
				.leftJoin(user6).on(user5.parentuserid.eq(user6.id))
				.leftJoin(user7).on(user6.parentuserid.eq(user7.id))
				.where(user1.id.eq(userID))
				.fetch();

        return intTupleToList(results);
	}

	public String getUserName(int userID) {		
		return userRepository.findOne(userID).getName();
	}

	//获取下级用户信息
	@Transactional
	public Organization getSubDepartmentInfo(int userID) {
		Organization selfinfo = new Organization();
		List<Organization> subs = new ArrayList<>();
				
		User user = userRepository.getOne(userID);
		
		selfinfo.setSelfinfo( new UserIDAndName(user.getId(), user.getType(), user.getName()) );
		selfinfo.setSubs(subs);

		//如果用户是科室/楼层/病区，就返回自己；否则，返回下属机构的相关数据
		if(user.getType() == 5){	
			return selfinfo;
		}
		
		List<User> childlist = userRepository.findByParentuserid(userID);
		childlist.forEach(childUser->subs.add( getSubDepartmentInfo(childUser.getId())));
		
		return selfinfo;
	}

	private List<Integer> intTupleToList(List<Tuple> tupleList){
		return tupleList.stream()
						.map(tuple -> {
							List<Integer> list = new ArrayList<>();

							for (int i = 0; i < tuple.size(); i++) {
								Integer id = tuple.get(i, Integer.class);
								if (id != null)
									list.add(id);
							}

							return list;
						})
						.flatMap(Collection::stream)
						.distinct()
						.collect(Collectors.toList());
    }

	//获取所有的下级用户ID
	public List<Integer> getAllChildID(int userID)
	{
        QUser user1 = QUser.user;
        QUser user2 = new QUser("user2");
        QUser user3 = new QUser("user3");
        QUser user4 = new QUser("user4");
        QUser user5 = new QUser("user5");
        QUser user6 = new QUser("user6");
        QUser user7 = new QUser("user7");

        List<Tuple> results = queryFactory.select(user1.id, user2.id, user3.id, user4.id, user5.id, user6.id, user7.id)
                .from(user1)
                .leftJoin(user2).on(user1.id.eq(user2.parentuserid))
                .leftJoin(user3).on(user2.id.eq(user3.parentuserid))
                .leftJoin(user4).on(user3.id.eq(user4.parentuserid))
                .leftJoin(user5).on(user4.id.eq(user5.parentuserid))
                .leftJoin(user6).on(user5.id.eq(user6.parentuserid))
                .leftJoin(user7).on(user6.id.eq(user7.parentuserid))
                .where(user1.id.eq(userID))
                .fetch();


        return intTupleToList(results);
	}

	//分页获取下级用户ID, countPerPage==-1表示获取所有下级用户
	public ResultList<Integer> getAllChildID(int userID, Integer page, Integer countPerPage)
	{
		List<Integer> userIds = getAllChildID(userID);
		if (countPerPage == -1)
			return new ResultList<>(userIds.size(), userIds);

		int first = page * countPerPage;
		if (first >= userIds.size())
		    return new ResultList<>(0, null);

		int last = (page+1)*countPerPage;
		if (last > userIds.size())
		    last = userIds.size();

		return new ResultList<>(userIds.size(), userIds.subList(first, last));
	}

	public User getUser(Integer userid) {
		return userRepository.findOne(userid);
	}

	//获取所有的上下级用户
	public Map<Integer, User> getAllParentsAndChildrenAndAnalysts(int userId) {
		List<Integer> userIds = getAllChildID(userId);
		userIds.addAll(getAllParentID(userId));
		userIds = userIds.stream().distinct().collect(Collectors.toList());

		Map<Integer, User> userMap = new HashMap();
		List<User> users = getAllUser(userIds);
		users.addAll(userRepository.findByType(7));
		users.forEach(user -> userMap.put(user.getId(), user));
		return userMap;
	}

	public List<User> getAllUser(List<Integer> userIDList) {
		return userRepository.findByIdIn(userIDList);
	}

    public User getUserById(Integer userId) {
		return userRepository.findOne(userId);
    }

    public String getFullName(Integer userid) {
		if (allUserFullName.isEmpty() || LocalDateTime.now().minusMinutes(3).isAfter(getTime)){
			getTime = LocalDateTime.now();
			List<User> users = userRepository.findAll();
            allUserFullName.clear();

			Map<Integer, User> userMap = new HashMap<>();
			users.forEach(user1 -> userMap.put(user1.getId(), user1));

			users.forEach(user -> {
				List<User> parentUsers = getParentUser(user, userMap);

				List<String> result = parentUsers.stream().map(user1 -> user1.getName()).collect(Collectors.toList());
				allUserFullName.put(user.getId(), String.join(" / ", result));
			});
		}

		return allUserFullName.get(userid);
    }

	private List<User> getParentUser(User user, Map<Integer, User> userMap) {
		List<User> parentUsers = new ArrayList<>();

		parentUsers.add(0, user);
		Integer parentId = user.getParentuserid();

		while (parentId != null && parentId != 0){
			User parentUser = userMap.get(parentId);
			if (parentUser != null) {
				parentUsers.add(0, parentUser);
				parentId = parentUser.getParentuserid();
			}
		}

		return parentUsers;
	}

	private String getType(int type){
	    String[] userTypes = {"", "管理员", "经销商", "中心", "医院", "科室/楼层/病区", "会诊", "分析师"};
	    return userTypes[type];
    }

	public List<UserInfo> getAllSubUser(Integer userId) {
		return getAllUser( getAllChildID(userId) )
				.stream()
				.map(user -> new UserInfo(user.getName(),
										  getFullName(user.getParentuserid()),
						   				  user))
				.collect(Collectors.toList());
	}

	public ResultList<User> getSubUser(Integer userId, Integer page, Integer countPerPage, String filter) {
        List<Integer> userIds = getAllChildID(userId);

        List<User> users = getAllUser(userIds);
        if (filter != null && !filter.isEmpty()) {
            String userName = filter.trim();
            users = users.stream().filter(user -> user.getName().contains(userName) || user.getAccount().contains(userName)).collect(Collectors.toList());
        }

        users.forEach(user -> {
            user.setParentFullName(getFullName(user.getParentuserid()));
            user.setUserType( getType(user.getType()) );
        });

        int totalCount = users.size();

        if (countPerPage != -1) {
            int first = page * countPerPage;
            if (first >= users.size())
                return new ResultList<>(0, null);

            int last = (page+1)*countPerPage;
            if (last > users.size())
                last = users.size();

            users = users.subList(first, last);
        }

        return new ResultList<>(totalCount, users);
	}

	public void addUser(User user) {
		userRepository.save(user);
	}

	public void deleteUser(Integer id) {
		userRepository.delete(id);
	}

	@Transactional
	public void editPwd(PwdEditInfo pwdEditInfo) {
		if (UserUtil.getCurUser().getId() != pwdEditInfo.getUserId())
			throw new AccessDeniedException("禁止修改其他人的密码");

		if (userRepository.findOne(pwdEditInfo.getUserId()).getPwd().compareTo(pwdEditInfo.getOldPwd()) != 0)
			throw new AccessDeniedException("原密码错误");

		userRepository.editPwd(pwdEditInfo.getUserId(), pwdEditInfo.getNewPwd());
	}
}
