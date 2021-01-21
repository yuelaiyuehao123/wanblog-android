package com.wanblog.ui.activity

import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.hjq.bar.OnTitleBarListener
import com.wanblog.R
import com.wanblog.base.BaseActivity
import com.wanblog.model.bean.LoginBean
import com.wanblog.model.bean.LoginResultBean
import com.wanblog.presenter.contract.LoginContract
import com.wanblog.presenter.impl.LoginPresenter
import com.wanblog.util.UserUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : BaseActivity<LoginPresenter>(), LoginContract.View {

    protected var mUserName: String? = null

    override fun initInject() {
        activityComponent.inject(this)
    }

    override fun initPresenter() {
        mPresenter.attachView(this)
    }

    override fun getLayout(): Int = R.layout.activity_login

    override fun initView() {

        title_bar_login.setOnTitleBarListener(object : OnTitleBarListener {

            override fun onLeftClick(v: View?) {
                finish()
            }

            override fun onTitleClick(v: View?) {
            }

            override fun onRightClick(v: View?) {
                startActivity<SignUpActivity>()
            }

        })

        bt_login.setOnClickListener {
            mUserName = et_login_name.text.toString()
            val password = et_login_password.text.toString()

            if (TextUtils.isEmpty(mUserName)) {
                Toast.makeText(mActivity, "用户名不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(mActivity, "密码不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val loginBean = LoginBean(mUserName!!, password)
            mPresenter.login(loginBean)
        }
    }

    override fun initData() {
    }

    override fun onLoginInResult(data: LoginResultBean) {
        if (!TextUtils.isEmpty(data.token)) {
            UserUtil.saveToken(mActivity, data.token)
            UserUtil.saveUserName(mActivity, mUserName!!)
            finish()
        }
    }

    override fun showProgress() {
    }

    override fun showError(url: String, msg: String, code: Int) {
    }


}
