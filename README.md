### `ZipUtils` 类介绍

`ZipUtils` 是一个用于压缩和解压缩 ZIP 文件的工具类，封装了一些常用的方法以处理带密码的压缩文件和普通压缩文件。该类利用了第三方库 [Zip4j](https://github.com/srikanth-lingala/zip4j) 来实现 ZIP 文件的操作。

#### 主要功能

1. **解压带密码的压缩文件**：
    - 方法：`unzipFileWithPassword(String zipFilePath, String destinationPath, String password, Callback callback)`
    - 描述：此方法可解压带有密码保护的 ZIP 文件。解压过程是异步的，通过回调接口 `Callback` 可以实时获取解压进度、成功或失败的信息。

2. **解压文件**：
    - 方法：`unzipFile(String zipFilePath, String destinationPath, Callback callback)`
    - 描述：此方法可解压没有密码保护的 ZIP 文件，功能与 `unzipFileWithPassword` 类似，支持进度回调。

3. **压缩带密码的文件**：
    - 方法：`zipFileWithPassword(String sourceFilePath, String zipFilePath, String password, Callback callback)`
    - 描述：此方法可将指定源文件压缩为 ZIP 文件并设置密码保护，支持异步处理和进度回调。

4. **压缩文件**：
    - 方法：`zipFile(String sourceFilePath, String zipFilePath, Callback callback)`
    - 描述：此方法可将指定源文件压缩为 ZIP 文件，功能与压缩带密码的文件类似。

#### 异步处理与进度回调

`ZipUtils` 类中的解压和压缩方法均在独立的线程中执行，以避免阻塞主线程。通过 `ProgressMonitor` 监控进度，并使用 `Callback` 接口来传递进度信息、成功或错误消息。可以实现以下方法来处理这些回调：
- `onProgress(int percentDone)`：接收当前进度的百分比。
- `onSuccess()`：操作成功完成。
- `onError(String errorMessage)`：操作中发生错误，并提供错误消息。

#### 使用示例

```java
ZipUtils.unzipFileWithPassword("path/to/your.zip", "destination/path", "yourpassword", new ZipUtils.Callback() {
    @Override
    public void onProgress(int percentDone) {
        System.out.println("解压进度: " + percentDone + "%");
    }

    @Override
    public void onSuccess() {
        System.out.println("解压成功!");
    }

    @Override
    public void onError(String errorMessage) {
        System.err.println("解压失败: " + errorMessage);
    }
});
```

#### 注意事项

- 在使用压缩和解压缩功能前，请确保已加入 Zip4j 依赖库。
- 回调方法应在主线程中进行 UI 更新，以避免潜在的线程安全问题。

`ZipUtils` 类通过其易于使用的方法和回调机制，简化了文件压缩和解压的过程，非常适合需要处理 ZIP 文件的场景。