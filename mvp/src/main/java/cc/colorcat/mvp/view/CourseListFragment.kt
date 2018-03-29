package cc.colorcat.mvp.view

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.colorcat.mvp.R
import cc.colorcat.mvp.contract.IList
import cc.colorcat.mvp.dagger.component.DaggerCourseListComponent
import cc.colorcat.mvp.dagger.module.CourseListModule
import cc.colorcat.mvp.entity.Course
import cc.colorcat.mvp.extension.image.CornerTransformer
import cc.colorcat.mvp.extension.image.ImageLoader
import cc.colorcat.mvp.extension.widget.RvAdapter
import cc.colorcat.mvp.extension.widget.RvHolder
import cc.colorcat.mvp.extension.widget.SimpleAutoChoiceRvAdapter
import javax.inject.Inject

/**
 * Created by cxx on 2018/3/1.
 * xx.ch@outlook.com
 */
class CourseListFragment : ListFragment<Course>() {

    @Inject
    override lateinit var mPresenter: IList.Presenter<Course>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        DaggerCourseListComponent.builder().courseListModule(CourseListModule()).build().inject(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun createAdapter(items: List<Course>): RvAdapter {
        return object : SimpleAutoChoiceRvAdapter<Course>(items, R.layout.item_course) {
            private val borderWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4F, resources.displayMetrics)
            private val tlBr = CornerTransformer.create(CornerTransformer.TYPE_TL or CornerTransformer.TYPE_BR, borderWidth, Color.RED)
            private val trBl = CornerTransformer.create(CornerTransformer.TYPE_TR or CornerTransformer.TYPE_BL, borderWidth, Color.BLUE)
            override fun bindView(holder: RvHolder, data: Course) {
                val helper = holder.helper
                val position = helper.position
                ImageLoader.load(data.picBigUrl)
                        .addTransformer(if (position and 1 == 0) tlBr else trBl)
                        .into(helper.getView(R.id.iv_icon))
                helper.setText(R.id.tv_serial_number, position.toString())
                        .setText(R.id.tv_name, data.name)
                        .setText(R.id.tv_description, data.description)
            }
        }
    }
}
