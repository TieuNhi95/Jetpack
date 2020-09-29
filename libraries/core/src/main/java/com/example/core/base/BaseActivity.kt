package com.example.core.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.core.utils.Constants
import com.example.core.utils.dialog.LoadingDialog
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.lang.ref.WeakReference

abstract class BaseActivity<BD : ViewDataBinding> : AppCompatActivity() {


    protected lateinit var binding: BD

    private var lastTimeClick: Long = 0

    private val compositeDisposable = CompositeDisposable()

    @LayoutRes
    protected abstract fun layoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(WeakReference(this).get()!!, layoutId())
        binding.lifecycleOwner = this

    }


    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }

    fun showLoading() {
        LoadingDialog.getInstance(this)?.show()
    }

    fun hiddenLoading() {
        LoadingDialog.getInstance(this)?.hidden()
    }

    //click able
    val isDoubleClick: Boolean
        get() {
            val timeNow = System.currentTimeMillis()
            if (timeNow - lastTimeClick >= Constants.DURATION_TIME_CLICKABLE) {
                //click able
                lastTimeClick = timeNow
                return false
            }
            return true
        }

    override fun onStop() {
        super.onStop()
        compositeDisposable.dispose()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Timber.d("onBackPressed in activity")
    }

//    private fun showAlertDialog(message: String) {
//        BaseDialog(this)
//            .setMessage(message)
//            .setOnDissmiss(object : BaseDialog.OnDialogListener {
//                override fun onClick() {
//                    //do nothing
//                }
//
//                override fun onDissmiss() {
//                    viewModel.messageError.value = ""
//                }
//
//            })
//            .setPositiveButton(R.string.ok, null)
//            .show()
//    }

//    fun showAlertDialog(@IdRes message: Int) {
//        BaseDialog(this)
//            .setMessage(message)
//            .setOnDissmiss(object : BaseDialog.OnDialogListener {
//                override fun onClick() {
//                    //do nothing
//                }
//
//                override fun onDissmiss() {
//                    viewModel.messageError.value = ""
//                }
//
//            })
//            .setPositiveButton(R.string.ok, null)
//            .show()
//    }
}