package com.yuanshuaicn.hlsdemo.thread;

import cn.hutool.core.collection.CollUtil;
import com.yuanshuaicn.hlsdemo.beans.dto.CameraDto;
import com.yuanshuaicn.hlsdemo.common.MediaConstant;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * hls切片
 * @author ZJ
 *
 */
@Slf4j
public class MediaTransferHls extends MediaTransfer {
	
	/**
	 * 运行状态
	 */
	private boolean running = false;
	
	private boolean enableLog = false;
	private Process process;
	private Thread inputThread;
	private Thread errThread;
	
	private int port = 8888;
	
	/**
	 * 相机
	 */
	private CameraDto cameraDto;

	/**
	 * cmd
	 */
	private List<String> command = new ArrayList<>();
	
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * 
	 * @param cameraDto
	 */
	public MediaTransferHls(CameraDto cameraDto, int port) {
		this.cameraDto = cameraDto;
		this.port = port;
		buildCommand();
	}

	/**
	 * String cmd = "ffmpeg -i rtsp://admin:VZCDOY@192.168.2.120:554/Streaming/Channels/102 -r 25 -g 25 -c:v libx264 -c:a aac -f hls -hls_list_size 1 -hls_wrap 6 -hls_time 1 -hls_base_url /ts/"+22+"/ -method put http://localhost:8888/record/"+22+"/out.m3u8";
	 */
	private void buildCommand() {
		
		command.add(System.getProperty(MediaConstant.ffmpegPathKey));
        
		// 本地文件 解决文件分片速度过快导致前端跳帧问题
		if(cameraDto.getType() == 1) {
			command.add("-re"); 
		}
		
		command.add("-i");
		command.add(cameraDto.getUrl());
		command.add("-r");
		command.add("25");
		command.add("-g");
		command.add("25");
		command.add("-c:v");
		command.add("libopenh264");	//javacv 1.5.5 无法使用libx264
		command.add("-c:a");
		command.add("aac");
		command.add("-f");
		command.add("hls");
		command.add("-hls_list_size");
		command.add("1");
		command.add("-hls_wrap");
		command.add("6");
		command.add("-hls_time");
		command.add("1");
		command.add("-hls_base_url");
		command.add("/ts/"+cameraDto.getMediaKey()+"/");
		command.add("-method");
		command.add("put");
		command.add("http://localhost:"+port+"/record/"+cameraDto.getMediaKey()+"/out.m3u8");
		
	}
	
	/**
	 * 执行
	 */
	public void execute() {
		String join = CollUtil.join(command, " ");
		log.info(join);
		
		try {
			process = new ProcessBuilder(command).start();
			running = true;
			dealStream(process);
		} catch (IOException e) {
			running = false;
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭
	 */
	public void stop() {
		this.running = false;
		try {
			process.destroy();
			log.info("关闭媒体流-ffmpeg，{} ", cameraDto.getUrl());
		} catch (Exception e) {
			process.destroyForcibly();
		}
	}


	/**
	 * 控制台输出
	 * 
	 * @param process
	 */
	private void dealStream(Process process) {
		if (process == null) {
			return;
		}
		// 处理InputStream的线程
		inputThread = new Thread() {
			@Override
			public void run() {
				BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;
				try {
					while (running) {
						line = in.readLine();
						if (line == null) {
							break;
						}
						if (enableLog) {
							log.info("output: " + line);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						running = false;
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		// 处理ErrorStream的线程
		errThread = new Thread() {
			@Override
			public void run() {
				BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				String line = null;
				try {
					while (running) {
						line = err.readLine();
						if (line == null) {
							break;
						}
						if (enableLog) {
							log.info("err: " + line);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						running = false;
						err.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};

		inputThread.start();
		errThread.start();
	}
	
	@Override
	public void hasClient() {
	
	}
	
}
