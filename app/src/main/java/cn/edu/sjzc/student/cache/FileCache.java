package cn.edu.sjzc.student.cache;

import android.content.Context;

import cn.edu.sjzc.student.util.FileManagerUtil;

public class FileCache extends AbstractFileCache {

	public FileCache(Context context) {
		super(context);

	}

	@Override
	public String getSavePath(String url) {
		String filename = String.valueOf(url.hashCode());
		return getCacheDir() + filename;
	}

	@Override
	public String getCacheDir() {

		return FileManagerUtil.getSaveFilePath();
	}

}
