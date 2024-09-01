package com.devgroup.basic.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devgroup.basic.loginRequest;
import com.devgroup.basic.entities.Group;
import com.devgroup.basic.entities.User;
import com.devgroup.basic.repository.userRepo;
import com.devgroup.basic.services.userService;


@Service
public class userServiceImpl implements userService {

    @Autowired
    private userRepo userRe;
    
    @Autowired
    private PasswordEncoder passEncoder;
    

	@Override
	public User getUserById(int id) {
		try {
			User tempUser = userRe.findById(id).get();
			return tempUser;
		}
		catch (Exception e){
			System.out.println("Error generated while finding the user!");
			return null;
		}
	}


	@Override
	public List<User> getAllUsers() {
		try {
			List<User> userList = (ArrayList<User>) userRe.findAll();
			return userList;
		}
		catch (Exception e){
			System.out.println("Error generated while finding all the users!");
			return null;
		}
	}


	@Override
	public User addUser(User user) {
		try {
			String encodedPass = passEncoder.encode(user.getPassword());
			user.setPassword(encodedPass);
			User tempuser = userRe.save(user);
            return tempuser;
        } catch (Exception e) {
        	System.err.println(e);
            System.out.println("Error genereated while creating new user!");
            return null;
        }
	}


	@Override
	public User updateUser(User user) {
		try {
            User tempuser = userRe.findByEmail(user.getEmail());
            tempuser.setFirstName(user.getFirstName());
            tempuser.setLastName(user.getLastName());
            tempuser.setPassword(user.getPassword());
            tempuser.setPhone(user.getPhone());
            userRe.save(tempuser);
            return tempuser;
        } catch (Exception e) {
            System.out.println("Error genereated while updating user!");
            return null;
        }
	}


	@Override
	// 0 is for success and 1 is for failure
	public int deleteUser(String email) {
		try {
            userRe.delete(userRe.findByEmail(email));
            return 0;
        } catch (Exception e) {
        	System.out.println(e);
            System.out.println("Error genereated while deleting user!");
            return 1;
        }
	}


	@Override
	// 0 : success, 1 : not matching/error
	public int userAuth(loginRequest req) {
		try {
			User user = userRe.findByEmail(req.getUserEmail());
			if(user != null) {
				if(passEncoder.matches(req.getUserPassword(), user.getPassword())) {
	            	return 0;
	            }else {
	            	return 1;
	            }
			}
			return 1;
			
        } catch (Exception e) {
        	System.err.println(e);
            System.out.println("Error genereated while authenticating user!");
            return 1;
        }
	}


	@Override
	public int addAllUsers(List<User> userList) {
		try {
			for(User tempUser : userList ) {
				String encodedPass = passEncoder.encode(tempUser.getPassword());
				tempUser.setPassword(encodedPass);
				userRe.save(tempUser);
			}			
            return 0;
        } catch (Exception e) {
            System.out.println("Error genereated while adding multiple users!");
            return 1;
        }
	}


	@Override
	public int updateUserGrpList(User user) {
		try {
			User tempuser = userRe.save(user);
            return 0;
        } catch (Exception e) {
            System.out.println("Error genereated while creating new user!");
            return 1;
        }
	}


	@Override
	public User getUserByEmail(String email) {
		try {
			return userRe.findByEmail(email);
		} catch (Exception e) {
			return null;
		}
	}

}
