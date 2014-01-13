package com.nimbusbase.tweetdiary.model;

import java.util.ArrayList;

public class VideoList {

	public static ArrayList<VideoItem> getVideoList() {
		ArrayList<VideoItem> resultList = new ArrayList<VideoItem>();

		VideoItem itm;

		itm = new VideoItem(
				1,
				89,
				"Title1",
				"cb_checked_blue.png",
				"Content1",
				false);
		resultList.add(itm);

		itm = new VideoItem(
				1,
				89,
				"Title2",
				"cb_checked_blue.png",
				"Content2",
				false); 
		resultList.add(itm);

		itm = new VideoItem(
				3,
				89,
				"Title3",
				"cb_checked_blue.png",
				"Content3",
				false);
		resultList.add(itm);
		resultList.add(itm);

		itm = new VideoItem(
				4,
				89,
				"Title4",
				"cb_checked_blue.png",
				"Content4",
				false);
		resultList.add(itm);
	 
		itm = new VideoItem(
				5,
				89,
				"Title5",
				"cb_checked_blue.png",
				"Content5",
				false); 
		resultList.add(itm);

		itm = new VideoItem(
				6,
				89,
				"Title6",
				"cb_checked_blue.png",
				"Content6",
				false); 
		resultList.add(itm);

		itm = new VideoItem(
				7,
				89,
				"Title7",
				"cb_checked_blue.png",
				"Content7",
				false); 
		resultList.add(itm);

		 

		return resultList;
	};

}
