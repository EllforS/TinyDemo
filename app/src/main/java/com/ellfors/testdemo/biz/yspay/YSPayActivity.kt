package com.ellfors.testdemo.biz.yspay

import android.content.Intent
import android.util.Log
import android.view.View
import com.ellfors.testdemo.R
import com.ellfors.testdemo.base.BaseActivity
import com.yinsheng.android.app.merchant.IActionCallBack
import com.yinsheng.android.app.merchant.YSPayAssist
import kotlinx.android.synthetic.main.activity_yspay.*
import org.json.JSONObject

/**
 * 测试公司支付
 * 2018/12/24 11:10
 */
class YSPayActivity : BaseActivity(), IActionCallBack, View.OnClickListener
{
    private var orderNo: String? = null

    companion object
    {
        private const val PayApkName = "YinShengPlugin.apk"

        fun start(activity: BaseActivity)
        {
            activity.startActivity(Intent(activity, YSPayActivity::class.java))
            inOverridePendingTransition(activity)
        }
    }

    override fun getLayout(): Int
    {
        return R.layout.activity_yspay
    }

    override fun initEventAndData()
    {
        btn_pay_1.setOnClickListener(this)
        btn_pay_2.setOnClickListener(this)
    }

    override fun onClick(view: View?)
    {
        orderNo = et_yspay.text.toString().trim()
        if (orderNo.isNullOrEmpty())
        {
            showToast("输入为空")
            return
        }
        if (!YSPayUtil.checkApkExist(this@YSPayActivity))
            return
        when (view?.id)
        {
            //银盛支付
            R.id.btn_pay_1 ->
            {
                try
                {
                    //打开支付控件
                    YSPayAssist.getInstance().startPay(
                            this@YSPayActivity,
                            this@YSPayActivity,
                            setOrderInfo(orderNo),
                            PayApkName)
                }
                catch (e: Exception)
                {
                    Log.d("AAA", "错误：" + e.toString())
                }
            }
            //银联支付
            R.id.btn_pay_2 ->
            {
                try
                {
                    //打开支付控件
                    YSPayAssist.getInstance().startPay(
                            this@YSPayActivity,
                            this@YSPayActivity,
                            setOrderInfo2(orderNo),
                            PayApkName)
                }
                catch (e: Exception)
                {
                    Log.d("AAA", "错误：" + e.toString())
                }
            }
        }
    }

    /**
     * 拼接json串
     */
    @Throws(Exception::class)
    private fun setOrderInfo(orderNo: String?): String
    {
        val jsonObj = JSONObject()
        if (orderNo.isNullOrEmpty())
            return jsonObj.toString()
        jsonObj.put("TradeSn", orderNo)
        jsonObj.put("PayMethod", "all") //unionpay:指定银联支付，all：不指定支付方式

//        jsonObj.put("GoTest","Y");
//        jsonObj.put("TranType", "311");

        return jsonObj.toString()
    }

    /**
     * 拼接json串
     */
    @Throws(Exception::class)
    private fun setOrderInfo2(orderNo: String?): String
    {
        val jsonObj = JSONObject()
        if (orderNo.isNullOrEmpty())
            return jsonObj.toString()
        jsonObj.put("PayMethod", "unionpay")//unionpay:指定银联支付，all：不指定支付方式
        jsonObj.put("TradeSn", orderNo)

//        jsonObj.put("TranType", mEdtxtTransType.getText().toString());
//        jsonObj.put("GoTest","Y");

        return jsonObj.toString()
    }

    /**
     * 支付回调
     */
    override fun callBack(result: Any?)
    {
        val str = result as String
        val temp = when
        {
            str.equals("success", ignoreCase = true) -> "支付成功"
            str.equals("fail", ignoreCase = true) -> "支付失败，您可以重试操作"
            str.equals("cancel", ignoreCase = true) -> "用户取消了支付"
            else -> str
        }
        showToast(temp)
    }

}