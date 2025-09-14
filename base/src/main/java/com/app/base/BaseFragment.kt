package com.app.base


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.View.OnTouchListener
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.app.base.utils.hideKeyboard


abstract class BaseFragment : Fragment() {
    private var activity : BaseActivity<*>? = null
    private var currentFragment: Fragment? = null
    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnTouchListener(OnTouchListener { v, event ->
            v.hideKeyboard(requireActivity())
            v.clearFocus()
            true
        })
        activity = requireActivity() as BaseActivity<*>
        init()
        initData()
        initAction()
    }


    fun openFragment(fragmentClazz: Class<*>, args: Bundle? = null,
        addBackStack: Boolean = false
    ) {
        activity?.openFragment(fragmentClazz, args, addBackStack)
    }
     fun openFragment(fragmentManager: FragmentManager,fragmentClazz: Class<*>,id : Int, args: Bundle? = null,addBackStack: Boolean= false){
         val tag = fragmentClazz.simpleName
         try {
             val fragment: Fragment
             try {
                 fragment = (fragmentClazz.asSubclass(Fragment::class.java)).newInstance()
                     .apply { arguments = args }

                 val transaction = fragmentManager.beginTransaction()
                 transaction.setCustomAnimations(
                     R.anim.slide_in,
                     R.anim.fade_out,
                     R.anim.fade_in,
                     R.anim.slide_out
                 )
                 if (addBackStack) {
                     transaction.addToBackStack(tag)
                 }
                 transaction.add(id, fragment, tag)
                 transaction.commit()
             } catch (e: java.lang.InstantiationException) {
                 e.printStackTrace()
             } catch (e: IllegalAccessException) {
                 e.printStackTrace()
             }
         } catch (e: Exception) {
             e.printStackTrace()
         }
    }
    fun toast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

    fun TextView.setTextWithAnimation(text: String) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
        startAnimation(animation)
        this.text = text
    }

    fun onBackPressed() {
        activity?.onBackPressed()
    }
    fun showFragment(fragment: Fragment, id: Int = android.R.id.content) {
        if (!isAdded || isDetached) return
        if (currentFragment == fragment) return

        val transaction = childFragmentManager.beginTransaction()

        // Only add animation if fragments are different
        if (currentFragment != null) {
            transaction.setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
        }

        // Hide current fragment
        currentFragment?.takeIf { it.isAdded }?.let { transaction.hide(it) }

        // Show or add target fragment
        when {
            fragment.isAdded -> transaction.show(fragment)
            else -> transaction.add(id, fragment)
        }

        transaction.commit()
        currentFragment = fragment
    }

    abstract fun init()
    abstract fun initData()
    abstract fun initAction()
    fun showLoadingDialog() = activity?.showLoadingDialog()

    fun hideLoadingDialog() = activity?.hideLoadingDialog()

    companion object{
        var onBackPressedCallback : (()-> Unit)?= null
    }

}