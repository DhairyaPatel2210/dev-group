package com.devgroup.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Votes {
	private int upvoteCount;
	private int downvoteCount;
}
