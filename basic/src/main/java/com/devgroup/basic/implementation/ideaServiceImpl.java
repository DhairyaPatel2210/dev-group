package com.devgroup.basic.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devgroup.basic.IdeaRequest;
import com.devgroup.basic.VoteRequest;
import com.devgroup.basic.Votes;
import com.devgroup.basic.dto.IdeaDTO;
import com.devgroup.basic.entities.Group;
import com.devgroup.basic.entities.Idea;
import com.devgroup.basic.entities.User;
import com.devgroup.basic.repository.ideaRepo;
import com.devgroup.basic.services.groupService;
import com.devgroup.basic.services.ideaService;
import com.devgroup.basic.services.userService;

@Service
public class ideaServiceImpl implements ideaService{
	
	@Autowired
	private userService userSer;
	
	@Autowired
	private ideaRepo ideaRep;
	
	@Autowired
	private groupService grpService;
	
	
	@Override
	public int createIdea(String userEmail,IdeaRequest idea) {
		try {
			User user = userSer.getUserByEmail(userEmail);
			if(user != null) {
				Group grp = user.getGroupList().stream().filter(group -> group.getGroupId() == idea.getGroupId()).findAny().get();
				Idea newIdea = new Idea(idea.getTitle(),idea.getDescription(),idea.getTechnology(),idea.getOwnership(),0,0,user,grp);
				newIdea = ideaRep.save(newIdea);
				
				// now adding newly generated idea to the group's idealist
				List<Idea> ideaList = grp.getIdeaList();
				ideaList.add(newIdea);
				grp.setIdeaList(ideaList);
				grpService.updateGroup(grp);	
			}
			return 0;
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Error generated while creating the idea (ideaServiceImpl.createIdea)");
			return 1;
		}	
	}

	@Override
	// 0 = dislike, 1 = like
	public Votes voteIdea(String userEmail,VoteRequest req) {
		try {
			Idea idea = ideaRep.findById(req.getIdeaId()).get();
			User user = userSer.getUserByEmail(userEmail);
			Votes votes = new Votes();
			if (idea.getOwnership().toLowerCase().equals("public") && user != null) {
				idea = voteLogic(user, idea, req);
			}else {
				Group groupCreatedInGroup = idea.getCreatedInGroup();
				List<User> memberList = groupCreatedInGroup.getUserList();
				if(memberList.contains(user)) {
					idea = voteLogic(user, idea, req);
				}else {
					return null;
				}
			}
			
			//returning the final votes
			if(idea != null) {
				votes.setUpvoteCount(idea.getUpvoteCounter());
				votes.setDownvoteCount(idea.getDownvoteCounter());
				return votes;
			}
			
			return null;
			
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Error while voting the idea (ideaServiceImpl.voteIdea)");
			return null;
		}
	}
	
	@Override
	public List<IdeaDTO> getIdeas(int groupId) {
		try {
			Group group = grpService.findGroupById(groupId);
			if(group != null) {
				List<Idea> sharedIdeas = group.getIdeaList();
				List<IdeaDTO> ideaDtoList = new ArrayList<IdeaDTO>(); 
				for(int i=0; i < sharedIdeas.size(); i++) {
					Idea idea = sharedIdeas.get(i);
					ideaDtoList.add(new IdeaDTO(
							idea.getIdeaId(),
							idea.getTitle(),
							idea.getDescription(),
							idea.getTechnology(),
							idea.getUpvoteCounter(),
							idea.getDownvoteCounter(),
							idea.getCreatedByUser().getFirstName() + " " +idea.getCreatedByUser().getLastName(),
							null));
				}
				return ideaDtoList;
			}
			return null;
		} catch (Exception e) {
			System.out.println("Error while getting ideas");
			return null;
		}
	}
	
	
	
	private Idea voteLogic(User user, Idea idea, VoteRequest req) {
		try {
				
				if(idea != null && user != null) {
					Set<Integer> upvoterList = idea.getUpvoterList();
					Set<Integer>downvoterList = idea.getDownvoterList(); 
					
					// if the request is made for like
					if(req.getVote() == 1) {
						
						// if the person requesting for like, already liked the idea
						if(upvoterList.contains(user.getUserId())) {
							upvoterList.remove((Integer)user.getUserId());
							idea.setUpvoteCounter(idea.getUpvoteCounter() - 1);
							idea.setUpvoterList(upvoterList);
							idea = ideaRep.save(idea);
							
						}
						// the person is not liked the post before
						else {
							// but the person has already disliked the post before.
							if(downvoterList.contains(user.getUserId())) {
								downvoterList.remove((Integer) user.getUserId());
								idea.setDownvoteCounter(idea.getDownvoteCounter() - 1);
								idea.setDownvoterList(downvoterList);
							}
							upvoterList.add(user.getUserId());
							idea.setUpvoteCounter(idea.getUpvoteCounter() + 1);
							idea.setUpvoterList(upvoterList);
							idea = ideaRep.save(idea);
						}
					}
				
				// if the request is made for disliking the idea
				else {
					if(downvoterList.contains(user.getUserId())) {
						downvoterList.remove((Integer) user.getUserId());
						idea.setDownvoteCounter(idea.getDownvoteCounter() - 1);
						idea.setDownvoterList(downvoterList);
						idea = ideaRep.save(idea);						
					}
					// the person is not disliked the post before
					else {
						// but the person has already like the post before
						if(upvoterList.contains(user.getUserId())) {
							upvoterList.remove((Integer)user.getUserId());
							idea.setUpvoteCounter(idea.getUpvoteCounter() - 1);
							idea.setUpvoterList(upvoterList);
						}
						downvoterList.add(user.getUserId());
						idea.setDownvoteCounter(idea.getDownvoteCounter() + 1);
						idea.setDownvoterList(downvoterList);
						idea = ideaRep.save(idea);
					}
				}
			}
				
			return idea;
			
		} catch (Exception e) {
			System.out.println("Error generated in the voting logic");
			return null;
		}
	}

	@Override
	public int validateUser(String userEmail, int groupId) {
		try {
			User user = userSer.getUserByEmail(userEmail);
			List<Group> grpList = user.getGroupList();
			Group grp = grpService.findGroupById(groupId);
			if(grp == null) {
				return 2;
			}
			if(grpList.contains(grp)) {
				return 0;
			}
			return 1;
			
			
		} catch (Exception e) {
			System.out.println("Error while validating user");
			return 1;
		}
	}

	@Override
	public Page<Idea> getPublicIdeas(int pageNumber, int pageSize) {
		try {
			Pageable pageReq = PageRequest.of(pageNumber, pageSize);
			Page<Idea> publicIdeas = ideaRep.findByOwnership("Public", pageReq);
//			List<Idea> publicIdeas = (List<Idea>) ideaRep.findByOwnership("Public");
//			System.out.println(publicIdeas);
//			List<IdeaDTO> publicIdeaDTOs = modelMapper.map(publicIdeas, new TypeToken<List<IdeaDTO>>() {}.getType());
			return publicIdeas;
		} catch (Exception e) {
			System.out.println("Error generated while getting the public ideas" + e);
			return null;
		}
	}

	
	
}
