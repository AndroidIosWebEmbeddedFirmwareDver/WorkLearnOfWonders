package com.wondersgroup.hs.healthcloud.common.entity;

import java.io.Serializable;

/**
 * 照片实体类
 * PhotoModel
 * chenbo
 * 2015年3月17日 下午3:35:22
 * @version 1.0
 */
public class PhotoModel implements Serializable {
    public static final int TYPE_CAMERA = 0;
    public static final int TYPE_PHOTO = 1;

	private static final long serialVersionUID = 1L;

	private String originalPath;

//    public void setLocalThumbPath(String localThumbPath) {
//        this.localThumbPath = localThumbPath;
//    }

    private String thumbPath;

//    public String getLocalThumbPath() {
//        return localThumbPath;
//    }

//    private String localThumbPath;
	private boolean isChecked;
	
	private int type = TYPE_PHOTO;

	public PhotoModel(String originalPath, boolean isChecked) {
		super();
		this.originalPath = originalPath;
		this.isChecked = isChecked;
	}

	public PhotoModel(String originalPath) {
		this.originalPath = originalPath;
	}

	public PhotoModel() {
	}

	public String getOriginalPath() {
		return originalPath;
	}

	public void setOriginalPath(String originalPath) {
		this.originalPath = originalPath;
	}

	public boolean isChecked() {
		return isChecked;
	}
	
	
//	@Override
//	public boolean equals(Object o) {
//		if (o.getClass() == getClass()) {
//			PhotoModel model = (PhotoModel) o;
//			if (this.getOriginalPath().equals(model.getOriginalPath())) {
//				return true;
//			}
//		}
//		return false;
//	}

	public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setChecked(boolean isChecked) {
//		System.out.println("checked " + isChecked + " for " + originalPath);
		this.isChecked = isChecked;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((originalPath == null) ? 0 : originalPath.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PhotoModel)) {
			return false;
		}
		PhotoModel other = (PhotoModel) obj;
		if (originalPath == null) {
			if (other.originalPath != null) {
				return false;
			}
		} else if (!originalPath.equals(other.originalPath)) {
			return false;
		}
		return true;
	}

    @Override
    public String toString() {
        return "PhotoModel{" +
                "type=" + type +
                ", thumbPath='" + thumbPath + '\'' +
                ", originalPath='" + originalPath + '\'' +
                '}';
    }
}
