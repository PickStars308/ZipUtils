# ZipUtils

`ZipUtils` 是一个基于 [zip4j](https://github.com/srikanth-lingala/zip4j) 实现的简便压缩和解压工具库，支持密码保护、进度回调、异步操作等功能。

## 功能

- 解压和压缩 ZIP 文件
- 支持密码保护的 ZIP 文件解压和压缩
- 支持进度回调，可实时获取解压或压缩进度
- 异步处理，避免阻塞主线程

## 安装

步骤 1.将 JitPack 存储库添加到您的构建文件中
在你项目文件夹中的 build.gradle 中：

```gradle
dependencyResolutionManagement {
   repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
   repositories {
      mavenCentral()
      maven { url 'https://jitpack.io' }
   }
}
```

步骤 2.添加依赖项

[![](https://jitpack.io/v/com.gitee.PickStars2024/ZipUtils.svg)](https://jitpack.io/#com.gitee.PickStars2024/ZipUtils)

```gradle
dependencies {
   implementation 'com.gitee.PickStars2024:ZipUtils:Tag'
}
```

请将 Tag 替换为你上传到 JitPack 的库的实际版本号。

## 使用示例

### 1. 解压 ZIP 文件

#### (1) 解压无密码 ZIP 文件
```java
ZipUtils.unzipFile("path/to/your.zip", "path/to/destination", new ZipUtils.Callback() {
   @Override
   public void onProgress(int percentDone) {
      // 处理解压进度
      System.out.println("Progress: " + percentDone + "%");
   }

   @Override
   public void onSuccess() {
      System.out.println("解压成功！");
   }

   @Override
   public void onError(String errorMessage) {
      System.err.println("解压失败：" + errorMessage);
   }
});
```

#### (2) 解压带密码的 ZIP 文件
```java
ZipUtils.unzipFileWithPassword("path/to/your.zip", "path/to/destination", "your_password", new ZipUtils.Callback() {
   @Override
   public void onProgress(int percentDone) {
      System.out.println("Progress: " + percentDone + "%");
   }

   @Override
   public void onSuccess() {
      System.out.println("解压成功！");
   }

   @Override
   public void onError(String errorMessage) {
      System.err.println("解压失败：" + errorMessage);
   }
});
```

### 2. 压缩文件

#### (1) 压缩无密码文件
```java
ZipUtils.zipFile("path/to/yourFile.txt", "path/to/output.zip", new ZipUtils.Callback() {
   @Override
   public void onProgress(int percentDone) {
      System.out.println("Progress: " + percentDone + "%");
   }

   @Override
   public void onSuccess() {
      System.out.println("压缩成功！");
   }

   @Override
   public void onError(String errorMessage) {
      System.err.println("压缩失败：" + errorMessage);
   }
});
```

#### (2) 压缩带密码文件
```java
ZipUtils.zipFileWithPassword("path/to/yourFile.txt", "path/to/output.zip", "your_password", new ZipUtils.Callback() {
   @Override
   public void onProgress(int percentDone) {
      System.out.println("Progress: " + percentDone + "%");
   }

   @Override
   public void onSuccess() {
      System.out.println("压缩成功！");
   }

   @Override
   public void onError(String errorMessage) {
      System.err.println("压缩失败：" + errorMessage);
   }
});
```

## 接口说明

### Callback 接口

`Callback` 接口用于获取异步操作的进度和状态，包含以下方法：

- `onProgress(int percentDone)`：返回压缩或解压的进度百分比。
- `onSuccess()`：当操作成功完成时调用。
- `onError(String errorMessage)`：当操作出错时调用，并返回错误信息。

### runOnMainThread 方法

为了在主线程更新 UI，`Callback` 接口提供了 `runOnMainThread` 方法，用于确保回调在主线程执行。无需额外调用，所有回调默认都会在主线程上运行。

## 注意事项

- 本库使用多线程进行解压和压缩操作，以确保操作不会阻塞主线程。
- 该库基于 `zip4j`，请确保在项目中正确引入 `zip4j` 依赖。

## 许可证

Apache License