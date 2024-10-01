package top.xinstudio.ziputilslib;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.progress.ProgressMonitor;

import java.io.File;
import java.util.Collections;

public class ZipUtils {

	/**
	 * 解压带密码的压缩文件
	 *
	 * @param zipFilePath
	 * @param destinationPath
	 * @param password
	 * @param callback
	 */
	public static void unzipFileWithPassword(String zipFilePath, String destinationPath, String password, final Callback callback) {
		try {
			ZipFile zipFile = new ZipFile(zipFilePath);
			if (zipFile.isEncrypted()) {
				zipFile.setPassword(password.toCharArray());
			}

			zipFile.setRunInThread(true); // 使用多线程解压缩
			zipFile.extractAll(destinationPath); // 开始解压缩

			final ProgressMonitor progressMonitor = zipFile.getProgressMonitor();

			new Thread(new Runnable() {
				@Override
				public void run() {
					while (progressMonitor.getState() == ProgressMonitor.State.BUSY) {
						int percentDone = progressMonitor.getPercentDone();
						if (callback != null) {
							callback.onProgress(percentDone);
						}
						try {
							Thread.sleep(100); // 休眠100毫秒，避免频繁刷新
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					// 解压完成时确保显示100%
					if (progressMonitor.getResult() == ProgressMonitor.Result.SUCCESS) {
						if (callback != null) {
							callback.onProgress(100); // 强制显示100%
							callback.onSuccess();
						}
					} else if (progressMonitor.getResult() == ProgressMonitor.Result.ERROR) {
						if (callback != null) {
							callback.onError(progressMonitor.getException().getMessage());
						}
					}
				}
			}).start();

		} catch (ZipException e) {
			e.printStackTrace();
			if (callback != null) {
				callback.onError(e.getMessage());
			}
		}
	}

	/**
	 * 解压文件
	 *
	 * @param zipFilePath
	 * @param destinationPath
	 * @param callback
	 */
	public static void unzipFile(String zipFilePath, String destinationPath, final Callback callback) {
		try {
			ZipFile zipFile = new ZipFile(zipFilePath);
			zipFile.setRunInThread(true); // 使用多线程解压缩
			zipFile.extractAll(destinationPath); // 开始解压缩

			final ProgressMonitor progressMonitor = zipFile.getProgressMonitor();

			new Thread(new Runnable() {
				@Override
				public void run() {
					while (progressMonitor.getState() == ProgressMonitor.State.BUSY) {
						int percentDone = progressMonitor.getPercentDone();
						if (callback != null) {
							callback.onProgress(percentDone);
						}
						try {
							Thread.sleep(100); // 休眠100毫秒，避免频繁刷新
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					// 解压完成时确保显示100%
					if (progressMonitor.getResult() == ProgressMonitor.Result.SUCCESS) {
						if (callback != null) {
							callback.onProgress(100); // 强制显示100%
							callback.onSuccess();
						}
					} else if (progressMonitor.getResult() == ProgressMonitor.Result.ERROR) {
						if (callback != null) {
							callback.onError(progressMonitor.getException().getMessage());
						}
					}
				}
			}).start();

		} catch (ZipException e) {
			e.printStackTrace();
			if (callback != null) {
				callback.onError(e.getMessage());
			}
		}
	}


	/**
	 * 压缩带密码的压缩文件
	 *
	 * @param sourceFilePath
	 * @param zipFilePath
	 * @param password
	 * @param callback
	 */
	public static void zipFileWithPassword(String sourceFilePath, String zipFilePath, String password, final Callback callback) {
		try {
			ZipFile zipFile = new ZipFile(zipFilePath);
			zipFile.setPassword(password.toCharArray());
			zipFile.setRunInThread(true); // 使用多线程

			// 开始压缩
			zipFile.addFiles(Collections.singletonList(new File(sourceFilePath)));

			final ProgressMonitor progressMonitor = zipFile.getProgressMonitor();
			new Thread(() -> {
				while (progressMonitor.getState() == ProgressMonitor.State.BUSY) {
					int percentDone = progressMonitor.getPercentDone();
					if (callback != null) {
						callback.onProgress(percentDone);
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				if (progressMonitor.getResult() == ProgressMonitor.Result.SUCCESS) {
					if (callback != null) {
						callback.onProgress(100);
						callback.onSuccess();
					}
				} else if (progressMonitor.getResult() == ProgressMonitor.Result.ERROR) {
					if (callback != null) {
						callback.onError(progressMonitor.getException().getMessage());
					}
				}
			}).start();

		} catch (ZipException e) {
			e.printStackTrace();
			if (callback != null) {
				callback.onError(e.getMessage());
			}
		}
	}


	/**
	 * 压缩文件
	 *
	 * @param sourceFilePath
	 * @param zipFilePath
	 * @param callback
	 */
	public static void zipFile(String sourceFilePath, String zipFilePath, final Callback callback) {
		try {
			ZipFile zipFile = new ZipFile(zipFilePath);
			zipFile.setRunInThread(true); // 使用多线程

			// 开始压缩
			zipFile.addFiles(Collections.singletonList(new File(sourceFilePath)));

			final ProgressMonitor progressMonitor = zipFile.getProgressMonitor();
			new Thread(() -> {
				while (progressMonitor.getState() == ProgressMonitor.State.BUSY) {
					int percentDone = progressMonitor.getPercentDone();
					if (callback != null) {
						callback.onProgress(percentDone);
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				if (progressMonitor.getResult() == ProgressMonitor.Result.SUCCESS) {
					if (callback != null) {
						callback.onProgress(100);
						callback.onSuccess();
					}
				} else if (progressMonitor.getResult() == ProgressMonitor.Result.ERROR) {
					if (callback != null) {
						callback.onError(progressMonitor.getException().getMessage());
					}
				}
			}).start();

		} catch (ZipException e) {
			e.printStackTrace();
			if (callback != null) {
				callback.onError(e.getMessage());
			}
		}
	}


	public interface Callback {
		void onProgress(int percentDone);

		void onSuccess();

		void onError(String errorMessage);
	}

}
