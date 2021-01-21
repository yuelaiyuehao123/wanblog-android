package com.wanblog.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.wanblog.R
import com.wanblog.base.SimpleFragment
import com.wanblog.ui.activity.LoginActivity
import com.wanblog.util.UserUtil
import kotlinx.android.synthetic.main.dialog_logout.view.*
import kotlinx.android.synthetic.main.fragment_me.*
import org.jetbrains.anko.support.v4.startActivity

class MeFragment : SimpleFragment() {

    companion object {
        fun newInstance(): MeFragment {
            val bundle = Bundle()
            val fragment = MeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayout(): Int = R.layout.fragment_me

    override fun initView() {
        rl_me_user_root.setOnClickListener {
            val isLogin = UserUtil.isLogin(mContext!!)
            if (isLogin) {
                showLogoutDialog()
            } else {
                startActivity<LoginActivity>()
            }
        }

        rl_me_bill_root.setOnClickListener {
            Toast.makeText(activity, "点击2", Toast.LENGTH_SHORT).show()
        }

        rl_me_question_root.setOnClickListener {
            Toast.makeText(activity, "点击3", Toast.LENGTH_SHORT).show()
        }

        rl_me_set_up_root.setOnClickListener {
            Toast.makeText(activity, "点击4", Toast.LENGTH_SHORT).show()
        }
    }

    override fun initData() {

    }

    override fun onResume() {
        super.onResume()
        checkIsLogin()
    }

    private fun showLogoutDialog() {
        val dialog = Dialog(mContext!!, R.style.MyDialog)
        val view = View.inflate(mContext!!, R.layout.dialog_logout, null)
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        view.dialog_logout_cancel.setOnClickListener {
            dialog.dismiss()
        }
        view.dialog_logout_confirm.setOnClickListener {
            UserUtil.logout(mContext!!)
            checkIsLogin()
            dialog.dismiss()
        }
    }

    /**
     * 检查用户是否登录，显示信息
     */
    private fun checkIsLogin() {
        val isLogin = UserUtil.isLogin(mContext!!)
        if (isLogin) {
            val userName = UserUtil.getUserName(mContext!!)
            tv_me_user_name.text = userName
        } else {
            tv_me_user_name.text = "登录/注册"
        }
    }

}