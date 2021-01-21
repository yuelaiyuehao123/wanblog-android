package com.wanblog.ui.activity

import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.hjq.bar.OnTitleBarListener
import com.wanblog.R
import com.wanblog.base.BaseActivity
import com.wanblog.model.bean.SignUpBean
import com.wanblog.presenter.contract.SignUpContract
import com.wanblog.presenter.impl.SignUpPresenter
import kotlinx.android.synthetic.main.activity_signup.*


class SignUpActivity : BaseActivity<SignUpPresenter>(), SignUpContract.View {

    protected var mUserName: String? = null

    override fun initInject() {
        activityComponent.inject(this)
    }

    override fun initPresenter() {
        mPresenter.attachView(this)
    }

    override fun getLayout(): Int = R.layout.activity_signup

    override fun initView() {

        title_bar_signUp.setOnTitleBarListener(object : OnTitleBarListener {

            override fun onLeftClick(v: View?) {
                finish()
            }

            override fun onTitleClick(v: View?) {
            }

            override fun onRightClick(v: View?) {
            }

        })

        bt_signUp.setOnClickListener {
            mUserName = et_signUp_name.text.toString()
            val password = et_signUp_password.text.toString()
            val password2 = et_signUp_password2.text.toString()

            if (TextUtils.isEmpty(mUserName)) {
                Toast.makeText(mActivity, "用户名不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(mActivity, "密码不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != password2) {
                Toast.makeText(mActivity, "两次密码不一致", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val signUpBean = SignUpBean(mUserName!!, password)
            mPresenter.signUp(signUpBean)
        }
    }

    override fun initData() {
    }

    override fun onSignUpResult() {
        Toast.makeText(mActivity, "注册成功!", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun showProgress() {
    }

    override fun showError(url: String, msg: String, code: Int) {
    }

}
