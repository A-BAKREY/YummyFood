package com.kt.core.utlities

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.Filter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kt.core.R
import com.kt.core.featurelist.AdapterList
import com.kt.core.featurelist.ModelList
import com.usecase.network.prefclearAll


fun AppCompatActivity.dialogForgetPwd(onOkClicked: () -> Unit) {
    //==========================================//
    val alert = android.app.AlertDialog.Builder(this)
    val edittext = android.widget.EditText(this)
    alert.setMessage(getString(R.string.forget_pwd_enter_your_email))
    alert.setView(edittext)
    alert.setPositiveButton(getString(R.string.ok)) { dialog, whichButton ->
        val email = edittext.text.toString()
        //==================================================//
        if (!email.isEmpty()) {
            onOkClicked()
        }
        //===================================================//
    }
    alert.setNegativeButton(getString(R.string.cancel)) { dialog, whichButton ->
    }

    alert.show()
}


fun AppCompatActivity.dialogExitApp() {

    val alertDialog = AlertDialog.Builder(this)
        //set icon
        .setIcon(android.R.drawable.ic_dialog_alert)
        //set title
        .setTitle(getString(R.string.confirm_exit_app_title))
        //set message
        .setMessage(getString(R.string.confirm_exit_app_msg))
        //set positive button
        .setPositiveButton(getString(R.string.ok), DialogInterface.OnClickListener { dialog, i ->

            try {
                finishAffinity()
                if (Build.VERSION.SDK_INT >= 21) {
                    finishAndRemoveTask()
                }

                val pid = android.os.Process.myPid()
                android.os.Process.killProcess(pid)
            } catch (e: Exception) {

            }

        })
        //set negative button
        .setNegativeButton(
            getString(R.string.cancel),
            DialogInterface.OnClickListener { dialogInterface, i ->


            })
        .show()
}

fun AppCompatActivity.dialogOkCansel(
    title: String,
    message: String,
    icon: Int = 1,
    okPressed: () -> Unit,
) {

    val alertDialog = AlertDialog.Builder(this)
        //set icon
        //  .setIcon(icon)
        //set title
        .setTitle(title)

        //set message
        .setMessage(message)
        //set positive button
        .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, i ->
            okPressed()
        })
        //set negative button
        .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->


        })
        .show()
}

fun AppCompatActivity.dialogOk(title: String, message: String, icon: Int, okPressed: () -> Unit) {

    val alertDialog = AlertDialog.Builder(this)
        //set icon
        .setIcon(icon)
        //set title
        .setTitle(title)
        //set message
        .setMessage(message)
        //set positive button
        .setPositiveButton(getString(R.string.ok), DialogInterface.OnClickListener { dialog, i ->
            okPressed()
        })
        //set negative button

        .show()
}

fun Context.dialogOpenLogIn(title: String, message: String, icon: Int) {

    val alertDialog = AlertDialog.Builder(this)
        .setCancelable(false)
        //set icon
        .setIcon(icon)
        //set title
        .setTitle(title)
        //set message
        .setMessage(message)
        //set positive button
        .setPositiveButton(getString(R.string.ok), DialogInterface.OnClickListener { dialog, i ->
            //todo add routin
            // openActivityByRouting("")
            //todo clear cach
            prefclearAll()
            val startMain = Intent(Intent.ACTION_MAIN)
            startMain.addCategory(Intent.CATEGORY_HOME)
            startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(startMain)
        })
        //set negative button

        .show()
}

fun Context.DialogRetry(title: String, desc: String = "", onRetry: () -> Unit) {
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.custom_dialog_no_connection)

    val dtvCancel = dialog.findViewById(R.id.cancel) as TextView
    dtvCancel.visibility = View.VISIBLE
    val dtvRetry = dialog.findViewById(R.id.retry) as TextView
    val dTitle = dialog.findViewById(R.id.title) as TextView
    val derrorMsg = dialog.findViewById(R.id.errorMsg) as TextView
    dTitle.text = title
    derrorMsg.text = desc
    dtvRetry.setOnClickListener {
        onRetry.invoke()
        dialog.dismiss()
    }
    dtvCancel.setOnClickListener {

        dialog.dismiss()
    }
    val window: Window? = dialog.window
    window?.setLayout(
        WindowManager.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    dialog.show()
}

