package com.yuanshuaicn.hlsdemo.beans.entity;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

/**
 * camera相机
 * @author ZJ
 *
 */
@Getter
@Setter
public class Camera implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5575352151805386129L;

    private Long id;

	private String url;

	private String remark;

	private int flv;

	private int hls;

	private int ffmpeg;

	private int autoClose;

	private int type = 0;

	private String mediaKey;
}
