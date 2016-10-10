package appewtc.masterung.iotfarmer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, userEditText, passwordEditText;
    private ImageView imageView;
    private String nameString, userString, passwordString,
            pathImageString, nameImageString;
    private boolean aBoolean = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind Widget
        nameEditText = (EditText) findViewById(R.id.editText);
        userEditText = (EditText) findViewById(R.id.editText2);
        passwordEditText = (EditText) findViewById(R.id.editText3);
        imageView = (ImageView) findViewById(R.id.imageView);

        //Image Controller
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Choose My Image
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "โปรเลือกรูปภาพ"), 0);

            }   // onClick
        });

    }   // Main Method

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 0) && (resultCode == RESULT_OK)) {

            aBoolean = false;

            Log.d("10octV1", "Choose Success");

            //Find Path of Image Choose
            Uri uri = data.getData();
            pathImageString = myFindPath(uri);
            Log.d("10octV1", "pathImageString ==> " + pathImageString);

            //Setup Image Choose to ImageView
            try {

                Bitmap bitmap = BitmapFactory
                        .decodeStream(getContentResolver()
                                .openInputStream(uri));
                imageView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }   // if


        //Find Name of Image Choose
        nameImageString = pathImageString.substring(pathImageString.lastIndexOf("/"));
        Log.d("10octV1", "nameImageString ==> " + nameImageString);

    }   // onActivityResult

    private String myFindPath(Uri uri) {

        String result = null;
        String[] strings = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, strings, null, null, null);

        if (cursor != null) {

            cursor.moveToFirst();
            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            result = cursor.getString(index);

        } else {
            result = uri.getPath();
        }

        return result;
    }

    public void clickSignUpSign(View view) {

        //Get Value From Edit Text
        nameString = nameEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check Space
        if (nameString.equals("") || userString.equals("") || passwordString.equals("")) {
            // Have Space
            MyAlert myAlert = new MyAlert(SignUpActivity.this, R.drawable.doremon48,
                    "มีช่องว่าง", "กรุณากรอก ทุกช่องด้วย คะ");
            myAlert.myDialog();
        } else if (aBoolean) {
            //Non Choose Image
            MyAlert myAlert = new MyAlert(SignUpActivity.this, R.drawable.bird48,
                    "ยังไม่ได้เลือกรูป", "กรุณาเลือกรูปภาพ ด้วยคะ");
            myAlert.myDialog();

        } else {
            // Everything OK


        }


    }   // clickSign

}   // Main Class
