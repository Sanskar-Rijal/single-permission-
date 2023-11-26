package com.example.permissioncheck
import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
   private val requestpermession : ActivityResultLauncher<String>
   = registerForActivityResult(
       ActivityResultContracts.RequestPermission()
   )
   {
       isGranted :Boolean ->
       if(isGranted)
       {
           Toast.makeText(this,"permission granted",Toast.LENGTH_SHORT).show()
       }
       else
       {
           Toast.makeText(this,"permession denied",Toast.LENGTH_SHORT).show()
       }
   }
    //we need activity result launcher for that
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btncamerapermession : Button =findViewById<Button>(R.id.btn_camera_per)
        btncamerapermession.setOnClickListener {
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) //checking the android version and shouldshowrequestpermission will show why we need this permission if user denies it
            //soo we need to create a alert dialogue
            {
                showRationalDialogue("permission  requires camera access","camera cannot be used because camera access is denied")
            }
            else
            {
                requestpermession.launch(Manifest.permission.CAMERA)
            }
        }
    }
    /**
     * shows rational dialogue for displaying why the app needs permessino
     * only shown if user has denied the permission which was asked previously
     */
    private  fun showRationalDialogue (title: String ,message: String)
    {
        val builder :AlertDialog.Builder =AlertDialog.Builder(this)
        builder.setTitle(title).setMessage(message).setPositiveButton("cancel")//what should happen if cancel button is pressed is given below in {}
        {
                dialog, _-> dialog.dismiss()
        }
        builder.create().show()
    }
}

