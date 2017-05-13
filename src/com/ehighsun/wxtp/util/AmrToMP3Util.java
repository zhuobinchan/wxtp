package com.ehighsun.wxtp.util;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;

import java.io.File;

public class AmrToMP3Util {
	public static String changeToMp3(String sourcePath, String targetPath) {

		File source = new File(sourcePath);
		if (!source.isFile() || !source.exists()) {
			System.out.println("删除单个文件" + sourcePath + "失败！");
			return null;
		}
		File target = new File(targetPath);
		AudioAttributes audio = new AudioAttributes();
		Encoder encoder = new Encoder();

		audio.setCodec("libmp3lame");
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audio);

		try {
			encoder.encode(source, target, attrs);
		} catch (Exception e) {
			System.out.println("异常：语音amr转mp3时错误！");
		}
		return targetPath;
	}

}