fun Context.DialogChooseCamOrGallery(onOk: (isCamClicked: Boolean) -> Unit) {
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_camera_gallery)

    val groupCam = dialog.findViewById(R.id.groupCam) as Group
    val groupGallery = dialog.findViewById(R.id.groupGallery) as Group
    val tvCancel = dialog.findViewById(R.id.tvCancel) as TextView


    groupCam.addOnClickListener {
        onOk(true)
        dialog.dismiss()
    }
    groupGallery.addOnClickListener {
        onOk(false)
        dialog.dismiss()
    }
    tvCancel.setOnClickListener {
        dialog.dismiss()
    }
    val window: Window? = dialog.window
    window?.setLayout(
        WindowManager.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    dialog.show()
}


fun Context.DialogMenuOptions(x: String, onOk: (isCamClicked: String, x: String) -> Unit) {
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_loading)

    val groupCam = dialog.findViewById(R.id.groupCam) as Group
    val groupGallery = dialog.findViewById(R.id.groupGallery) as Group
    val tvCancel = dialog.findViewById(R.id.tvCancel) as TextView

    groupCam.addOnClickListener {
        onOk("x", "")
        dialog.dismiss()
    }
    groupGallery.addOnClickListener {
        onOk("x", "")
        dialog.dismiss()
    }
    tvCancel.setOnClickListener {
        onOk("x", "")
        dialog.dismiss()
    }
    val window: Window? = dialog.window
    window?.setLayout(
        WindowManager.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    dialog.show()
}


fun Context.ShowDialogList(title: String, list: List<ModelList>, onOk: (item: ModelList) -> Unit) {
    val adapterList by lazy { AdapterList() }
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_list)

    val tvTitle = dialog.findViewById(R.id.a_list_title) as TextView
    tvTitle.setText(title)
    val rvList = dialog.findViewById(R.id.cv_list) as RecyclerView
    val tvCancel = dialog.findViewById(R.id.list_Cancel_tv) as TextView
    val tvOk = dialog.findViewById(R.id.list_save_change_tv) as TextView
    tvOk.setOnClickListener {
        if(adapterList.selectedId!=-1){
            onOk( adapterList.selectedItem )
            dialog.dismiss()
        }

    }
    tvCancel.setOnClickListener { dialog.dismiss() }
    val edQuery = dialog.findViewById(R.id.edQuery) as EditText


    //init rv
    rvList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    rvList.adapter = adapterList
    adapterList.addItems(list)
    adapterList.selectedItem=list.get(0)
    adapterList.setOnClickListener { clickedView, item, position ->
        adapterList.selectedId = position
        adapterList.selectedItem=item
        adapterList.notifyDataSetChanged()
    }
    edQuery.onChange {
        QueryAdapter(adapterList, it, list)
    }
    val window: Window? = dialog.window
    window?.setLayout(
        WindowManager.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    dialog.show()
}
fun QueryAdapter(adapterList: AdapterList, query: String, list: List<ModelList>){
    //adapterList.selectedId=-1

    var filteredList = list.filter {
        it.name.contains(query, true)
    }
    if(filteredList.size>0){
        adapterList.selectedItem=filteredList.get(0)
    }else{
        adapterList.selectedId=-1
    }
    //adapterList.selectedItem=filteredList.get(0)
    if(query.equals("")){
        filteredList=list
        var filter: Filter? = adapterList.getFilter()
        filter = null
        adapterList.selectedItem=list.get(0)
        adapterList.selectedId=0
        adapterList.fill(list)

    }else{
        // adapterList.submitFilteredList(filteredList.toMutableList())
        // adapterList.filter.filter(query)
        adapterList.fill(filteredList.toMutableList())
    }


}

