package com.wondersgroup.hs.healthcloud.common.entity;

public class AlbumModel {
    
    public static final int TYPE_ALL = 0;
    public static final int TYPE_ALBUM = 1;

	private String name;

	private int count;

	private String recent;

	private boolean isCheck;
	
	private int type = TYPE_ALBUM;

	public AlbumModel() {
		super();
	}
	
	public AlbumModel(String name) {
		this.name = name;
	}

	public AlbumModel(String name, int count, String recent) {
		super();
		this.name = name;
		this.count = count;
		this.recent = recent;
	}
	
	public AlbumModel(String name, int count, String recent, boolean isCheck) {
		super();
		this.name = name;
		this.count = count;
		this.recent = recent;
		this.isCheck = isCheck;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getRecent() {
		return recent;
	}

	public void setRecent(String recent) {
		this.recent = recent;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public void increaseCount() {
		count++;
	}

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
