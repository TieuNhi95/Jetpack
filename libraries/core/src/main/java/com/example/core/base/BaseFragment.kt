package com.example.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

abstract class BaseFragment<BD : ViewDataBinding> : Fragment() {

    lateinit var binding: BD

    private val compositeDisposable = CompositeDisposable()

    @LayoutRes
    abstract fun layoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        setOnClick()

        bindingStateView()

        bindingAction()
    }

    open fun setOnClick() {

    }

    open fun initView(){

    }

    open fun bindingStateView(){

    }

    open fun bindingAction(){

    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        var animation = super.onCreateAnimation(transit, enter, nextAnim)

        if (animation == null && nextAnim != 0) {
            animation = AnimationUtils.loadAnimation(activity, nextAnim)
        }

        if (animation != null) {
            view?.setLayerType(View.LAYER_TYPE_HARDWARE, null)

            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    view?.setLayerType(View.LAYER_TYPE_NONE, null)
                    onFinishAnimStart()
                }

                override fun onAnimationStart(animation: Animation?) {
                }

            })
        } else {
            onFinishAnimStart()
        }

        return animation
    }

    protected open fun onFinishAnimStart() {
        //do something common if you want.
        //pass data to previous screen.
        Timber.d("onFinishAnimStart in Fragment")

    }

    protected val isDoubleClick: Boolean
        get() {
            if (activity == null) {
                return false
            }
            return if (activity is BaseActivity<*>) {
                (activity as BaseActivity<*>?)!!.isDoubleClick
            } else false
        }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    fun showLoading() {
        if (activity != null && activity is BaseActivity<*>) {
            (activity as BaseActivity<*>?)!!.showLoading()
        }
    }

    fun hideLoading() {
        if (activity != null && activity is BaseActivity<*>) {
            (activity as BaseActivity<*>?)!!.hiddenLoading()
        }
    }

//    private fun showAlertDialog(message: String) {
//        BaseDialog(requireContext())
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
//
//    fun showAlertDialog(@IdRes message: Int) {
//        BaseDialog(requireContext())
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