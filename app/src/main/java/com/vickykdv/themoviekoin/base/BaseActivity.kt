package com.vickykdv.themoviekoin.base

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.vickykdv.themoviekoin.BuildConfig
import com.vickykdv.themoviekoin.R
import com.vickykdv.themoviekoin.databinding.BottomDlgNointernetBinding

abstract class BaseActivity : AppCompatActivity() {

    private val TAG = BaseActivity::class.java.simpleName

    private var toolBar: Toolbar? = null
    private var mInflater: LayoutInflater? = null
    private var mActionBar: ActionBar? = null

    private val baseFragmentManager: FragmentManager
        get() = super.getSupportFragmentManager()


    /**
     * Set layout
     *
     * @return
     */
    abstract fun setLayout() : View


    /**
     * On create activity
     *
     * @param savedInstanceState
     */
    abstract fun onCreateActivity(savedInstanceState: Bundle?)




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayout())
        mInflater = LayoutInflater.from(this)
        onCreateActivity(savedInstanceState)



    }


    /**
     * Open activity
     *
     * @param clazz
     * @param isFinished
     * @param extras
     * @receiver
     */
    fun openActivity(
        clazz: Class<*>,
        isFinished: Boolean = false,
        extras: Bundle.() -> Unit = { }
    ){
        val intent = Intent(this, clazz)
        intent.putExtras(Bundle().apply(extras))
        startActivity(intent)
        if(isFinished) finish()
    }

    fun openActivityResult(
        clazz: Class<*>,
        requestCode:Int = BaseConstants.DEFAULT_REQ_CODE,
        extras: Bundle.() -> Unit = { }
    ){
        val intent = Intent(this, clazz)
        intent.putExtras(Bundle().apply(extras))
        startActivityForResult(intent,requestCode)
    }


    /**
     * Setup toolbar
     *
     * @param needHomeButton
     * @param onClickListener
     * @param titleTolbar
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected fun setupToolbar(
        setupToolBar: Toolbar? = null,
        needHomeButton: Boolean = true,
        onClickListener: View.OnClickListener? = null,
        titleTolbar: CharSequence? = "",
        @DrawableRes
        homeIndicator: Int = R.drawable.backbutton,
        elevation: Float = 24f,
        layout: Int = R.layout.custometoolbar,
        showTitle: Boolean = false,
        homeButtonEnable: Boolean = false
    ) {

        toolBar = setupToolBar
        setSupportActionBar(setupToolBar)
        mActionBar = supportActionBar
        if (mActionBar != null) {
            mActionBar!!.setHomeButtonEnabled(homeButtonEnable)
            supportActionBar!!.setCustomView(layout)
            supportActionBar!!.setDisplayHomeAsUpEnabled(needHomeButton)
            supportActionBar!!.setDisplayShowTitleEnabled(showTitle)
            supportActionBar!!.title = titleTolbar
            toolBar!!.elevation = elevation
            setupToolBar?.setTitleTextColor(resources.getColor(R.color.black))
            supportActionBar!!.setHomeAsUpIndicator(homeIndicator)
        }

        try {
            val txtToolbarTitle: TextView = setupToolBar!!.findViewById(R.id.txtToolbarTitle)
            txtToolbarTitle.text = titleTolbar
        }catch (e: Exception){
            logDebug("txtTitle not found!")
        }

        if (onClickListener != null)
            setupToolBar!!.setNavigationOnClickListener(onClickListener)
    }


    override fun setTitle(title: Int) {
        super.setTitle(title)
        if (mActionBar != null)
            mActionBar!!.title = getString(title)
    }




    /**
     * Show toast
     *
     * @param message
     * @param short
     */
    fun Context.showToast(message: String, short: Int = 0) {
        Toast.makeText(this, message, short).show()
    }

    override fun onBackPressed() {
        if (baseFragmentManager.backStackEntryCount > 0) {
            baseFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }



    /**
     * Hide
     *
     * @param view
     */
    fun hide(view: View){
        view.visibility = View.GONE
    }

    /**
     * Show
     *
     * @param view
     */
    fun show(view: View){
        view.visibility = View.VISIBLE
    }

    /**
     * Disable
     *
     * @param view
     */
    fun disable(view: View){
        view.isEnabled = false
    }

    /**
     * Enable
     *
     * @param view
     */
    fun enable(view: View){
        view.isEnabled = true
    }

    /**
     * Invisible
     *
     * @param view
     */
    fun invisible(view: View){
        view.visibility = View.INVISIBLE
    }

    /**
     * Log debug
     *
     * @param message
     */
    fun logDebug(message: String){
        if(BuildConfig.DEBUG) Log.d("Logging", message)
    }


    /**
     * Handle delay
     *
     * @param delay
     * @param process
     * @receiver
     */

    fun handlerDelay(delay: Long = 3000, process: () -> Unit){
        Handler(mainLooper).postDelayed({
            process()
        }, delay)
    }


    /**
     * Show dialog error internet
     *
     * @param onRefresh
     */
    fun showDialogErrorInternet(
        refreshClick: (() -> Unit)? = null,
    ){

        val binding = BottomDlgNointernetBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        with(binding) {
            btnRefresh.setOnClickListener {
                dialog.dismiss()
                refreshClick?.invoke()
            }

            btnViewSetting.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    /**
     * Is connection ready
     *
     * @param onReady
     * @param onRefresh
     * @receiver
     */
    fun isConnectionReady(
        onReady: () -> Unit,
        onRefresh: (() -> Unit)? = null,
    ){
        if(isInternetAvailable()){
            logDebug("isConnectionReady: Net Ready!!")
            onReady()
        }else{
            logDebug("isConnectionReady: Net Failed!!")
            vibrateEffect()
            showDialogErrorInternet {
                onRefresh?.invoke()
            }
        }
    }

    /**
     * Is internet available
     *
     * @return
     */
    private fun isInternetAvailable(): Boolean {
        var result = false
        val connManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connManager.activeNetwork ?: return false
            val actNw =
                connManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connManager.run {
                connManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }


    fun vibrateEffect() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(100)
        }
    }

    fun showError(
        refreshClick: (() -> Unit)? = null,
    ) {
        val binding = BottomDlgNointernetBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        with(binding) {
            btnRefresh.setOnClickListener {
                dialog.dismiss()
                refreshClick?.invoke()
            }

            btnViewSetting.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()
    }


}