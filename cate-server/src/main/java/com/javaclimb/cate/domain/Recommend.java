package com.javaclimb.cate.domain;

import java.io.Serializable;

/**
 * 推荐
 */
public class Recommend implements Serializable {
    private Long userId; //用户id
    private Long foodId; //美食id
    private Integer commend; //收藏数
    
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getFoodId() {
		return foodId;
	}
	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}
	public Integer getCommend() {
		return commend;
	}
	public void setCommend(Integer commend) {
		this.commend = commend;
	}

}
