package com.ellfors.testdemo.biz.dragphoto

import android.os.Bundle
import butterknife.BindView
import com.ellfors.testdemo.R
import com.ellfors.testdemo.base.BaseActivity.fadeInOverridePendingTransition
import com.ellfors.testdemo.base.BaseFragment
import com.ellfors.testdemo.util.ImageLoader
import com.ellfors.testdemo.widget.DragPhotoView

/**
 * 图片拖拽返回
 * 2019/5/9 11:39
 */
class DragPhotoFragment : BaseFragment()
{
    @BindView(R.id.pv_image_pager)
    lateinit var mImage: DragPhotoView

    private var mUrl: String? = null

    companion object
    {
        fun newInstance(imageUrl: String?): DragPhotoFragment
        {
            val f = DragPhotoFragment()
            val bundle = Bundle()
            bundle.putString("url", imageUrl)
            f.arguments = bundle
            return f
        }
    }

    override fun getLayout(): Int
    {
        return R.layout.fragment_drag_photo
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        mUrl = arguments?.getString("url")
    }

    override fun initEventAndData()
    {
        ImageLoader.loadFit(activity, mUrl, mImage)
        mImage.setOnExitListener { _, _, _, _, _ ->
            activity?.finish()
            fadeInOverridePendingTransition(activity)
        }
        mImage.setOnTapListener {
            activity?.finish()
            fadeInOverridePendingTransition(activity)
        }
    }
}