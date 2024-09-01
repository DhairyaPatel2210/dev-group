package com.devgroup.basic.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.aspectj.weaver.ast.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devgroup.basic.groupInfo;
import com.devgroup.basic.dto.GroupDTO;
import com.devgroup.basic.dto.UserDashDetails;
import com.devgroup.basic.entities.Group;
import com.devgroup.basic.entities.User;
import com.devgroup.basic.repository.groupRepo;
import com.devgroup.basic.services.groupService;
import com.devgroup.basic.services.userService;

import net.bytebuddy.asm.Advice.Unused;

@Service
public class groupServiceImpl implements groupService{
	
	@Autowired
	private groupRepo grpRepo;
	
	@Autowired
	private userService userSer;
	
	@Override
	public Group createGroup(String userEmail, String grpName) {
		try {
			
			//getting user object which need to be added into the group
			User user = userSer.getUserByEmail(userEmail);
			List<User> userList = new ArrayList<User>();
			userList.add(user);
			Group group = new Group();
			group.setCreatedByUser(user);
			group.setGroupName(grpName);
			group.setUserList(userList);
			Group newGroup = grpRepo.save(group);
			
			//adding newly generated group into the user's joined grp list
			List<Group> grpList = user.getGroupList();
			grpList.add(newGroup);
			user.setGroupList(grpList);
			int status = userSer.updateUserGrpList(user);
			
			return newGroup;
		
		} catch (Exception e) {
			System.out.println("Error generated while creating a new group!" + e);
			return null;
		}
	}

	@Override
	public int joinGroupByName(String userEmail, String grpName) {
		try {
			
			//need to get the user
			User user = userSer.getUserByEmail(userEmail);
			
			if (user != null) {
				//then need to get the userlist from the defined group and add the particular user back to the list
				Group grp = grpRepo.findGroupByGroupName(grpName);
				if(grp != null) {
					List<User> userList = grp.getUserList();
					
					if(!userList.contains(user)) {
						userList.add(user);
						grp.setUserList(userList);
						grp = grpRepo.save(grp);
						
						//now update the user's grouplist and save it to the database
						List<Group> grpList = user.getGroupList();
						grpList.add(grp);
						user.setGroupList(grpList);
						userSer.updateUser(user);
						
						return 0;
					}					
				}
			}
			return 1;
		} catch (Exception e) {
			System.out.println("Error generated while joining a group by name!");
			return 1;
		}
	}

	@Override
	public List<groupInfo> searchGroupByName(String grpName) {
		try {
			List<groupInfo> groupInfoList = new ArrayList<groupInfo>(); 
			if(grpName.length() != 0) {
				List<Group> grpList = grpRepo.findByGroupNameContains(grpName);
				if (grpList.size() != 0) {
					grpList.stream().forEach(group -> { 
						groupInfo newGrpInfo = new groupInfo(group.getGroupName(), this.getCreatorName(group), group.getUserList().size());
						groupInfoList.add(newGrpInfo);
					});
					return groupInfoList;
				}
			}	
			return groupInfoList;
		} catch (Exception e) {
			System.out.println("Error while searching groups by name");
			return null;
		}
	}
	
	

	@Override
	public int leaveGroup(String userEmail, int groupId) {
		try {
			Group grp = grpRepo.findById(groupId).get();
			User reqUser = userSer.getUserByEmail(userEmail);
			if(grp != null) {
				List<User> memberList = grp.getUserList();
				User owner = grp.getCreatedByUser();
				int ownerId = owner.getUserId();
				//if the leaving user is the owner and member count is more than 2, then make someone else as an owner
				if (ownerId == reqUser.getUserId() && memberList.size()>=2)	{
					User newOwner = memberList.get(1);  //got the second senior member
					memberList.remove(0);   // removing the owner from the userList
					grp.setCreatedByUser(newOwner); // setting up the new Owner
					grp.setUserList(memberList);  // setting up updated memberList
					grp = grpRepo.save(grp); // making the changes to the actual database
					deleteGroupFromUser(reqUser.getUserId(), groupId);
				}else if(ownerId != reqUser.getUserId() && memberList.size()>=2){
					memberList.removeIf(member -> (member.getUserId() == reqUser.getUserId()));
					grp.setUserList(memberList);
					grp = grpRepo.save(grp);
					deleteGroupFromUser(reqUser.getUserId(), groupId);
				}else{
					deleteGroupFromUser(reqUser.getUserId(), groupId);
					grpRepo.deleteById(grp.getGroupId());  // if there is only one member then delete the whole group with all ideas
					  // inside it. 
				}
				return 0;
			}
			return 1;
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Error generated while leaving the group (groupServiceImpl.leaveGroup)");
			return 1;
		}
		
	}
	
	private void deleteGroupFromUser(int userId, int groupId) {
		try {
			//the below code is to remove the group from the user_group relation
			User user = userSer.getUserById(userId);
			if(user != null) {
				List<Group> grpList = user.getGroupList();
				grpList.removeIf(eachGroup -> (eachGroup.getGroupId() == groupId));
				user.setGroupList(grpList);
				userSer.updateUserGrpList(user);
			}
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Error generated while deleting the group from the user(groupServiceImpl.deleteGroupFromUser)");
		}
	}

	@Override
	public Group updateGroup(Group grp) {
		try {
			Group newGrp = grpRepo.save(grp);
			return newGrp;
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Error generated while deleting the group from the user(groupServiceImpl.updateGroup)");
			return null;
		}
	}

	@Override
	public UserDashDetails getDetails(String userEmail) {
		try {
			User user = userSer.getUserByEmail(userEmail);
			List<GroupDTO> joinedGroupList = new ArrayList<GroupDTO>();
			List<GroupDTO> createdGroupList = new ArrayList<GroupDTO>();
			user.getGroupList().stream().forEach(group -> { 
				GroupDTO newGrpInfo = new GroupDTO(group.getGroupName(), this.getCreatorName(group), group.getUserList().size(), group.getGroupId());
				if(group.getCreatedByUser().getUserId() == user.getUserId()) {
					createdGroupList.add(newGrpInfo);
				}else {
					joinedGroupList.add(newGrpInfo);
				}
			});
			UserDashDetails userDetails = new UserDashDetails(this.getUserName(user), joinedGroupList, createdGroupList);
			return userDetails;
		} catch (Exception e) {
			System.out.println("Error while getting userdetails" + e);
			return null;
		}
	}
	
	private String getCreatorName(Group grp) {
		try {
			User user = grp.getCreatedByUser();
			return user.getFirstName() + " " + user.getLastName();
		} catch (Exception e) {
			System.out.println("Error generated while extracting the creator name");
			return null;
		}
	}
	
	private String getUserName(User user) {
		try {
			return user.getFirstName() + " " + user.getLastName();
		} catch (Exception e) {
			System.out.println("Error generated while extracting the creator name");
			return null;
		}
	}

	@Override
	public Group findGroupById(int groupId) {
		try {
			Group grp = grpRepo.findById(groupId).get();
			return grp;
		} catch (Exception e) {
			System.out.println("Error while finding group by Id");
			return null;
		}
	}

}
