package top.xinstudio.ziputils;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import top.xinstudio.ziputilslib.ZipUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (可选) 是否显示线程信息，默认为ture
                .methodCount(0)         // (可选) 显示的方法行数，默认为2
                .methodOffset(7)        // (可选)
                .tag("ZipUtils")   // (可选) 每个日志的全局标记. 默认 PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));


        Button button = findViewById(R.id.Button);
        button.setOnClickListener(v -> {
            // TODO: 2023/4/14
            ZipUtils.unzipFileWithPassword("path/to/your.zip", "destination/path", "yourpassword", new ZipUtils.Callback() {
                @Override
                public void onProgress(int percentDone) {
                    Logger.d("解压进度: " + percentDone + "%");
                }

                @Override
                public void onSuccess() {
                    Logger.d("解压完成");
                }

                @Override
                public void onError(String errorMessage) {
                    Logger.d("解压失败" + errorMessage);
                }
            });

        });
    }
}