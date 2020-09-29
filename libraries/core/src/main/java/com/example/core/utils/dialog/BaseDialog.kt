package com.example.core.utils.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.annotation.StringRes

class BaseDialog {
    private lateinit var dialog: AlertDialog
    private lateinit var builder: AlertDialog.Builder
    private lateinit var context: Context

    constructor(context: Context?) {
        builder = AlertDialog.Builder(context)
            .setOnDismissListener { initialize() }
        //        RxBus.getInstance().subscribe(value -> {
//            if (!(value instanceof String))
//                return;
//            if (value.equals(Define.Bus.OPEN_NOTIFICATION) || value.equals(Define.Bus.SHOW_SEARCH_RESULT)) {
//                if (dialog != null) {
//                    dialog.dismiss();
//                }
//            }
//        });
    }

    constructor(
        context: Context?, title: String?, message: String?,
        positive: String?, onPositiveClick: OnDialogListener?,
        negative: String?, onNegativeClick: OnDialogListener?,
        isCancelable: Boolean
    ) {
        builder = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                positive
            ) { dialog1: DialogInterface, _: Int ->
                dialog1.cancel()
                onPositiveClick?.onClick()
            }
            .setNegativeButton(
                negative
            ) { dialog1: DialogInterface, _: Int ->
                dialog1.cancel()
                onNegativeClick?.onClick()
            }
            .setOnDismissListener { initialize() }
            .setCancelable(isCancelable)
        dialog = builder.create()
        dialog.show()
        //        RxBus.getInstance().subscribe(value -> {
//            if (!(value instanceof String))
//                return;
//            if (value.equals(Define.Bus.OPEN_NOTIFICATION)) {
//                if (dialog != null) {
//                    dialog.dismiss();
//                }
//            }
//        });
    }

    fun setTitle(title: String?): BaseDialog {
        builder.setTitle(title)
        return this
    }

    fun setTitle(@StringRes title: Int): BaseDialog {
        builder.setTitle(title)
        return this
    }

    fun setMessage(message: String?): BaseDialog {
        builder.setMessage(message)
        return this
    }

    fun setMessage(@StringRes message: Int): BaseDialog {
        builder.setMessage(message)
        return this
    }

    fun setPositiveButton(label: String?, onDialogListener: OnDialogListener?): BaseDialog {
        builder.setPositiveButton(
            label
        ) { dialog: DialogInterface, _: Int ->
            dialog.cancel()
            initialize()
            onDialogListener?.onClick()
        }
        return this
    }

    fun setPositiveButton(@StringRes label: Int, onDialogListener: OnDialogListener?): BaseDialog {
        builder.setPositiveButton(
            label
        ) { dialog: DialogInterface, _: Int ->
            dialog.cancel()
            initialize()
            onDialogListener?.onClick()
        }
        return this
    }

    fun setNegativeButton(label: String?, onDialogListener: OnDialogListener?): BaseDialog {
        builder.setNegativeButton(
            label
        ) { dialog: DialogInterface, _: Int ->
            dialog.cancel()
            initialize()
            onDialogListener?.onClick()
        }
        return this
    }

    fun setNegativeButton(@StringRes label: Int, onDialogListener: OnDialogListener?): BaseDialog {
        builder.setNegativeButton(
            label
        ) { dialog: DialogInterface, _: Int ->
            dialog.cancel()
            initialize()
            onDialogListener?.onClick()
        }
        return this
    }

    fun setCancelable(isCancelable: Boolean): BaseDialog {
        builder.setCancelable(isCancelable)
        builder.setOnCancelListener { initialize() }
        return this
    }

    fun setCancelable(
        isCancelable: Boolean,
        onDialogListener: OnDialogListener?
    ): BaseDialog {
        builder.setCancelable(isCancelable)
        builder.setOnCancelListener { dialog: DialogInterface ->
            dialog.cancel()
            initialize()
            onDialogListener?.onClick()
        }
        return this
    }

    fun setOnDissmiss(
        onDialogListener: OnDialogListener?
    ): BaseDialog {
        builder.setOnDismissListener {
            onDialogListener?.onDissmiss()
        }
        return this
    }

    fun show() {
        if (!isShown) {
            dialog = builder.create()
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()
            forceShown()
        }
    }

    interface OnDialogListener {
        fun onClick()
        fun onDissmiss()
    }

    companion object {
        private var isShown = false

        private fun forceShown() {
            isShown = true
        }

        private fun initialize() {
            isShown = false
        }
    }
}