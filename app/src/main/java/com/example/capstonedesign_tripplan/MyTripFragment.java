package com.example.capstonedesign_tripplan;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTripFragment extends Fragment {
    private final String TAG = "java.StorageActivity";
    private Context context; //context에 대해서 fragment에서 getcontentresolver사용법 스토리지 접근 방법
    final int GALLERY_CODE = 10;
    private FirebaseStorage storage;
    private String[] permissionList = {Manifest.permission.READ_EXTERNAL_STORAGE};
    ImageView image;
    Uri file;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_trip, container, false);
        storage = FirebaseStorage.getInstance();
        image = v.findViewById(R.id.addphoto_image);

        context = container.getContext();

        Button uplaodBtn = v.findViewById(R.id.btnUpload);
        Button chooseBtn = v.findViewById(R.id.chooseBtn);

        /*uplaodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });*/
        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GALLERY_CODE);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_CODE){
            if (resultCode == Activity.RESULT_OK){
                file = Uri.fromFile(new File(getPath(data.getData())));
                StorageReference storageRef = storage.getReferenceFromUrl("gs://capstonedesign-trip.appspot.com/");
                StorageReference riversRef = storageRef.child("photo/" + file.getLastPathSegment());
                UploadTask uploadTask = riversRef.putFile(file);
                try{
                    InputStream in = context.getContentResolver().openInputStream(data.getData());
                    Bitmap bm = BitmapFactory.decodeStream(in);
                    image.setImageBitmap(bm);
                    assert in != null;
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(context, "사진이 정상적으로 업로드 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(context, "사진이 정상적으로 업로드 되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
    }

    private String getPath(Uri uri) {
        String [] proj={MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(context,uri,proj,null,null,null);
        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(index);
    }
    /*private void uploadFile() {
        //업로드할 파일이 있으면 수행
        if (file != null) {
            //업로드 진행 Dialog 보이기
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();

            //storage
            FirebaseStorage storage = FirebaseStorage.getInstance();

            //Unique한 파일명을 만들자.
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();
            String filename = formatter.format(now) + ".png";
            //storage 주소와 폴더 파일명을 지정해 준다.
            StorageReference storageRef = storage.getReferenceFromUrl("gs://yourStorage.appspot.com").child("images/" + filename);
            //올라가거라...
            storageRef.putFile(file)
                    //성공시
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                            Toast.makeText(context.getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(context.getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //진행중
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") //이걸 넣어 줘야 아랫줄에 에러가 사라진다. 넌 누구냐?
                                    double progress = (100 * taskSnapshot.getBytesTransferred()) /  taskSnapshot.getTotalByteCount();
                            //dialog에 진행률을 퍼센트로 출력해 준다
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                        }
                    });
        } else {
            Toast.makeText(context.getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }*/
    public void checkPermission(){
        //현재 버전 6.0 미만이면 종료 --> 6이후 부터 권한 허락
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;

        //각 권한 허용 여부를 확인
        for(String permission : permissionList){
            int chk = context.checkCallingOrSelfPermission(permission);
            //거부 상태라면
            if(chk == PackageManager.PERMISSION_DENIED){
                //사용자에게 권한 허용여부를 확인하는 창을 띄운다.
                requestPermissions(permissionList,0); //권한 검사 필요한 것들만 남는다.
                break;
            }
        }
    }
}