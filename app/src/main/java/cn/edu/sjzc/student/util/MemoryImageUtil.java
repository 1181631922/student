package cn.edu.sjzc.student.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class MemoryImageUtil {
	private final int maxMemory = (int) Runtime.getRuntime().maxMemory();
	private final int cacheSize = maxMemory / 5;

	private LruCache<String, Bitmap> mLruCache = new LruCache<String, Bitmap>(
			cacheSize) {

		//重写sizeof（）方法
		@Override
		protected int sizeOf(String key, Bitmap bitmap) {
			// TODO Auto-generated method stub
			return bitmap.getRowBytes()*bitmap.getHeight()/1024;
		}

	};
}
