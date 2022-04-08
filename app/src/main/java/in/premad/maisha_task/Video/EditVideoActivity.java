package in.premad.maisha_task.Video;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.gowtham.library.utils.TrimVideo;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import in.premad.maisha_task.R;

public class EditVideoActivity extends AppCompatActivity {
    private VideoView videoView;
    private Button btnSelectVideo;
    private final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_video);
        videoView = findViewById(R.id.videoView);
        btnSelectVideo = findViewById(R.id.btnSelectVideo);

        btnSelectVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkP();
            }
        });
    }

    private void checkP (){
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_PICK);
                        intent.setType("video/*");
                        startActivityForResult(intent,PICK_IMAGE);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();

                    }
                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==PICK_IMAGE&&resultCode==RESULT_OK){
            if (data!=null){
                Uri selectVideo = data.getData();
                trimVideo(selectVideo);
            }
        }
        if (requestCode == TrimVideo.VIDEO_TRIMMER_REQ_CODE && data != null) {
            Uri uri = Uri.parse(TrimVideo.getTrimmedVideoPath(data));

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(uri);
            sendBroadcast(intent);

            showVideo(uri);
        }
    }

    private void trimVideo (Uri videoUri){
        TrimVideo.activity(String.valueOf(videoUri))
                .setDestination("/storage/emulated/0/DCIM/Trim video")
                .start(this);
    }

    private void showVideo (Uri trimVideo){
        videoView.setVideoURI(trimVideo);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
    }
}