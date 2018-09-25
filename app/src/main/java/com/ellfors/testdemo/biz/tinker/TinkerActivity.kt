package com.ellfors.testdemo.biz.tinker

import android.content.Intent
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import com.ellfors.testdemo.R
import com.ellfors.testdemo.base.BaseActivity
import com.ellfors.testdemo.util.ViewUtil
import com.tencent.tinker.lib.tinker.TinkerInstaller

/**
 * 热修复
 * 2018/9/25 16:25
 */
class TinkerActivity : BaseActivity()
{
    @BindView(R.id.tv_tinker_content)
    lateinit var mText: TextView
    @BindView(R.id.btn_tinker)
    lateinit var mBtn: Button

    companion object
    {
        fun start(activity: BaseActivity)
        {
            activity.startActivity(Intent(activity, TinkerActivity::class.java))
            inOverridePendingTransition(activity)
        }
    }

    override fun getLayout(): Int
    {
        return R.layout.activity_tinker
    }

    override fun initEventAndData()
    {
        /**
         * 一、配置项目gradle文件
         * 二、配置appgradle文件
         *      1、添加apply tinker插件
         *      2、添加dependencies依赖
         *      3、配置签名项
         *      4、配置dexOptions
         *      5、配置gradle下方的Tinker属性
         *      6、改造自己的ApplicationLike {
         *          新建一个ApplicationLike类，继承DefaultApplicationLike
         *          将自己原本的Application删除，代码挪到ApplicaitonLike里面，
         *          将原来调用application的地方换成getApplication(),
         *          外部调用的静态方法通通改成ApplicationLike的方法，
         *          添加注解，Manifests中使用注解生成的Application,
         *          Rebuild一下，打完收工
         *      }
         * 三、每次更新修改
         *      ext {
         *               tinkerEnabled = true
         *               tinkerOldApkPath = "${bakPath}/app-release-0925-17-07-22.apk"              //这个
         *               tinkerApplyResourcePath = "${bakPath}/app-release-0925-17-07-22-R.txt"     //和这个
         *       }
         * 四、can't the get signConfig for this build 报错的解决方法
         *      1、右键App Module，Open Module Settings
         *      2、点击signing 配置好你的jks文件(alias,password等等)
         *      3、回到buildTypes，在SignConfig中配置刚才写好的signing文件
         * 五、每次要以正式包为基包，增量更新的包不好使！！！！！
         */

        mText.text = "第二次修复"

        mBtn.setOnClickListener {
            //安装修复的方法
            TinkerInstaller.onReceiveUpgradePatch(applicationContext, ViewUtil.getMyAppPath())
        }
    }

}