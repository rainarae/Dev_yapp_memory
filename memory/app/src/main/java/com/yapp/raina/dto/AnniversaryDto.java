package com.yapp.raina.dto;

import java.io.Serializable;

public class AnniversaryDto implements Serializable
{
	private int id_pk;
	private String date_ymd;
	private String title;
	private String abstract_;
	private String content;
	private String category;
	private String reference;
	private boolean alarm_st;
	private boolean solar_st;
	private boolean bookmark_st;

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("id_pk: " + id_pk + "\n");
		sb.append("date_ymd: " + date_ymd + "\n");
		sb.append("title: " + title + "\n");
		sb.append("abstract_: " + abstract_ + "\n");
		sb.append("content: " + content + "\n");
		sb.append("category: " + category + "\n");
		sb.append("reference: " + reference + "\n");
		sb.append("alarm_st: " + alarm_st + "\n");
		sb.append("solar_st: " + solar_st + "\n");
		sb.append("bookmark_st: " + bookmark_st + "\n");
		return sb.toString();
	}

	public void setId_pk(int id_pk) {
		this.id_pk = id_pk;
	}

	public int getId_pk() {
		return this.id_pk;
	}

	public void setDate_ymd(String date_ymd) {
		this.date_ymd = date_ymd;
	}
	public String getDate_ymd() {
		return this.date_ymd;
	}

	public void setAbstract_(String abstract_) {
		this.abstract_ = abstract_;
	}

	public String getAbstract_() {
		return this.abstract_;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategory() {
		return this.category;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getReference() {
		return this.reference;
	}

	public void setAlarm_st(boolean alarm_st) {
		this.alarm_st = alarm_st;
	}

	public boolean getAlarm_st() {
		return this.alarm_st;
	}

	public void setSolar_st(boolean solar_st) {
		this.solar_st = solar_st;
	}

	public boolean getSolar_st() {
		return this.solar_st;
	}

	public void setBookmark_st(boolean bookmark_st) {
		this.bookmark_st = bookmark_st;
	}

	public boolean getBookmark_st() {
		return this.bookmark_st;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return this.title;
	}
}
